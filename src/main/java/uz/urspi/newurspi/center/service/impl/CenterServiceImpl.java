package uz.urspi.newurspi.center.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.urspi.newurspi.audit.AuditAction;
import uz.urspi.newurspi.audit.Auditable;
import uz.urspi.newurspi.center.Center;
import uz.urspi.newurspi.center.dto.CenterDTO;
import uz.urspi.newurspi.center.mapper.CenterMapper;
import uz.urspi.newurspi.center.repository.CenterRepository;
import uz.urspi.newurspi.center.response.CenterLocalizedResponse;
import uz.urspi.newurspi.center.response.CenterResponse;
import uz.urspi.newurspi.center.service.CenterService;
import uz.urspi.newurspi.exceptions.BadRequestException;
import uz.urspi.newurspi.exceptions.ResourceNotFoundException;
import uz.urspi.newurspi.utils.CacheNames;
import uz.urspi.newurspi.utils.Language;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CenterServiceImpl implements CenterService {
    private final CenterRepository repository;
    private final CenterMapper mapper;

    @Override
    @CacheEvict(value = CacheNames.CENTERS, allEntries = true)
    @Auditable(action = AuditAction.CREATE, entity = "Center")
    public CenterResponse create(CenterDTO dto) {
        if (repository.existsByNameUzIgnoreCase(dto.getNameUz())) {
            throw new BadRequestException("Center with this name already exists");
        }

        String username = currentUsername();

        Center center = Center.builder()
                .nameUz(dto.getNameUz())
                .nameRu(dto.getNameRu())
                .nameEn(dto.getNameEn())
                .descriptionUz(dto.getDescriptionUz())
                .descriptionRu(dto.getDescriptionRu())
                .descriptionEn(dto.getDescriptionEn())
                .status(Status.ACTIVE)
                .createdUsername(username)
                .build();

        Center saved = repository.save(center);
        log.info("Center created with id {}", saved.getId());
        return mapper.toResponse(saved);
    }

    @Override
    @Cacheable(value = CacheNames.CENTERS, key = "#id")
    @Auditable(action = AuditAction.READ, entity = "Center")
    public CenterResponse findById(Long id) {
        log.info("Find center by id {}", id);
        return mapper.toResponse(getCenterOrThrow(id));
    }

    @Override
    @Cacheable(value = CacheNames.CENTERS, key = "'all'")
    @Auditable(action = AuditAction.READ, entity = "Center")
    public List<CenterResponse> fetchAllCenters() {
        log.info("Fetch all centers");
        return mapper.toResponseList(repository.findAll());
    }

    @Override
    @Cacheable(value = CacheNames.CENTERS, key = "'lang:'+#lang")
    @Auditable(action = AuditAction.READ, entity = "Center")
    public List<CenterLocalizedResponse> fetchAllCentersByLang(Language lang) {
        log.info("Fetch all centers by lang {}", lang);
        return mapper.toLocalizedResponseList(repository.findAll(), lang);
    }

    @Override
    @CacheEvict(value = CacheNames.CENTERS, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "Center")
    public CenterResponse update(Long id, CenterDTO dto) {
        log.info("Update center with id {}", id);

        Center center = getCenterOrThrow(id);

        if (!center.getNameUz().equalsIgnoreCase(dto.getNameUz())
                && repository.existsByNameUzIgnoreCase(dto.getNameUz())) {
            throw new BadRequestException("Center with this name already exists");
        }

        center.setNameUz(dto.getNameUz());
        center.setNameRu(dto.getNameRu());
        center.setNameEn(dto.getNameEn());
        center.setDescriptionUz(dto.getDescriptionUz());
        center.setDescriptionRu(dto.getDescriptionRu());
        center.setDescriptionEn(dto.getDescriptionEn());

        Center updated = repository.save(center);
        return mapper.toResponse(updated);
    }

    @Override
    @CacheEvict(value = CacheNames.CENTERS, allEntries = true)
    @Auditable(action = AuditAction.DELETE, entity = "Center")
    public void delete(Long id) {
        log.info("Delete center by id {}", id);
        Center center = getCenterOrThrow(id);
        center.setStatus(Status.DELETED);
    }

    @Override
    @CacheEvict(value = CacheNames.CENTERS, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "Center")
    public void activeOrDisabledCenter(Long id) {
        log.info("Disable or active center with id {}", id);
        Center center = getCenterOrThrow(id);
        if (center.getStatus() == Status.ACTIVE) {
            log.info("Disabled center {}", center.getNameUz());
            center.setStatus(Status.DISABLED);
        } else if (center.getStatus() == Status.DISABLED) {
            log.info("Activate center {}", center.getNameUz());
            center.setStatus(Status.ACTIVE);
        }
    }

    private Center getCenterOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Center not found with id = " + id));
    }

    private String currentUsername() {
        return Objects.requireNonNull(SecurityContextHolder
                        .getContext()
                        .getAuthentication())
                .getName();
    }
}
