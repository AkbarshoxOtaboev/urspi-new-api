package uz.urspi.newurspi.hemis.publicstat.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import uz.urspi.newurspi.hemis.client.HemisApiClient;
import uz.urspi.newurspi.hemis.publicstat.response.StudentStatResponse;
import uz.urspi.newurspi.hemis.publicstat.service.HemisStudentStatService;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class HemisStudentStatServiceImpl implements HemisStudentStatService {

    public static final String STAT_STUDENT_PATH = "/rest/v1/public/stat-student";

    private final HemisApiClient hemisApiClient;
    private final ObjectMapper objectMapper;

    @Override
    public StudentStatResponse fetchStudentStats(String lang) {
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.hasText(lang)) {
            params.put("l", lang.trim());
        }
        JsonNode root = hemisApiClient.getPublic(STAT_STUDENT_PATH, params);
        StudentStatResponse response = objectMapper.convertValue(root, StudentStatResponse.class);
        if (response.getCode() == null && root.has("code") && root.get("code").canConvertToInt()) {
            response.setCode(root.get("code").asInt());
        }
        log.info("Fetched HEMIS student stats, success={}", response.getSuccess());
        return response;
    }
}
