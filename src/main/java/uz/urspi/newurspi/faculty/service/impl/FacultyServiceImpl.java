package uz.urspi.newurspi.faculty.service.impl;

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
import uz.urspi.newurspi.exceptions.BadRequestException;
import uz.urspi.newurspi.exceptions.ResourceNotFoundException;
import uz.urspi.newurspi.faculty.Faculty;
import uz.urspi.newurspi.faculty.dto.FacultyDTO;
import uz.urspi.newurspi.faculty.mapper.FacultyMapper;
import uz.urspi.newurspi.faculty.repository.FacultyRepository;
import uz.urspi.newurspi.faculty.response.FacultyLocalizedResponse;
import uz.urspi.newurspi.faculty.response.FacultyResponse;
import uz.urspi.newurspi.faculty.service.FacultyService;
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
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository repository;
    private final FacultyMapper mapper;
    private final StorageService storageService;

    @Override
    @CacheEvict(value = {CacheNames.FACULTIES, CacheNames.DEPARTMENTS, CacheNames.TEACHERS}, allEntries = true)
    @Auditable(
            action = AuditAction.CREATE,
            entity = "Faculty"
    )
    public FacultyResponse create(FacultyDTO dto) {
        if (repository.existsByCode(dto.getCode())) {
            throw new BadRequestException("Faculty code already exists");
        }

        String username = currentUsername();

        Faculty faculty = Faculty.builder()
                .code(dto.getCode())
                .nameUz(dto.getNameUz())
                .nameRu(dto.getNameRu())
                .nameEn(dto.getNameEn())
                .descriptionUz(dto.getDescriptionUz())
                .descriptionRu(dto.getDescriptionRu())
                .descriptionEn(dto.getDescriptionEn())
                .logoLink(storeFile(dto.getLogo()))
                .status(Status.ACTIVE)
                .createdUsername(username)
                .build();

        Faculty saved = repository.save(faculty);
        log.info("Faculty created with id {}", saved.getId());
        return mapper.toResponse(saved);
    }

    @Override
    @Cacheable(value = CacheNames.FACULTIES, key = "#id")
    @Auditable(
            action = AuditAction.READ,
            entity = "Faculty"
    )
    public FacultyResponse findById(Long id) {
        log.info("Find faculty by id {}", id);
        return mapper.toResponse(getFacultyOrThrow(id));
    }

    @Override
    @Cacheable(value = CacheNames.FACULTIES, key = "'all'")
    @Auditable(
            action = AuditAction.READ,
            entity = "Faculty"
    )
    public List<FacultyResponse> fetchAllFaculties() {
        log.info("Fetch all faculties");
        return mapper.toResponseList(repository.findAll());
    }

    @Override
    @Cacheable(value = CacheNames.FACULTIES, key = "'lang_' + #lang.name()")
    @Auditable(
            action = AuditAction.READ,
            entity = "Faculty"
    )
    public List<FacultyLocalizedResponse> fetchAllFacultiesByLang(Language lang) {
        log.info("Fetch all faculties by language {}", lang);
        return mapper.toLocalizedResponseList(repository.findAll(), lang);
    }

    @Override
    @CacheEvict(value = {CacheNames.FACULTIES, CacheNames.DEPARTMENTS, CacheNames.TEACHERS}, allEntries = true)
    @Auditable(
            action = AuditAction.UPDATE,
            entity = "Faculty"
    )
    public FacultyResponse update(Long id, FacultyDTO dto) {
        log.info("Update faculty with id {}", id);

        Faculty faculty = getFacultyOrThrow(id);

        if (!faculty.getCode().equalsIgnoreCase(dto.getCode()) && repository.existsByCode(dto.getCode())) {
            throw new BadRequestException("Faculty code already exists");
        }

        faculty.setCode(dto.getCode());
        faculty.setNameUz(dto.getNameUz());
        faculty.setNameRu(dto.getNameRu());
        faculty.setNameEn(dto.getNameEn());
        faculty.setDescriptionUz(dto.getDescriptionUz());
        faculty.setDescriptionRu(dto.getDescriptionRu());
        faculty.setDescriptionEn(dto.getDescriptionEn());

        String logoLink = storeFile(dto.getLogo());
        if (logoLink != null) {
            faculty.setLogoLink(logoLink);
        }

        Faculty updated = repository.save(faculty);
        return mapper.toResponse(updated);
    }

    @Override
    @CacheEvict(value = {CacheNames.FACULTIES, CacheNames.DEPARTMENTS, CacheNames.TEACHERS}, allEntries = true)
    @Auditable(
            action = AuditAction.DELETE,
            entity = "Faculty"
    )
    public void delete(Long id) {
        log.info("Delete faculty by id {}", id);
        Faculty faculty = getFacultyOrThrow(id);
        faculty.setStatus(Status.DELETED);
    }

    @Override
    @CacheEvict(value = {CacheNames.FACULTIES, CacheNames.DEPARTMENTS, CacheNames.TEACHERS}, allEntries = true)
    @Auditable(
            action = AuditAction.UPDATE,
            entity = "Faculty"
    )
    public void activeOrDisabledFaculty(Long id) {
        log.info("Disable or active faculty with id {}", id);
        Faculty faculty = getFacultyOrThrow(id);
        if (faculty.getStatus() == Status.ACTIVE) {
            log.info("Disabled faculty {}", faculty.getNameUz());
            faculty.setStatus(Status.DISABLED);
        } else if (faculty.getStatus() == Status.DISABLED) {
            log.info("Activate faculty {}", faculty.getNameUz());
            faculty.setStatus(Status.ACTIVE);
        }
    }

    private Faculty getFacultyOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with id = " + id));
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
