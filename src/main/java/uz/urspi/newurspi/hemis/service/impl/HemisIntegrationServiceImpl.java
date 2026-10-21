package uz.urspi.newurspi.hemis.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.urspi.newurspi.audit.AuditAction;
import uz.urspi.newurspi.audit.Auditable;
import uz.urspi.newurspi.hemis.client.HemisApiClient;
import uz.urspi.newurspi.hemis.exception.HemisApiException;
import uz.urspi.newurspi.hemis.response.HemisConnectionTestResponse;
import uz.urspi.newurspi.hemis.response.HemisSyncResultResponse;
import uz.urspi.newurspi.hemis.service.HemisIntegrationService;
import uz.urspi.newurspi.hemis.sync.HemisDepartment;
import uz.urspi.newurspi.hemis.sync.HemisEmployee;
import uz.urspi.newurspi.hemis.sync.HemisSyncLog;
import uz.urspi.newurspi.hemis.sync.HemisSyncResource;
import uz.urspi.newurspi.hemis.sync.repository.HemisDepartmentRepository;
import uz.urspi.newurspi.hemis.sync.repository.HemisEmployeeRepository;
import uz.urspi.newurspi.hemis.sync.repository.HemisSyncLogRepository;
import uz.urspi.newurspi.hemisconfig.HemisConfig;
import uz.urspi.newurspi.hemisconfig.service.HemisConfigService;
import uz.urspi.newurspi.utils.CacheNames;
import uz.urspi.newurspi.utils.Status;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class HemisIntegrationServiceImpl implements HemisIntegrationService {

    private static final String DEPARTMENT_PATH = "/rest/v1/data/department-list";
    private static final String EMPLOYEE_PATH = "/rest/v1/data/employee-list";
    private static final int PAGE_LIMIT = 200;

    private final HemisApiClient hemisApiClient;
    private final HemisConfigService hemisConfigService;
    private final HemisDepartmentRepository departmentRepository;
    private final HemisEmployeeRepository employeeRepository;
    private final HemisSyncLogRepository syncLogRepository;

    @Override
    @Auditable(action = AuditAction.READ, entity = "HemisIntegration")
    public HemisConnectionTestResponse testConnection() {
        HemisConfig config = hemisConfigService.getActiveConfigEntity();
        try {
            JsonNode root = hemisApiClient.get(config, DEPARTMENT_PATH, Map.of("limit", 1, "page", 1));
            JsonNode items = extractItems(root);
            int count = items != null && items.isArray() ? items.size() : 0;
            return HemisConnectionTestResponse.builder()
                    .success(true)
                    .baseUrl(config.getBaseUrl())
                    .path(DEPARTMENT_PATH)
                    .httpLikeStatus(200)
                    .message("HEMIS connection OK")
                    .sampleItemCount(count)
                    .build();
        } catch (HemisApiException ex) {
            return HemisConnectionTestResponse.builder()
                    .success(false)
                    .baseUrl(config.getBaseUrl())
                    .path(DEPARTMENT_PATH)
                    .httpLikeStatus(ex.getStatusCode() == 0 ? null : ex.getStatusCode())
                    .message(ex.getMessage())
                    .sampleItemCount(0)
                    .build();
        }
    }

    @Override
    @CacheEvict(value = {CacheNames.HEMIS_DEPARTMENTS, CacheNames.HEMIS_EMPLOYEES}, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "HemisIntegration")
    public HemisSyncResultResponse sync(HemisSyncResource resource) {
        return switch (resource) {
            case DEPARTMENT -> syncDepartments();
            case EMPLOYEE -> syncEmployees();
        };
    }

    private HemisSyncResultResponse syncDepartments() {
        int fetched = 0;
        int upserted = 0;
        try {
            int page = 1;
            while (true) {
                Map<String, Object> params = new HashMap<>();
                params.put("limit", PAGE_LIMIT);
                params.put("page", page);
                JsonNode root = hemisApiClient.get(DEPARTMENT_PATH, params);
                JsonNode items = extractItems(root);
                if (items == null || !items.isArray() || items.isEmpty()) {
                    break;
                }
                for (JsonNode item : items) {
                    fetched++;
                    Long externalId = readLong(item, "id");
                    if (externalId == null) {
                        continue;
                    }
                    HemisDepartment entity = departmentRepository.findByExternalId(externalId)
                            .orElseGet(() -> HemisDepartment.builder()
                                    .externalId(externalId)
                                    .status(Status.ACTIVE)
                                    .createdUsername(currentUsername())
                                    .build());
                    entity.setCode(readText(item, "code"));
                    entity.setName(firstNonBlank(readText(item, "name"), readText(item, "nameUz"), "Department #" + externalId));
                    entity.setParentId(readLong(item, "parent"));
                    if (entity.getParentId() == null) {
                        entity.setParentId(readLong(item, "parent_id"));
                    }
                    entity.setStructureType(readText(item, "structureType"));
                    if (entity.getStructureType() == null) {
                        entity.setStructureType(readText(item, "structure_type"));
                    }
                    entity.setStatus(Status.ACTIVE);
                    departmentRepository.save(entity);
                    upserted++;
                }
                if (items.size() < PAGE_LIMIT) {
                    break;
                }
                page++;
            }
            return saveLog(HemisSyncResource.DEPARTMENT, true, fetched, upserted, "Departments synced");
        } catch (Exception ex) {
            log.error("HEMIS department sync failed: {}", ex.getMessage());
            return saveLog(HemisSyncResource.DEPARTMENT, false, fetched, upserted, ex.getMessage());
        }
    }

    private HemisSyncResultResponse syncEmployees() {
        int fetched = 0;
        int upserted = 0;
        try {
            int page = 1;
            while (true) {
                Map<String, Object> params = new HashMap<>();
                params.put("limit", PAGE_LIMIT);
                params.put("page", page);
                JsonNode root = hemisApiClient.get(EMPLOYEE_PATH, params);
                JsonNode items = extractItems(root);
                if (items == null || !items.isArray() || items.isEmpty()) {
                    break;
                }
                for (JsonNode item : items) {
                    fetched++;
                    Long externalId = readLong(item, "id");
                    if (externalId == null) {
                        continue;
                    }
                    HemisEmployee entity = employeeRepository.findByExternalId(externalId)
                            .orElseGet(() -> HemisEmployee.builder()
                                    .externalId(externalId)
                                    .status(Status.ACTIVE)
                                    .createdUsername(currentUsername())
                                    .build());
                    entity.setFullName(firstNonBlank(
                            readText(item, "full_name"),
                            readText(item, "fullName"),
                            readText(item, "name"),
                            "Employee #" + externalId
                    ));
                    entity.setEmployeeIdNumber(firstNonBlank(
                            readText(item, "employee_id_number"),
                            readText(item, "employeeIdNumber")
                    ));
                    entity.setDepartmentId(readNestedLong(item, "department", "id"));
                    entity.setDepartmentName(readNestedText(item, "department", "name"));
                    entity.setPositionName(firstNonBlank(
                            readNestedText(item, "staffPosition", "name"),
                            readNestedText(item, "position", "name")
                    ));
                    entity.setAcademicDegreeName(firstNonBlank(
                            readNestedText(item, "academicDegree", "name"),
                            readNestedText(item, "academic_degree", "name")
                    ));
                    entity.setStatus(Status.ACTIVE);
                    employeeRepository.save(entity);
                    upserted++;
                }
                if (items.size() < PAGE_LIMIT) {
                    break;
                }
                page++;
            }
            return saveLog(HemisSyncResource.EMPLOYEE, true, fetched, upserted, "Employees synced");
        } catch (Exception ex) {
            log.error("HEMIS employee sync failed: {}", ex.getMessage());
            return saveLog(HemisSyncResource.EMPLOYEE, false, fetched, upserted, ex.getMessage());
        }
    }

    private HemisSyncResultResponse saveLog(
            HemisSyncResource resource,
            boolean success,
            int fetched,
            int upserted,
            String message
    ) {
        HemisSyncLog logEntity = HemisSyncLog.builder()
                .resource(resource)
                .success(success)
                .fetchedCount(fetched)
                .upsertedCount(upserted)
                .message(message != null && message.length() > 2000 ? message.substring(0, 2000) : message)
                .status(Status.ACTIVE)
                .createdUsername(currentUsername())
                .build();
        syncLogRepository.save(logEntity);
        return HemisSyncResultResponse.builder()
                .resource(resource)
                .success(success)
                .fetchedCount(fetched)
                .upsertedCount(upserted)
                .message(message)
                .build();
    }

    private JsonNode extractItems(JsonNode root) {
        if (root == null) {
            return null;
        }
        if (root.has("data") && root.get("data").has("items")) {
            return root.get("data").get("items");
        }
        if (root.has("items")) {
            return root.get("items");
        }
        if (root.isArray()) {
            return root;
        }
        return null;
    }

    private Long readLong(JsonNode node, String field) {
        if (node == null || !node.has(field) || node.get(field).isNull()) {
            return null;
        }
        JsonNode value = node.get(field);
        if (value.isNumber()) {
            return value.asLong();
        }
        if (value.isTextual() && !value.asText().isBlank()) {
            try {
                return Long.parseLong(value.asText());
            } catch (NumberFormatException ignored) {
                return null;
            }
        }
        if (value.isObject() && value.has("id")) {
            return readLong(value, "id");
        }
        return null;
    }

    private Long readNestedLong(JsonNode node, String objectField, String field) {
        if (node == null || !node.has(objectField) || node.get(objectField).isNull()) {
            return null;
        }
        return readLong(node.get(objectField), field);
    }

    private String readText(JsonNode node, String field) {
        if (node == null || !node.has(field) || node.get(field).isNull()) {
            return null;
        }
        String text = node.get(field).asText();
        return text == null || text.isBlank() ? null : text;
    }

    private String readNestedText(JsonNode node, String objectField, String field) {
        if (node == null || !node.has(objectField) || node.get(objectField).isNull()) {
            return null;
        }
        return readText(node.get(objectField), field);
    }

    private String firstNonBlank(String... values) {
        if (values == null) {
            return null;
        }
        for (String value : values) {
            if (value != null && !value.isBlank()) {
                return value;
            }
        }
        return null;
    }

    private String currentUsername() {
        try {
            return Objects.requireNonNull(SecurityContextHolder
                            .getContext()
                            .getAuthentication())
                    .getName();
        } catch (Exception ex) {
            return "SYSTEM";
        }
    }
}
