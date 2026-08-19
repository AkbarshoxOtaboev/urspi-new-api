package uz.urspi.newurspi.leader.service.impl;

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
import uz.urspi.newurspi.leader.Leader;
import uz.urspi.newurspi.leader.dto.LeaderDTO;
import uz.urspi.newurspi.leader.mapper.LeaderMapper;
import uz.urspi.newurspi.leader.repository.LeaderRepository;
import uz.urspi.newurspi.leader.response.LeaderLocalizedResponse;
import uz.urspi.newurspi.leader.response.LeaderResponse;
import uz.urspi.newurspi.leader.service.LeaderService;
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
public class LeaderServiceImpl implements LeaderService {
    private final LeaderRepository repository;
    private final LeaderMapper mapper;
    private final StorageService storageService;

    @Override
    @CacheEvict(value = CacheNames.LEADERS, allEntries = true)
    @Auditable(action = AuditAction.CREATE, entity = "Leader")
    public LeaderResponse create(LeaderDTO dto) {
        String username = currentUsername();

        Leader leader = Leader.builder()
                .fullNameUz(dto.getFullNameUz())
                .fullNameRu(dto.getFullNameRu())
                .fullNameEn(dto.getFullNameEn())
                .positionTitleUz(dto.getPositionTitleUz())
                .positionTitleRu(dto.getPositionTitleRu())
                .positionTitleEn(dto.getPositionTitleEn())
                .addressUz(dto.getAddressUz())
                .addressRu(dto.getAddressRu())
                .addressEn(dto.getAddressEn())
                .receptionTimeUz(dto.getReceptionTimeUz())
                .receptionTimeRu(dto.getReceptionTimeRu())
                .receptionTimeEn(dto.getReceptionTimeEn())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .photoLink(storeFile(dto.getPhoto()))
                .sortOrder(dto.getSortOrder())
                .status(Status.ACTIVE)
                .createdUsername(username)
                .build();

        Leader saved = repository.save(leader);
        log.info("Leader created with id {}", saved.getId());
        return mapper.toResponse(saved);
    }

    @Override
    @Cacheable(value = CacheNames.LEADERS, key = "#id")
    @Auditable(action = AuditAction.READ, entity = "Leader")
    public LeaderResponse findById(Long id) {
        log.info("Find leader by id {}", id);
        return mapper.toResponse(getLeaderOrThrow(id));
    }

    @Override
    @Cacheable(value = CacheNames.LEADERS, key = "'all'")
    @Auditable(action = AuditAction.READ, entity = "Leader")
    public List<LeaderResponse> fetchAllLeaders() {
        log.info("Fetch all leaders");
        return mapper.toResponseList(repository.findAllByOrderBySortOrderAsc());
    }

    @Override
    @Cacheable(value = CacheNames.LEADERS, key = "'lang_' + #lang.name()")
    @Auditable(action = AuditAction.READ, entity = "Leader")
    public List<LeaderLocalizedResponse> fetchAllLeadersByLang(Language lang) {
        log.info("Fetch all leaders by language {}", lang);
        return mapper.toLocalizedResponseList(repository.findAllByOrderBySortOrderAsc(), lang);
    }

    @Override
    @CacheEvict(value = CacheNames.LEADERS, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "Leader")
    public LeaderResponse update(Long id, LeaderDTO dto) {
        log.info("Update leader with id {}", id);

        Leader leader = getLeaderOrThrow(id);

        leader.setFullNameUz(dto.getFullNameUz());
        leader.setFullNameRu(dto.getFullNameRu());
        leader.setFullNameEn(dto.getFullNameEn());
        leader.setPositionTitleUz(dto.getPositionTitleUz());
        leader.setPositionTitleRu(dto.getPositionTitleRu());
        leader.setPositionTitleEn(dto.getPositionTitleEn());
        leader.setAddressUz(dto.getAddressUz());
        leader.setAddressRu(dto.getAddressRu());
        leader.setAddressEn(dto.getAddressEn());
        leader.setReceptionTimeUz(dto.getReceptionTimeUz());
        leader.setReceptionTimeRu(dto.getReceptionTimeRu());
        leader.setReceptionTimeEn(dto.getReceptionTimeEn());
        leader.setPhoneNumber(dto.getPhoneNumber());
        leader.setEmail(dto.getEmail());
        leader.setSortOrder(dto.getSortOrder());

        String photoLink = storeFile(dto.getPhoto());
        if (photoLink != null) {
            leader.setPhotoLink(photoLink);
        }

        Leader updated = repository.save(leader);
        return mapper.toResponse(updated);
    }

    @Override
    @CacheEvict(value = CacheNames.LEADERS, allEntries = true)
    @Auditable(action = AuditAction.DELETE, entity = "Leader")
    public void delete(Long id) {
        log.info("Delete leader by id {}", id);
        Leader leader = getLeaderOrThrow(id);
        leader.setStatus(Status.DELETED);
    }

    @Override
    @CacheEvict(value = CacheNames.LEADERS, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "Leader")
    public void activeOrDisabledLeader(Long id) {
        log.info("Disable or active leader with id {}", id);
        Leader leader = getLeaderOrThrow(id);
        if (leader.getStatus() == Status.ACTIVE) {
            log.info("Disabled leader {}", leader.getFullNameUz());
            leader.setStatus(Status.DISABLED);
        } else if (leader.getStatus() == Status.DISABLED) {
            log.info("Activate leader {}", leader.getFullNameUz());
            leader.setStatus(Status.ACTIVE);
        }
    }

    private Leader getLeaderOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leader not found with id = " + id));
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
