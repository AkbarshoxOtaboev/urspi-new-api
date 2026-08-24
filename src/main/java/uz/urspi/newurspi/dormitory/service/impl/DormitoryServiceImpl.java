package uz.urspi.newurspi.dormitory.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.urspi.newurspi.audit.AuditAction;
import uz.urspi.newurspi.audit.Auditable;
import uz.urspi.newurspi.dormitory.Dormitory;
import uz.urspi.newurspi.dormitory.dto.DormitoryDTO;
import uz.urspi.newurspi.dormitory.mapper.DormitoryMapper;
import uz.urspi.newurspi.dormitory.repository.DormitoryRepository;
import uz.urspi.newurspi.dormitory.response.DormitoryLocalizedResponse;
import uz.urspi.newurspi.dormitory.response.DormitoryResponse;
import uz.urspi.newurspi.dormitory.service.DormitoryService;
import uz.urspi.newurspi.exceptions.ResourceNotFoundException;
import uz.urspi.newurspi.storage.StorageService;
import uz.urspi.newurspi.utils.CacheNames;
import uz.urspi.newurspi.utils.Language;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DormitoryServiceImpl implements DormitoryService {
    private final DormitoryRepository repository;
    private final DormitoryMapper mapper;
    private final StorageService storageService;

    @Override
    @CacheEvict(value = CacheNames.DORMITORIES, allEntries = true)
    @Auditable(action = AuditAction.CREATE, entity = "Dormitory")
    public DormitoryResponse create(DormitoryDTO dto) {
        return mapper.toResponse(repository.save(Dormitory.builder()
                .titleUz(dto.getTitleUz())
                .titleRu(dto.getTitleRu())
                .titleEn(dto.getTitleEn())
                .descriptionUz(dto.getDescriptionUz())
                .descriptionRu(dto.getDescriptionRu())
                .descriptionEn(dto.getDescriptionEn())
                .imageLink(storeFile(dto.getImage()))
                .status(Status.ACTIVE)
                .createdUsername(currentUsername())
                .build()));
    }

    @Override
    @Cacheable(value = CacheNames.DORMITORIES, key = "#id")
    @Auditable(action = AuditAction.READ, entity = "Dormitory")
    public DormitoryResponse findById(Long id) {
        return mapper.toResponse(getOrThrow(id));
    }

    @Override
    @Cacheable(value = CacheNames.DORMITORIES, key = "'all'")
    @Auditable(action = AuditAction.READ, entity = "Dormitory")
    public List<DormitoryResponse> fetchAll() {
        return mapper.toResponseList(repository.findAllByOrderByCreatedAtDesc());
    }

    @Override
    @Cacheable(value = CacheNames.DORMITORIES, key = "'lang_' + #lang.name()")
    @Auditable(action = AuditAction.READ, entity = "Dormitory")
    public List<DormitoryLocalizedResponse> fetchAllByLang(Language lang) {
        return mapper.toLocalizedResponseList(repository.findAllByOrderByCreatedAtDesc(), lang);
    }

    @Override
    @CacheEvict(value = CacheNames.DORMITORIES, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "Dormitory")
    public DormitoryResponse update(Long id, DormitoryDTO dto) {
        Dormitory entity = getOrThrow(id);
        entity.setTitleUz(dto.getTitleUz());
        entity.setTitleRu(dto.getTitleRu());
        entity.setTitleEn(dto.getTitleEn());
        entity.setDescriptionUz(dto.getDescriptionUz());
        entity.setDescriptionRu(dto.getDescriptionRu());
        entity.setDescriptionEn(dto.getDescriptionEn());
        String imageLink = storeFile(dto.getImage());
        if (imageLink != null) {
            entity.setImageLink(imageLink);
        }
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @CacheEvict(value = CacheNames.DORMITORIES, allEntries = true)
    @Auditable(action = AuditAction.DELETE, entity = "Dormitory")
    public void delete(Long id) {
        getOrThrow(id).setStatus(Status.DELETED);
    }

    @Override
    @CacheEvict(value = CacheNames.DORMITORIES, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "Dormitory")
    public void activeOrDisabled(Long id) {
        Dormitory entity = getOrThrow(id);
        if (entity.getStatus() == Status.ACTIVE) {
            entity.setStatus(Status.DISABLED);
        } else if (entity.getStatus() == Status.DISABLED) {
            entity.setStatus(Status.ACTIVE);
        }
    }

    private Dormitory getOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dormitory not found with id = " + id));
    }

    private String currentUsername() {
        return Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
    }

    private String storeFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        return "/api/files/" + storageService.uploadFile(file);
    }
}
