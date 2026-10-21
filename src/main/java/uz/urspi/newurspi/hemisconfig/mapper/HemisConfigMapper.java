package uz.urspi.newurspi.hemisconfig.mapper;

import org.springframework.stereotype.Component;
import uz.urspi.newurspi.hemisconfig.HemisConfig;
import uz.urspi.newurspi.hemisconfig.response.HemisConfigResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HemisConfigMapper {

    public HemisConfigResponse toResponse(HemisConfig config) {
        if (config == null) {
            return null;
        }
        return HemisConfigResponse.builder()
                .id(config.getId())
                .name(config.getName())
                .baseUrl(config.getBaseUrl())
                .tokenMasked(maskToken(config.getToken()))
                .defaultLang(config.getDefaultLang())
                .active(config.isActive())
                .status(config.getStatus())
                .createdAt(config.getCreatedAt())
                .updatedAt(config.getUpdatedAt())
                .build();
    }

    public List<HemisConfigResponse> toResponseList(List<HemisConfig> configs) {
        return configs.stream()
                .map(this::toResponse)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private String maskToken(String token) {
        if (token == null || token.isBlank()) {
            return null;
        }
        if (token.length() <= 4) {
            return "****";
        }
        return "****" + token.substring(token.length() - 4);
    }
}
