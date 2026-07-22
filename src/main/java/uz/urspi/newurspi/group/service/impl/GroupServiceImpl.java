package uz.urspi.newurspi.group.service.impl;

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
import uz.urspi.newurspi.department.repository.DepartmentRepository;
import uz.urspi.newurspi.exceptions.BadRequestException;
import uz.urspi.newurspi.exceptions.ResourceNotFoundException;
import uz.urspi.newurspi.faculty.Faculty;
import uz.urspi.newurspi.faculty.repository.FacultyRepository;
import uz.urspi.newurspi.group.Group;
import uz.urspi.newurspi.group.dto.GroupDTO;
import uz.urspi.newurspi.group.mapper.GroupMapper;
import uz.urspi.newurspi.group.repository.GroupRepository;
import uz.urspi.newurspi.group.response.GroupResponse;
import uz.urspi.newurspi.group.service.GroupService;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GroupServiceImpl implements GroupService {
    private final GroupRepository repository;
    private final FacultyRepository facultyRepository;
    private final DepartmentRepository departmentRepository;
    private final GroupMapper mapper;

    @Override
    @CacheEvict(value = "groups", allEntries = true)
    @Auditable(
            action = AuditAction.CREATE,
            entity = "Group"
    )
    public GroupResponse create(GroupDTO dto) {
        Faculty faculty = getFacultyOrThrow(dto.getFacultyId());
        Department department = resolveDepartment(dto.getDepartmentId(), faculty);

        if (repository.existsByNameAndFacultyId(dto.getName(), dto.getFacultyId())) {
            throw new BadRequestException("Group with this name already exists in the faculty");
        }

        String username = currentUsername();

        Group group = Group.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .faculty(faculty)
                .department(department)
                .status(Status.ACTIVE)
                .createdUsername(username)
                .build();

        Group saved = repository.save(group);
        log.info("Group created with id {}", saved.getId());
        return mapper.toResponse(saved);
    }

    @Override
    @Cacheable(value = "groups", key = "#id")
    @Auditable(
            action = AuditAction.READ,
            entity = "Group"
    )
    public GroupResponse findById(Long id) {
        log.info("Find group by id {}", id);
        return mapper.toResponse(getGroupOrThrow(id));
    }

    @Override
    @Cacheable(value = "groups")
    @Auditable(
            action = AuditAction.READ,
            entity = "Group"
    )
    public List<GroupResponse> fetchAllGroups() {
        log.info("Fetch all groups");
        return mapper.toResponseList(repository.findAll());
    }

    @Override
    @Auditable(
            action = AuditAction.READ,
            entity = "Group"
    )
    public List<GroupResponse> fetchByFacultyId(Long facultyId) {
        log.info("Fetch groups by faculty id {}", facultyId);
        getFacultyOrThrow(facultyId);
        return mapper.toResponseList(repository.findAllByFacultyId(facultyId));
    }

    @Override
    @Auditable(
            action = AuditAction.READ,
            entity = "Group"
    )
    public List<GroupResponse> fetchByDepartmentId(Long departmentId) {
        log.info("Fetch groups by department id {}", departmentId);
        getDepartmentOrThrow(departmentId);
        return mapper.toResponseList(repository.findAllByDepartmentId(departmentId));
    }

    @Override
    @CacheEvict(value = "groups", key = "#id")
    @Auditable(
            action = AuditAction.UPDATE,
            entity = "Group"
    )
    public GroupResponse update(Long id, GroupDTO dto) {
        log.info("Update group with id {}", id);

        Group group = getGroupOrThrow(id);
        Faculty faculty = getFacultyOrThrow(dto.getFacultyId());
        Department department = resolveDepartment(dto.getDepartmentId(), faculty);

        boolean nameOrFacultyChanged = !group.getName().equalsIgnoreCase(dto.getName())
                || !group.getFaculty().getId().equals(dto.getFacultyId());

        if (nameOrFacultyChanged && repository.existsByNameAndFacultyId(dto.getName(), dto.getFacultyId())) {
            throw new BadRequestException("Group with this name already exists in the faculty");
        }

        group.setName(dto.getName());
        group.setDescription(dto.getDescription());
        group.setFaculty(faculty);
        group.setDepartment(department);

        Group updated = repository.save(group);
        return mapper.toResponse(updated);
    }

    @Override
    @CacheEvict(value = "groups", key = "#id")
    @Auditable(
            action = AuditAction.DELETE,
            entity = "Group"
    )
    public void delete(Long id) {
        log.info("Delete group by id {}", id);
        Group group = getGroupOrThrow(id);
        group.setStatus(Status.DELETED);
    }

    @Override
    @CacheEvict(value = "groups", key = "#id")
    @Auditable(
            action = AuditAction.UPDATE,
            entity = "Group"
    )
    public void activeOrDisabledGroup(Long id) {
        log.info("Disable or active group with id {}", id);
        Group group = getGroupOrThrow(id);
        if (group.getStatus() == Status.ACTIVE) {
            log.info("Disabled group {}", group.getName());
            group.setStatus(Status.DISABLED);
        } else if (group.getStatus() == Status.DISABLED) {
            log.info("Activate group {}", group.getName());
            group.setStatus(Status.ACTIVE);
        }
    }

    private Department resolveDepartment(Long departmentId, Faculty faculty) {
        if (departmentId == null) {
            return null;
        }
        Department department = getDepartmentOrThrow(departmentId);
        if (!department.getFaculty().getId().equals(faculty.getId())) {
            throw new BadRequestException("Department does not belong to the selected faculty");
        }
        return department;
    }

    private Group getGroupOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found with id = " + id));
    }

    private Faculty getFacultyOrThrow(Long facultyId) {
        return facultyRepository.findById(facultyId)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with id = " + facultyId));
    }

    private Department getDepartmentOrThrow(Long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id = " + departmentId));
    }

    private String currentUsername() {
        return Objects.requireNonNull(SecurityContextHolder
                        .getContext()
                        .getAuthentication())
                .getName();
    }
}
