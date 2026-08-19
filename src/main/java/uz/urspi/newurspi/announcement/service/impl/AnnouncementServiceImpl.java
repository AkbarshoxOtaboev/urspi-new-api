package uz.urspi.newurspi.announcement.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.urspi.newurspi.announcement.Announcement;
import uz.urspi.newurspi.announcement.dto.AnnouncementDTO;
import uz.urspi.newurspi.announcement.mapper.AnnouncementMapper;
import uz.urspi.newurspi.announcement.repository.AnnouncementRepository;
import uz.urspi.newurspi.announcement.response.AnnouncementLocalizedResponse;
import uz.urspi.newurspi.announcement.response.AnnouncementResponse;
import uz.urspi.newurspi.announcement.service.AnnouncementService;
import uz.urspi.newurspi.audit.AuditAction;
import uz.urspi.newurspi.audit.Auditable;
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
public class AnnouncementServiceImpl implements AnnouncementService {
    private final AnnouncementRepository repository;
    private final AnnouncementMapper mapper;
    private final StorageService storageService;

    @Override
    @CacheEvict(value = CacheNames.ANNOUNCEMENTS, allEntries = true)
    @Auditable(
            action = AuditAction.CREATE,
            entity = "Announcement"
    )
    public AnnouncementResponse create(AnnouncementDTO dto) {
        String username = currentUsername();

        Announcement announcement = Announcement.builder()
                .titleUz(dto.getTitleUz())
                .titleRu(dto.getTitleRu())
                .titleEn(dto.getTitleEn())
                .contentUz(dto.getContentUz())
                .contentRu(dto.getContentRu())
                .contentEn(dto.getContentEn())
                .imageLink(storeFile(dto.getImage()))
                .status(Status.ACTIVE)
                .createdUsername(username)
                .build();

        Announcement saved = repository.save(announcement);
        log.info("Announcement created with id {}", saved.getId());
        return mapper.toResponse(saved);
    }

    @Override
    @Cacheable(value = CacheNames.ANNOUNCEMENTS, key = "#id")
    @Auditable(
            action = AuditAction.READ,
            entity = "Announcement"
    )
    public AnnouncementResponse findById(Long id) {
        log.info("Find announcement by id {}", id);
        return mapper.toResponse(getAnnouncementOrThrow(id));
    }

    @Override
    @Cacheable(value = CacheNames.ANNOUNCEMENTS, key = "'all'")
    @Auditable(
            action = AuditAction.READ,
            entity = "Announcement"
    )
    public List<AnnouncementResponse> fetchAllAnnouncements() {
        log.info("Fetch all announcements");
        return mapper.toResponseList(repository.findAllByOrderByCreatedAtDesc());
    }

    @Override
    @Cacheable(value = CacheNames.ANNOUNCEMENTS, key = "'lang_' + #lang.name()")
    @Auditable(
            action = AuditAction.READ,
            entity = "Announcement"
    )
    public List<AnnouncementLocalizedResponse> fetchAllAnnouncementsByLang(Language lang) {
        log.info("Fetch all announcements by language {}", lang);
        return mapper.toLocalizedResponseList(repository.findAllByOrderByCreatedAtDesc(), lang);
    }

    @Override
    @CacheEvict(value = CacheNames.ANNOUNCEMENTS, allEntries = true)
    @Auditable(
            action = AuditAction.UPDATE,
            entity = "Announcement"
    )
    public AnnouncementResponse update(Long id, AnnouncementDTO dto) {
        log.info("Update announcement with id {}", id);

        Announcement announcement = getAnnouncementOrThrow(id);

        announcement.setTitleUz(dto.getTitleUz());
        announcement.setTitleRu(dto.getTitleRu());
        announcement.setTitleEn(dto.getTitleEn());
        announcement.setContentUz(dto.getContentUz());
        announcement.setContentRu(dto.getContentRu());
        announcement.setContentEn(dto.getContentEn());

        String imageLink = storeFile(dto.getImage());
        if (imageLink != null) {
            announcement.setImageLink(imageLink);
        }

        Announcement updated = repository.save(announcement);
        return mapper.toResponse(updated);
    }

    @Override
    @CacheEvict(value = CacheNames.ANNOUNCEMENTS, allEntries = true)
    @Auditable(
            action = AuditAction.DELETE,
            entity = "Announcement"
    )
    public void delete(Long id) {
        log.info("Delete announcement by id {}", id);
        Announcement announcement = getAnnouncementOrThrow(id);
        announcement.setStatus(Status.DELETED);
    }

    @Override
    @CacheEvict(value = CacheNames.ANNOUNCEMENTS, allEntries = true)
    @Auditable(
            action = AuditAction.UPDATE,
            entity = "Announcement"
    )
    public void activeOrDisabledAnnouncement(Long id) {
        log.info("Disable or active announcement with id {}", id);
        Announcement announcement = getAnnouncementOrThrow(id);
        if (announcement.getStatus() == Status.ACTIVE) {
            log.info("Disabled announcement {}", announcement.getTitleUz());
            announcement.setStatus(Status.DISABLED);
        } else if (announcement.getStatus() == Status.DISABLED) {
            log.info("Activate announcement {}", announcement.getTitleUz());
            announcement.setStatus(Status.ACTIVE);
        }
    }

    private Announcement getAnnouncementOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Announcement not found with id = " + id));
    }

    private String currentUsername() {
        return Objects.requireNonNull(SecurityContextHolder
                        .getContext()
                        .getAuthentication())
                .getName();
    }

    private String storeFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        return "/api/files/" + storageService.uploadFile(file);
    }
}
