package uz.urspi.newurspi.hemis.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;
import uz.urspi.newurspi.hemis.exception.HemisApiException;
import uz.urspi.newurspi.hemisconfig.HemisConfig;
import uz.urspi.newurspi.hemisconfig.service.HemisConfigService;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class HemisApiClient {

    private static final int MAX_LIMIT = 200;
    private static final int MAX_RETRIES = 3;

    private final RestClient.Builder hemisRestClientBuilder;
    private final HemisConfigService hemisConfigService;
    private final HemisRateLimiter rateLimiter;
    private final ObjectMapper objectMapper;

    public JsonNode get(String path, Map<String, ?> queryParams) {
        HemisConfig config = hemisConfigService.getActiveConfigEntity();
        return get(config, path, queryParams, true);
    }

    public JsonNode get(HemisConfig config, String path, Map<String, ?> queryParams) {
        return get(config, path, queryParams, true);
    }

    /**
     * HEMIS Public API — token talab qilinmaydi, faqat baseUrl kerak.
     */
    public JsonNode getPublic(String path, Map<String, ?> queryParams) {
        HemisConfig config = hemisConfigService.getActiveConfigEntity();
        return get(config, path, queryParams, false);
    }

    private JsonNode get(HemisConfig config, String path, Map<String, ?> queryParams, boolean withAuth) {
        String url = buildUrl(config.getBaseUrl(), path, queryParams, config.getDefaultLang());
        int attempt = 0;
        while (true) {
            attempt++;
            rateLimiter.acquire();
            try {
                RestClient.RequestHeadersSpec<?> request = hemisRestClientBuilder.build()
                        .get()
                        .uri(url)
                        .accept(MediaType.APPLICATION_JSON);
                if (withAuth) {
                    request = request.header(HttpHeaders.AUTHORIZATION, "Bearer " + config.getToken());
                }
                String body = request.retrieve().body(String.class);
                return objectMapper.readTree(body == null ? "{}" : body);
            } catch (RestClientResponseException ex) {
                if (ex.getStatusCode().value() == 429 && attempt < MAX_RETRIES) {
                    log.warn("HEMIS rate limited (429), retry {}/{}", attempt, MAX_RETRIES);
                    sleepBackoff(attempt);
                    continue;
                }
                throw new HemisApiException(
                        "HEMIS request failed: HTTP " + ex.getStatusCode().value(),
                        ex.getStatusCode().value()
                );
            } catch (HemisApiException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new HemisApiException("HEMIS request failed: " + ex.getMessage());
            }
        }
    }

    private String buildUrl(String baseUrl, String path, Map<String, ?> queryParams, String defaultLang) {
        String normalizedPath = path.startsWith("/") ? path : "/" + path;
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(baseUrl + normalizedPath);

        boolean hasLang = false;
        if (queryParams != null) {
            for (Map.Entry<String, ?> entry : queryParams.entrySet()) {
                if (entry.getValue() == null) {
                    continue;
                }
                if ("l".equals(entry.getKey())) {
                    hasLang = true;
                }
                if ("limit".equals(entry.getKey())) {
                    int limit = Integer.parseInt(String.valueOf(entry.getValue()));
                    builder.queryParam("limit", Math.min(Math.max(limit, 1), MAX_LIMIT));
                    continue;
                }
                builder.queryParam(entry.getKey(), entry.getValue());
            }
        }
        if (!hasLang && defaultLang != null) {
            builder.queryParam("l", defaultLang);
        }
        return builder.toUriString();
    }

    private void sleepBackoff(int attempt) {
        try {
            Thread.sleep(200L * attempt);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new HemisApiException("Interrupted while waiting for HEMIS retry");
        }
    }
}
