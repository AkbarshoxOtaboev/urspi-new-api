package uz.urspi.newurspi.study_year.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.urspi.newurspi.audit.AuditAction;
import uz.urspi.newurspi.audit.Auditable;
import uz.urspi.newurspi.exceptions.BadRequestException;
import uz.urspi.newurspi.exceptions.ResourceNotFoundException;
import uz.urspi.newurspi.study_year.StudyYear;
import uz.urspi.newurspi.study_year.dto.StudyYearDTO;
import uz.urspi.newurspi.study_year.mapper.StudyYearMapper;
import uz.urspi.newurspi.study_year.repository.StudyYearRepository;
import uz.urspi.newurspi.study_year.response.StudyYearResponse;
import uz.urspi.newurspi.study_year.service.StudyYearService;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class StudyYearServiceImpl implements StudyYearService {
    private final StudyYearRepository repository;
    private final StudyYearMapper mapper;

    @Override
    @CacheEvict(value = "studyYears", allEntries = true)
    @Auditable(
            action = AuditAction.CREATE,
            entity = "StudyYear"
    )
    public StudyYearResponse create(StudyYearDTO dto) {
        if (repository.existsByYear(dto.getYear())) {
            throw new BadRequestException("Study year with this year already exists");
        }

        String username = currentUsername();

        StudyYear studyYear = StudyYear.builder()
                .year(dto.getYear())
                .status(Status.ACTIVE)
                .createdUsername(username)
                .build();

        StudyYear saved = repository.save(studyYear);
        log.info("Study year created with id {}", saved.getId());
        return mapper.toResponse(saved);
    }

    @Override
    @Cacheable(value = "studyYears", key = "#id")
    @Auditable(
            action = AuditAction.READ,
            entity = "StudyYear"
    )
    public StudyYearResponse findById(Long id) {
        log.info("Find study year by id {}", id);
        return mapper.toResponse(getStudyYearOrThrow(id));
    }

    @Override
    @Cacheable(value = "studyYears")
    @Auditable(
            action = AuditAction.READ,
            entity = "StudyYear"
    )
    public List<StudyYearResponse> fetchAllStudyYears() {
        log.info("Fetch all study years");
        return mapper.toResponseList(repository.findAll());
    }

    @Override
    @CacheEvict(value = "studyYears", key = "#id")
    @Auditable(
            action = AuditAction.UPDATE,
            entity = "StudyYear"
    )
    public StudyYearResponse update(Long id, StudyYearDTO dto) {
        log.info("Update study year with id {}", id);

        StudyYear studyYear = getStudyYearOrThrow(id);

        boolean yearChanged = !studyYear.getYear().equalsIgnoreCase(dto.getYear());

        if (yearChanged && repository.existsByYear(dto.getYear())) {
            throw new BadRequestException("Study year with this year already exists");
        }

        studyYear.setYear(dto.getYear());

        StudyYear updated = repository.save(studyYear);
        return mapper.toResponse(updated);
    }

    @Override
    @CacheEvict(value = "studyYears", key = "#id")
    @Auditable(
            action = AuditAction.DELETE,
            entity = "StudyYear"
    )
    public void delete(Long id) {
        log.info("Delete study year by id {}", id);
        StudyYear studyYear = getStudyYearOrThrow(id);
        studyYear.setStatus(Status.DELETED);
    }

    @Override
    @CacheEvict(value = "studyYears", key = "#id")
    @Auditable(
            action = AuditAction.UPDATE,
            entity = "StudyYear"
    )
    public void activeOrDisabledStudyYear(Long id) {
        log.info("Disable or active study year with id {}", id);
        StudyYear studyYear = getStudyYearOrThrow(id);
        if (studyYear.getStatus() == Status.ACTIVE) {
            log.info("Disabled study year {}", studyYear.getYear());
            studyYear.setStatus(Status.DISABLED);
        } else if (studyYear.getStatus() == Status.DISABLED) {
            log.info("Activate study year {}", studyYear.getYear());
            studyYear.setStatus(Status.ACTIVE);
        }
    }

    private StudyYear getStudyYearOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Study year not found with id = " + id));
    }

    private String currentUsername() {
        return Objects.requireNonNull(SecurityContextHolder
                        .getContext()
                        .getAuthentication())
                .getName();
    }
}
