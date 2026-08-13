package uz.urspi.newurspi.teacher.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.urspi.newurspi.academicdegree.AcademicDegree;
import uz.urspi.newurspi.academicdegree.repository.AcademicDegreeRepository;
import uz.urspi.newurspi.audit.AuditAction;
import uz.urspi.newurspi.audit.Auditable;
import uz.urspi.newurspi.department.Department;
import uz.urspi.newurspi.department.repository.DepartmentRepository;
import uz.urspi.newurspi.exceptions.BadRequestException;
import uz.urspi.newurspi.exceptions.ResourceNotFoundException;
import uz.urspi.newurspi.faculty.Faculty;
import uz.urspi.newurspi.faculty.repository.FacultyRepository;
import uz.urspi.newurspi.position.Position;
import uz.urspi.newurspi.position.repository.PositionRepository;
import uz.urspi.newurspi.storage.StorageService;
import uz.urspi.newurspi.teacher.Teacher;
import uz.urspi.newurspi.teacher.dto.TeacherDTO;
import uz.urspi.newurspi.teacher.mapper.TeacherMapper;
import uz.urspi.newurspi.teacher.repository.TeacherRepository;
import uz.urspi.newurspi.teacher.response.TeacherResponse;
import uz.urspi.newurspi.teacher.service.TeacherService;
import uz.urspi.newurspi.utils.CacheNames;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository repository;
    private final FacultyRepository facultyRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;
    private final AcademicDegreeRepository academicDegreeRepository;
    private final StorageService storageService;
    private final TeacherMapper mapper;

    @Override
    @CacheEvict(value = CacheNames.TEACHERS, allEntries = true)
    @Auditable(action = AuditAction.CREATE, entity = "Teacher")
    public TeacherResponse create(TeacherDTO dto) {
        if (repository.existsByEmailIgnoreCase(dto.getEmail())) {
            throw new BadRequestException("Teacher with this email already exists");
        }

        Faculty faculty = getFacultyOrThrow(dto.getFacultyId());
        Department department = resolveDepartment(dto.getDepartmentId(), faculty);
        Position position = getPositionOrThrow(dto.getPositionId());
        AcademicDegree academicDegree = getAcademicDegreeOrThrow(dto.getAcademicDegreeId());

        Teacher teacher = Teacher.builder()
                .fullName(dto.getFullName())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .photoLink(storeFile(dto.getPhoto()))
                .cvLink(storeFile(dto.getCv()))
                .faculty(faculty)
                .department(department)
                .position(position)
                .academicDegree(academicDegree)
                .status(Status.ACTIVE)
                .createdUsername(currentUsername())
                .build();

        Teacher saved = repository.save(teacher);
        log.info("Teacher created with id {}", saved.getId());
        return mapper.toResponse(saved);
    }

    @Override
    @Cacheable(value = CacheNames.TEACHERS, key = "#id")
    @Auditable(action = AuditAction.READ, entity = "Teacher")
    public TeacherResponse findById(Long id) {
        log.info("Find teacher by id {}", id);
        return mapper.toResponse(getTeacherOrThrow(id));
    }

    @Override
    @Cacheable(value = CacheNames.TEACHERS, key = "'all'")
    @Auditable(action = AuditAction.READ, entity = "Teacher")
    public List<TeacherResponse> fetchAllTeachers() {
        log.info("Fetch all teachers");
        return mapper.toResponseList(repository.findAll());
    }

    @Override
    @Cacheable(value = CacheNames.TEACHERS, key = "'faculty:' + #facultyId")
    @Auditable(action = AuditAction.READ, entity = "Teacher")
    public List<TeacherResponse> fetchByFacultyId(Long facultyId) {
        log.info("Fetch teachers by faculty id {}", facultyId);
        getFacultyOrThrow(facultyId);
        return mapper.toResponseList(repository.findAllByFacultyId(facultyId));
    }

    @Override
    @Cacheable(value = CacheNames.TEACHERS, key = "'department:' + #departmentId")
    @Auditable(action = AuditAction.READ, entity = "Teacher")
    public List<TeacherResponse> fetchByDepartmentId(Long departmentId) {
        log.info("Fetch teachers by department id {}", departmentId);
        getDepartmentOrThrow(departmentId);
        return mapper.toResponseList(repository.findAllByDepartmentId(departmentId));
    }

    @Override
    @Cacheable(value = CacheNames.TEACHERS, key = "'position:' + #positionId")
    @Auditable(action = AuditAction.READ, entity = "Teacher")
    public List<TeacherResponse> fetchByPositionId(Long positionId) {
        log.info("Fetch teachers by position id {}", positionId);
        getPositionOrThrow(positionId);
        return mapper.toResponseList(repository.findAllByPositionId(positionId));
    }

    @Override
    @Cacheable(value = CacheNames.TEACHERS, key = "'academicDegree:' + #academicDegreeId")
    @Auditable(action = AuditAction.READ, entity = "Teacher")
    public List<TeacherResponse> fetchByAcademicDegreeId(Long academicDegreeId) {
        log.info("Fetch teachers by academic degree id {}", academicDegreeId);
        getAcademicDegreeOrThrow(academicDegreeId);
        return mapper.toResponseList(repository.findAllByAcademicDegreeId(academicDegreeId));
    }

    @Override
    @CacheEvict(value = CacheNames.TEACHERS, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "Teacher")
    public TeacherResponse update(Long id, TeacherDTO dto) {
        log.info("Update teacher with id {}", id);
        Teacher teacher = getTeacherOrThrow(id);

        if (!teacher.getEmail().equalsIgnoreCase(dto.getEmail())
                && repository.existsByEmailIgnoreCase(dto.getEmail())) {
            throw new BadRequestException("Teacher with this email already exists");
        }

        Faculty faculty = getFacultyOrThrow(dto.getFacultyId());
        Department department = resolveDepartment(dto.getDepartmentId(), faculty);
        Position position = getPositionOrThrow(dto.getPositionId());
        AcademicDegree academicDegree = getAcademicDegreeOrThrow(dto.getAcademicDegreeId());

        teacher.setFullName(dto.getFullName());
        teacher.setPhoneNumber(dto.getPhoneNumber());
        teacher.setEmail(dto.getEmail());
        teacher.setFaculty(faculty);
        teacher.setDepartment(department);
        teacher.setPosition(position);
        teacher.setAcademicDegree(academicDegree);

        if (dto.getPhoto() != null && !dto.getPhoto().isEmpty()) {
            teacher.setPhotoLink(storeFile(dto.getPhoto()));
        }
        if (dto.getCv() != null && !dto.getCv().isEmpty()) {
            teacher.setCvLink(storeFile(dto.getCv()));
        }

        return mapper.toResponse(repository.save(teacher));
    }

    @Override
    @CacheEvict(value = CacheNames.TEACHERS, allEntries = true)
    @Auditable(action = AuditAction.DELETE, entity = "Teacher")
    public void delete(Long id) {
        log.info("Delete teacher by id {}", id);
        Teacher teacher = getTeacherOrThrow(id);
        teacher.setStatus(Status.DELETED);
    }

    @Override
    @CacheEvict(value = CacheNames.TEACHERS, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "Teacher")
    public void activeOrDisabledTeacher(Long id) {
        log.info("Disable or active teacher with id {}", id);
        Teacher teacher = getTeacherOrThrow(id);
        if (teacher.getStatus() == Status.ACTIVE) {
            log.info("Disabled teacher {}", teacher.getFullName());
            teacher.setStatus(Status.DISABLED);
        } else if (teacher.getStatus() == Status.DISABLED) {
            log.info("Activate teacher {}", teacher.getFullName());
            teacher.setStatus(Status.ACTIVE);
        }
    }

    private Department resolveDepartment(Long departmentId, Faculty faculty) {
        Department department = getDepartmentOrThrow(departmentId);
        if (!department.getFaculty().getId().equals(faculty.getId())) {
            throw new BadRequestException("Department does not belong to the selected faculty");
        }
        return department;
    }

    private String storeFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        return "/api/files/" + storageService.uploadFile(file);
    }

    private Teacher getTeacherOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id = " + id));
    }

    private Faculty getFacultyOrThrow(Long facultyId) {
        return facultyRepository.findById(facultyId)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with id = " + facultyId));
    }

    private Department getDepartmentOrThrow(Long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id = " + departmentId));
    }

    private Position getPositionOrThrow(Long positionId) {
        return positionRepository.findById(positionId)
                .orElseThrow(() -> new ResourceNotFoundException("Position not found with id = " + positionId));
    }

    private AcademicDegree getAcademicDegreeOrThrow(Long academicDegreeId) {
        return academicDegreeRepository.findById(academicDegreeId)
                .orElseThrow(() -> new ResourceNotFoundException("Academic degree not found with id = " + academicDegreeId));
    }

    private String currentUsername() {
        return Objects.requireNonNull(SecurityContextHolder
                        .getContext()
                        .getAuthentication())
                .getName();
    }
}
