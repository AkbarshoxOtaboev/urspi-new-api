package uz.urspi.newurspi.academicdegree.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.urspi.newurspi.academicdegree.AcademicDegree;
import uz.urspi.newurspi.academicdegree.dto.AcademicDegreeDTO;
import uz.urspi.newurspi.academicdegree.mapper.AcademicDegreeMapper;
import uz.urspi.newurspi.academicdegree.repository.AcademicDegreeRepository;
import uz.urspi.newurspi.academicdegree.response.AcademicDegreeResponse;
import uz.urspi.newurspi.academicdegree.service.AcademicDegreeService;
import uz.urspi.newurspi.audit.AuditAction;
import uz.urspi.newurspi.audit.Auditable;
import uz.urspi.newurspi.exceptions.BadRequestException;
import uz.urspi.newurspi.exceptions.ResourceNotFoundException;
import uz.urspi.newurspi.utils.CacheNames;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AcademicDegreeServiceImpl implements AcademicDegreeService {
    private final AcademicDegreeRepository repository;
    private final AcademicDegreeMapper mapper;

    @Override
    @CacheEvict(value = {CacheNames.ACADEMIC_DEGREES, CacheNames.TEACHERS}, allEntries = true)
    @Auditable(action = AuditAction.CREATE, entity = "AcademicDegree")
    public AcademicDegreeResponse create(AcademicDegreeDTO dto) {
        if (repository.existsByNameIgnoreCase(dto.getName())) {
            throw new BadRequestException("Academic degree with this name already exists");
        }

        AcademicDegree academicDegree = AcademicDegree.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .status(Status.ACTIVE)
                .createdUsername(currentUsername())
                .build();

        AcademicDegree saved = repository.save(academicDegree);
        log.info("Academic degree created with id {}", saved.getId());
        return mapper.toResponse(saved);
    }

    @Override
    @Cacheable(value = CacheNames.ACADEMIC_DEGREES, key = "#id")
    @Auditable(action = AuditAction.READ, entity = "AcademicDegree")
    public AcademicDegreeResponse findById(Long id) {
        log.info("Find academic degree by id {}", id);
        return mapper.toResponse(getAcademicDegreeOrThrow(id));
    }

    @Override
    @Cacheable(value = CacheNames.ACADEMIC_DEGREES, key = "'all'")
    @Auditable(action = AuditAction.READ, entity = "AcademicDegree")
    public List<AcademicDegreeResponse> fetchAllAcademicDegrees() {
        log.info("Fetch all academic degrees");
        return mapper.toResponseList(repository.findAll());
    }

    @Override
    @CacheEvict(value = {CacheNames.ACADEMIC_DEGREES, CacheNames.TEACHERS}, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "AcademicDegree")
    public AcademicDegreeResponse update(Long id, AcademicDegreeDTO dto) {
        log.info("Update academic degree with id {}", id);
        AcademicDegree academicDegree = getAcademicDegreeOrThrow(id);

        if (!academicDegree.getName().equalsIgnoreCase(dto.getName())
                && repository.existsByNameIgnoreCase(dto.getName())) {
            throw new BadRequestException("Academic degree with this name already exists");
        }

        academicDegree.setName(dto.getName());
        academicDegree.setDescription(dto.getDescription());
        return mapper.toResponse(repository.save(academicDegree));
    }

    @Override
    @CacheEvict(value = {CacheNames.ACADEMIC_DEGREES, CacheNames.TEACHERS}, allEntries = true)
    @Auditable(action = AuditAction.DELETE, entity = "AcademicDegree")
    public void delete(Long id) {
        log.info("Delete academic degree by id {}", id);
        AcademicDegree academicDegree = getAcademicDegreeOrThrow(id);
        academicDegree.setStatus(Status.DELETED);
    }

    @Override
    @CacheEvict(value = {CacheNames.ACADEMIC_DEGREES, CacheNames.TEACHERS}, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "AcademicDegree")
    public void activeOrDisabledAcademicDegree(Long id) {
        log.info("Disable or active academic degree with id {}", id);
        AcademicDegree academicDegree = getAcademicDegreeOrThrow(id);
        if (academicDegree.getStatus() == Status.ACTIVE) {
            log.info("Disabled academic degree {}", academicDegree.getName());
            academicDegree.setStatus(Status.DISABLED);
        } else if (academicDegree.getStatus() == Status.DISABLED) {
            log.info("Activate academic degree {}", academicDegree.getName());
            academicDegree.setStatus(Status.ACTIVE);
        }
    }

    private AcademicDegree getAcademicDegreeOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Academic degree not found with id = " + id));
    }

    private String currentUsername() {
        return Objects.requireNonNull(SecurityContextHolder
                        .getContext()
                        .getAuthentication())
                .getName();
    }
}
