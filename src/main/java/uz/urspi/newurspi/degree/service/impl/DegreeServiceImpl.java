package uz.urspi.newurspi.degree.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.urspi.newurspi.audit.AuditAction;
import uz.urspi.newurspi.audit.Auditable;
import uz.urspi.newurspi.degree.Degree;
import uz.urspi.newurspi.degree.dto.DegreeDTO;
import uz.urspi.newurspi.degree.mapper.DegreeMapper;
import uz.urspi.newurspi.degree.repository.DegreeRepository;
import uz.urspi.newurspi.degree.response.DegreeResponse;
import uz.urspi.newurspi.degree.service.DegreeService;
import uz.urspi.newurspi.exceptions.BadRequestException;
import uz.urspi.newurspi.exceptions.ResourceNotFoundException;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DegreeServiceImpl implements DegreeService {
    private final DegreeRepository repository;
    private final DegreeMapper mapper;

    @Override
    @CacheEvict(value = "degrees", allEntries = true)
    @Auditable(
            action = AuditAction.CREATE,
            entity = "Degree"
    )
    public DegreeResponse create(DegreeDTO dto) {
        if (repository.existsByName(dto.getName())) {
            throw new BadRequestException("Degree with this name already exists");
        }

        String username = currentUsername();

        Degree degree = Degree.builder()
                .name(dto.getName())
                .status(Status.ACTIVE)
                .createdUsername(username)
                .build();

        Degree saved = repository.save(degree);
        log.info("Degree created with id {}", saved.getId());
        return mapper.toResponse(saved);
    }

    @Override
    @Cacheable(value = "degrees", key = "#id")
    @Auditable(
            action = AuditAction.READ,
            entity = "Degree"
    )
    public DegreeResponse findById(Long id) {
        log.info("Find degree by id {}", id);
        return mapper.toResponse(getDegreeOrThrow(id));
    }

    @Override
    @Cacheable(value = "degrees")
    @Auditable(
            action = AuditAction.READ,
            entity = "Degree"
    )
    public List<DegreeResponse> fetchAllDegrees() {
        log.info("Fetch all degrees");
        return mapper.toResponseList(repository.findAll());
    }

    @Override
    @CacheEvict(value = "degrees", key = "#id")
    @Auditable(
            action = AuditAction.UPDATE,
            entity = "Degree"
    )
    public DegreeResponse update(Long id, DegreeDTO dto) {
        log.info("Update degree with id {}", id);

        Degree degree = getDegreeOrThrow(id);

        boolean nameChanged = !degree.getName().equalsIgnoreCase(dto.getName());

        if (nameChanged && repository.existsByName(dto.getName())) {
            throw new BadRequestException("Degree with this name already exists");
        }

        degree.setName(dto.getName());

        Degree updated = repository.save(degree);
        return mapper.toResponse(updated);
    }

    @Override
    @CacheEvict(value = "degrees", key = "#id")
    @Auditable(
            action = AuditAction.DELETE,
            entity = "Degree"
    )
    public void delete(Long id) {
        log.info("Delete degree by id {}", id);
        Degree degree = getDegreeOrThrow(id);
        degree.setStatus(Status.DELETED);
    }

    @Override
    @CacheEvict(value = "degrees", key = "#id")
    @Auditable(
            action = AuditAction.UPDATE,
            entity = "Degree"
    )
    public void activeOrDisabledDegree(Long id) {
        log.info("Disable or active degree with id {}", id);
        Degree degree = getDegreeOrThrow(id);
        if (degree.getStatus() == Status.ACTIVE) {
            log.info("Disabled degree {}", degree.getName());
            degree.setStatus(Status.DISABLED);
        } else if (degree.getStatus() == Status.DISABLED) {
            log.info("Activate degree {}", degree.getName());
            degree.setStatus(Status.ACTIVE);
        }
    }

    private Degree getDegreeOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Degree not found with id = " + id));
    }

    private String currentUsername() {
        return Objects.requireNonNull(SecurityContextHolder
                        .getContext()
                        .getAuthentication())
                .getName();
    }
}
