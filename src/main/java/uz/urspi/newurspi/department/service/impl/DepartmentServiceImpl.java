package uz.urspi.newurspi.department.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.urspi.newurspi.audit.AuditAction;
import uz.urspi.newurspi.audit.Auditable;
import uz.urspi.newurspi.department.Department;
import uz.urspi.newurspi.department.dto.DepartmentDTO;
import uz.urspi.newurspi.department.mapper.DepartmentMapper;
import uz.urspi.newurspi.department.repository.DepartmentRepository;
import uz.urspi.newurspi.department.response.DepartmentLocalizedResponse;
import uz.urspi.newurspi.department.response.DepartmentResponse;
import uz.urspi.newurspi.department.service.DepartmentService;
import uz.urspi.newurspi.exceptions.BadRequestException;
import uz.urspi.newurspi.exceptions.ResourceNotFoundException;
import uz.urspi.newurspi.faculty.Faculty;
import uz.urspi.newurspi.faculty.repository.FacultyRepository;
import uz.urspi.newurspi.utils.CacheNames;
import uz.urspi.newurspi.utils.Language;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository repository;
    private final FacultyRepository facultyRepository;
    private final DepartmentMapper mapper;

    @Override
    @CacheEvict(value = {CacheNames.DEPARTMENTS, CacheNames.TEACHERS}, allEntries = true)
    @Auditable(
            action = AuditAction.CREATE,
            entity = "Department"
    )
    public DepartmentResponse create(DepartmentDTO dto) {
        Faculty faculty = getFacultyOrThrow(dto.getFacultyId());

        if (repository.existsByNameUzAndFacultyId(dto.getNameUz(), dto.getFacultyId())) {
            throw new BadRequestException("Department with this name already exists in the faculty");
        }

        String username = currentUsername();

        Department department = Department.builder()
                .nameUz(dto.getNameUz())
                .nameRu(dto.getNameRu())
                .nameEn(dto.getNameEn())
                .descriptionUz(dto.getDescriptionUz())
                .descriptionRu(dto.getDescriptionRu())
                .descriptionEn(dto.getDescriptionEn())
                .faculty(faculty)
                .status(Status.ACTIVE)
                .createdUsername(username)
                .build();

        Department saved = repository.save(department);
        log.info("Department created with id {}", saved.getId());
        return mapper.toResponse(saved);
    }

    @Override
    @Cacheable(value = CacheNames.DEPARTMENTS, key = "#id")
    @Auditable(
            action = AuditAction.READ,
            entity = "Department"
    )
    public DepartmentResponse findById(Long id) {
        log.info("Find department by id {}", id);
        return mapper.toResponse(getDepartmentOrThrow(id));
    }

    @Override
    @Cacheable(value = CacheNames.DEPARTMENTS, key = "'all'")
    @Auditable(
            action = AuditAction.READ,
            entity = "Department"
    )
    public List<DepartmentResponse> fetchAllDepartments() {
        log.info("Fetch all departments");
        return mapper.toResponseList(repository.findAll());
    }

    @Override
    @Cacheable(value = CacheNames.DEPARTMENTS, key = "'faculty:' + #facultyId")
    @Auditable(
            action = AuditAction.READ,
            entity = "Department"
    )
    public List<DepartmentResponse> fetchByFacultyId(Long facultyId) {
        log.info("Fetch departments by faculty id {}", facultyId);
        getFacultyOrThrow(facultyId);
        return mapper.toResponseList(repository.findAllByFacultyId(facultyId));
    }

    @Override
    @CacheEvict(value = {CacheNames.DEPARTMENTS, CacheNames.TEACHERS}, allEntries = true)
    @Auditable(
            action = AuditAction.UPDATE,
            entity = "Department"
    )
    public DepartmentResponse update(Long id, DepartmentDTO dto) {
        log.info("Update department with id {}", id);

        Department department = getDepartmentOrThrow(id);
        Faculty faculty = getFacultyOrThrow(dto.getFacultyId());

        boolean nameOrFacultyChanged = !department.getNameUz().equalsIgnoreCase(dto.getNameUz())
                || !department.getFaculty().getId().equals(dto.getFacultyId());

        if (nameOrFacultyChanged && repository.existsByNameUzAndFacultyId(dto.getNameUz(), dto.getFacultyId())) {
            throw new BadRequestException("Department with this name already exists in the faculty");
        }

        department.setNameUz(dto.getNameUz());
        department.setNameRu(dto.getNameRu());
        department.setNameEn(dto.getNameEn());
        department.setDescriptionUz(dto.getDescriptionUz());
        department.setDescriptionRu(dto.getDescriptionRu());
        department.setDescriptionEn(dto.getDescriptionEn());
        department.setFaculty(faculty);

        Department updated = repository.save(department);
        return mapper.toResponse(updated);
    }

    @Override
    @CacheEvict(value = {CacheNames.DEPARTMENTS, CacheNames.TEACHERS}, allEntries = true)
    @Auditable(
            action = AuditAction.DELETE,
            entity = "Department"
    )
    public void delete(Long id) {
        log.info("Delete department by id {}", id);
        Department department = getDepartmentOrThrow(id);
        department.setStatus(Status.DELETED);
    }

    @Override
    @CacheEvict(value = {CacheNames.DEPARTMENTS, CacheNames.TEACHERS}, allEntries = true)
    @Auditable(
            action = AuditAction.UPDATE,
            entity = "Department"
    )
    public void activeOrDisabledDepartment(Long id) {
        log.info("Disable or active department with id {}", id);
        Department department = getDepartmentOrThrow(id);
        if (department.getStatus() == Status.ACTIVE) {
            log.info("Disabled department {}", department.getNameUz());
            department.setStatus(Status.DISABLED);
        } else if (department.getStatus() == Status.DISABLED) {
            log.info("Activate department {}", department.getNameUz());
            department.setStatus(Status.ACTIVE);
        }
    }

    @Override
    @Cacheable(value = CacheNames.DEPARTMENTS, key = "'all:lang:' + #lang")
    @Auditable(
            action = AuditAction.READ,
            entity = "Department"
    )
    public List<DepartmentLocalizedResponse> fetchAllDepartmentsByLang(Language lang) {
        log.info("Fetch all departments by lang {}", lang);
        return mapper.toLocalizedResponseList(repository.findAll(), lang);
    }

    @Override
    @Cacheable(value = CacheNames.DEPARTMENTS, key = "'faculty:' + #facultyId + ':lang:' + #lang")
    @Auditable(
            action = AuditAction.READ,
            entity = "Department"
    )
    public List<DepartmentLocalizedResponse> fetchByFacultyIdAndLang(Long facultyId, Language lang) {
        log.info("Fetch departments by faculty id {} and lang {}", facultyId, lang);
        getFacultyOrThrow(facultyId);
        return mapper.toLocalizedResponseList(repository.findAllByFacultyId(facultyId), lang);
    }

    private Department getDepartmentOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id = " + id));
    }

    private Faculty getFacultyOrThrow(Long facultyId) {
        return facultyRepository.findById(facultyId)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with id = " + facultyId));
    }

    private String currentUsername() {
        return Objects.requireNonNull(SecurityContextHolder
                        .getContext()
                        .getAuthentication())
                .getName();
    }
}
