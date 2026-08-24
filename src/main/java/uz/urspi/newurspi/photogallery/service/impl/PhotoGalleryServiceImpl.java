package uz.urspi.newurspi.photogallery.service.impl;

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
import uz.urspi.newurspi.exceptions.ResourceNotFoundException;
import uz.urspi.newurspi.photogallery.PhotoGallery;
import uz.urspi.newurspi.photogallery.dto.PhotoGalleryDTO;
import uz.urspi.newurspi.photogallery.mapper.PhotoGalleryMapper;
import uz.urspi.newurspi.photogallery.repository.PhotoGalleryRepository;
import uz.urspi.newurspi.photogallery.response.PhotoGalleryLocalizedResponse;
import uz.urspi.newurspi.photogallery.response.PhotoGalleryResponse;
import uz.urspi.newurspi.photogallery.service.PhotoGalleryService;
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
public class PhotoGalleryServiceImpl implements PhotoGalleryService {
    private final PhotoGalleryRepository repository;
    private final PhotoGalleryMapper mapper;
    private final StorageService storageService;

    @Override
    @CacheEvict(value = CacheNames.PHOTO_GALLERIES, allEntries = true)
    @Auditable(action = AuditAction.CREATE, entity = "PhotoGallery")
    public PhotoGalleryResponse create(PhotoGalleryDTO dto) {
        PhotoGallery saved = repository.save(PhotoGallery.builder()
                .titleUz(dto.getTitleUz())
                .titleRu(dto.getTitleRu())
                .titleEn(dto.getTitleEn())
                .descriptionUz(dto.getDescriptionUz())
                .descriptionRu(dto.getDescriptionRu())
                .descriptionEn(dto.getDescriptionEn())
                .imageLink(storeFile(dto.getImage()))
                .status(Status.ACTIVE)
                .createdUsername(currentUsername())
                .build());
        return mapper.toResponse(saved);
    }

    @Override
    @Cacheable(value = CacheNames.PHOTO_GALLERIES, key = "#id")
    @Auditable(action = AuditAction.READ, entity = "PhotoGallery")
    public PhotoGalleryResponse findById(Long id) {
        return mapper.toResponse(getOrThrow(id));
    }

    @Override
    @Cacheable(value = CacheNames.PHOTO_GALLERIES, key = "'all'")
    @Auditable(action = AuditAction.READ, entity = "PhotoGallery")
    public List<PhotoGalleryResponse> fetchAll() {
        return mapper.toResponseList(repository.findAllByOrderByCreatedAtDesc());
    }

    @Override
    @Cacheable(value = CacheNames.PHOTO_GALLERIES, key = "'lang_' + #lang.name()")
    @Auditable(action = AuditAction.READ, entity = "PhotoGallery")
    public List<PhotoGalleryLocalizedResponse> fetchAllByLang(Language lang) {
        return mapper.toLocalizedResponseList(repository.findAllByOrderByCreatedAtDesc(), lang);
    }

    @Override
    @CacheEvict(value = CacheNames.PHOTO_GALLERIES, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "PhotoGallery")
    public PhotoGalleryResponse update(Long id, PhotoGalleryDTO dto) {
        PhotoGallery entity = getOrThrow(id);
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
    @CacheEvict(value = CacheNames.PHOTO_GALLERIES, allEntries = true)
    @Auditable(action = AuditAction.DELETE, entity = "PhotoGallery")
    public void delete(Long id) {
        getOrThrow(id).setStatus(Status.DELETED);
    }

    @Override
    @CacheEvict(value = CacheNames.PHOTO_GALLERIES, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "PhotoGallery")
    public void activeOrDisabled(Long id) {
        PhotoGallery entity = getOrThrow(id);
        if (entity.getStatus() == Status.ACTIVE) {
            entity.setStatus(Status.DISABLED);
        } else if (entity.getStatus() == Status.DISABLED) {
            entity.setStatus(Status.ACTIVE);
        }
    }

    private PhotoGallery getOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Photo gallery not found with id = " + id));
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
