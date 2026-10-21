package uz.urspi.newurspi.hemisconfig.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import uz.urspi.newurspi.audit.AuditAction;
import uz.urspi.newurspi.audit.Auditable;
import uz.urspi.newurspi.exceptions.BadRequestException;
import uz.urspi.newurspi.exceptions.ResourceNotFoundException;
import uz.urspi.newurspi.hemisconfig.HemisConfig;
import uz.urspi.newurspi.hemisconfig.dto.HemisConfigDTO;
import uz.urspi.newurspi.hemisconfig.mapper.HemisConfigMapper;
import uz.urspi.newurspi.hemisconfig.repository.HemisConfigRepository;
import uz.urspi.newurspi.hemisconfig.response.HemisConfigResponse;
import uz.urspi.newurspi.hemisconfig.service.HemisConfigService;
import uz.urspi.newurspi.utils.CacheNames;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class HemisConfigServiceImpl implements HemisConfigService {

    private static final String DEFAULT_LANG = "uz-UZ";

    private final HemisConfigRepository repository;
    private final HemisConfigMapper mapper;

    @Override
    @CacheEvict(value = CacheNames.HEMIS_CONFIGS, allEntries = true)
    @Auditable(action = AuditAction.CREATE, entity = "HemisConfig")
    public HemisConfigResponse create(HemisConfigDTO dto) {
        if (!StringUtils.hasText(dto.getToken())) {
            throw new BadRequestException("Token cannot be empty");
        }
        if (repository.existsByNameIgnoreCase(dto.getName())) {
            throw new BadRequestException("Hemis config with this name already exists");
        }

        boolean active = Boolean.TRUE.equals(dto.getActive());

        HemisConfig config = HemisConfig.builder()
                .name(dto.getName().trim())
                .baseUrl(normalizeBaseUrl(dto.getBaseUrl()))
                .token(dto.getToken().trim())
                .defaultLang(resolveLang(dto.getDefaultLang()))
                .active(active)
                .status(Status.ACTIVE)
                .createdUsername(currentUsername())
                .build();

        HemisConfig saved = repository.save(config);
        if (active) {
            deactivateOthers(saved.getId());
        }
        log.info("HemisConfig created with id {}", saved.getId());
        return mapper.toResponse(saved);
    }

    @Override
    @Cacheable(value = CacheNames.HEMIS_CONFIGS, key = "#id")
    @Auditable(action = AuditAction.READ, entity = "HemisConfig")
    public HemisConfigResponse findById(Long id) {
        return mapper.toResponse(getOrThrow(id));
    }

    @Override
    @Cacheable(value = CacheNames.HEMIS_CONFIGS, key = "'all'")
    @Auditable(action = AuditAction.READ, entity = "HemisConfig")
    public List<HemisConfigResponse> fetchAll() {
        return mapper.toResponseList(repository.findAll());
    }

    @Override
    @CacheEvict(value = CacheNames.HEMIS_CONFIGS, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "HemisConfig")
    public HemisConfigResponse update(Long id, HemisConfigDTO dto) {
        HemisConfig config = getOrThrow(id);

        if (!config.getName().equalsIgnoreCase(dto.getName())
                && repository.existsByNameIgnoreCase(dto.getName())) {
            throw new BadRequestException("Hemis config with this name already exists");
        }

        boolean active = dto.getActive() != null ? dto.getActive() : config.isActive();

        config.setName(dto.getName().trim());
        config.setBaseUrl(normalizeBaseUrl(dto.getBaseUrl()));
        config.setDefaultLang(resolveLang(dto.getDefaultLang() != null ? dto.getDefaultLang() : config.getDefaultLang()));
        config.setActive(active);
        if (StringUtils.hasText(dto.getToken())) {
            config.setToken(dto.getToken().trim());
        }

        HemisConfig saved = repository.save(config);
        if (active) {
            deactivateOthers(saved.getId());
        }
        return mapper.toResponse(saved);
    }

    @Override
    @CacheEvict(value = CacheNames.HEMIS_CONFIGS, allEntries = true)
    @Auditable(action = AuditAction.DELETE, entity = "HemisConfig")
    public void delete(Long id) {
        HemisConfig config = getOrThrow(id);
        config.setActive(false);
        config.setStatus(Status.DELETED);
    }

    @Override
    @CacheEvict(value = CacheNames.HEMIS_CONFIGS, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "HemisConfig")
    public void activeOrDisabled(Long id) {
        HemisConfig config = getOrThrow(id);
        if (config.getStatus() == Status.ACTIVE) {
            config.setStatus(Status.DISABLED);
            config.setActive(false);
        } else if (config.getStatus() == Status.DISABLED) {
            config.setStatus(Status.ACTIVE);
        }
    }

    @Override
    public HemisConfig getActiveConfigEntity() {
        return repository.findFirstByActiveTrueAndStatus(Status.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Active HEMIS config not found. Create and activate one first."));
    }

    private void deactivateOthers(Long keepId) {
        for (HemisConfig other : repository.findAllByActiveTrue()) {
            if (!other.getId().equals(keepId)) {
                other.setActive(false);
            }
        }
    }

    private HemisConfig getOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hemis config not found with id = " + id));
    }

    private String normalizeBaseUrl(String baseUrl) {
        String url = baseUrl.trim();
        while (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        return url;
    }

    private String resolveLang(String lang) {
        return StringUtils.hasText(lang) ? lang.trim() : DEFAULT_LANG;
    }

    private String currentUsername() {
        return Objects.requireNonNull(SecurityContextHolder
                        .getContext()
                        .getAuthentication())
                .getName();
    }
}
