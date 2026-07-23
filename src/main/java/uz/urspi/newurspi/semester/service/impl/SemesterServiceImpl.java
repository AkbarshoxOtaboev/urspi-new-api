package uz.urspi.newurspi.semester.service.impl;

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
import uz.urspi.newurspi.semester.Semester;
import uz.urspi.newurspi.semester.dto.SemesterDTO;
import uz.urspi.newurspi.semester.mapper.SemesterMapper;
import uz.urspi.newurspi.semester.repository.SemesterRepository;
import uz.urspi.newurspi.semester.response.SemesterResponse;
import uz.urspi.newurspi.semester.service.SemesterService;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SemesterServiceImpl implements SemesterService {
    private final SemesterRepository repository;
    private final SemesterMapper mapper;

    @Override
    @CacheEvict(value = "semesters", allEntries = true)
    @Auditable(
            action = AuditAction.CREATE,
            entity = "Semester"
    )
    public SemesterResponse create(SemesterDTO dto) {
        if (repository.existsByName(dto.getName())) {
            throw new BadRequestException("Semester with this name already exists");
        }

        String username = currentUsername();

        Semester semester = Semester.builder()
                .name(dto.getName())
                .status(Status.ACTIVE)
                .createdUsername(username)
                .build();

        Semester saved = repository.save(semester);
        log.info("Semester created with id {}", saved.getId());
        return mapper.toResponse(saved);
    }

    @Override
    @Cacheable(value = "semesters", key = "#id")
    @Auditable(
            action = AuditAction.READ,
            entity = "Semester"
    )
    public SemesterResponse findById(Long id) {
        log.info("Find semester by id {}", id);
        return mapper.toResponse(getSemesterOrThrow(id));
    }

    @Override
    @Cacheable(value = "semesters")
    @Auditable(
            action = AuditAction.READ,
            entity = "Semester"
    )
    public List<SemesterResponse> fetchAllSemesters() {
        log.info("Fetch all semesters");
        return mapper.toResponseList(repository.findAll());
    }

    @Override
    @CacheEvict(value = "semesters", key = "#id")
    @Auditable(
            action = AuditAction.UPDATE,
            entity = "Semester"
    )
    public SemesterResponse update(Long id, SemesterDTO dto) {
        log.info("Update semester with id {}", id);

        Semester semester = getSemesterOrThrow(id);

        boolean nameChanged = !semester.getName().equalsIgnoreCase(dto.getName());

        if (nameChanged && repository.existsByName(dto.getName())) {
            throw new BadRequestException("Semester with this name already exists");
        }

        semester.setName(dto.getName());

        Semester updated = repository.save(semester);
        return mapper.toResponse(updated);
    }

    @Override
    @CacheEvict(value = "semesters", key = "#id")
    @Auditable(
            action = AuditAction.DELETE,
            entity = "Semester"
    )
    public void delete(Long id) {
        log.info("Delete semester by id {}", id);
        Semester semester = getSemesterOrThrow(id);
        semester.setStatus(Status.DELETED);
    }

    @Override
    @CacheEvict(value = "semesters", key = "#id")
    @Auditable(
            action = AuditAction.UPDATE,
            entity = "Semester"
    )
    public void activeOrDisabledSemester(Long id) {
        log.info("Disable or active semester with id {}", id);
        Semester semester = getSemesterOrThrow(id);
        if (semester.getStatus() == Status.ACTIVE) {
            log.info("Disabled semester {}", semester.getName());
            semester.setStatus(Status.DISABLED);
        } else if (semester.getStatus() == Status.DISABLED) {
            log.info("Activate semester {}", semester.getName());
            semester.setStatus(Status.ACTIVE);
        }
    }

    private Semester getSemesterOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Semester not found with id = " + id));
    }

    private String currentUsername() {
        return Objects.requireNonNull(SecurityContextHolder
                        .getContext()
                        .getAuthentication())
                .getName();
    }
}
