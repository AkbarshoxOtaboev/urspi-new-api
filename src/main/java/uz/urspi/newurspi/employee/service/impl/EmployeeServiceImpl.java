package uz.urspi.newurspi.employee.service.impl;

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
import uz.urspi.newurspi.center.Center;
import uz.urspi.newurspi.center.repository.CenterRepository;
import uz.urspi.newurspi.employee.Employee;
import uz.urspi.newurspi.employee.dto.EmployeeDTO;
import uz.urspi.newurspi.employee.mapper.EmployeeMapper;
import uz.urspi.newurspi.employee.repository.EmployeeRepository;
import uz.urspi.newurspi.employee.response.EmployeeLocalizedResponse;
import uz.urspi.newurspi.employee.response.EmployeeResponse;
import uz.urspi.newurspi.employee.service.EmployeeService;
import uz.urspi.newurspi.exceptions.BadRequestException;
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
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository repository;
    private final CenterRepository centerRepository;
    private final EmployeeMapper mapper;
    private final StorageService storageService;

    @Override
    @CacheEvict(value = CacheNames.EMPLOYEES, allEntries = true)
    @Auditable(action = AuditAction.CREATE, entity = "Employee")
    public EmployeeResponse create(EmployeeDTO dto) {
        if (repository.existsByEmailIgnoreCase(dto.getEmail())) {
            throw new BadRequestException("Employee with this email already exists");
        }

        Center center = getCenterOrThrow(dto.getCenterId());
        String username = currentUsername();

        Employee employee = Employee.builder()
                .fullNameUz(dto.getFullNameUz())
                .fullNameRu(dto.getFullNameRu())
                .fullNameEn(dto.getFullNameEn())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .photoLink(storeFile(dto.getPhoto()))
                .cvLink(storeFile(dto.getCv()))
                .positionTitleUz(dto.getPositionTitleUz())
                .positionTitleRu(dto.getPositionTitleRu())
                .positionTitleEn(dto.getPositionTitleEn())
                .sortOrder(dto.getSortOrder())
                .center(center)
                .status(Status.ACTIVE)
                .createdUsername(username)
                .build();

        Employee saved = repository.save(employee);
        log.info("Employee created with id {}", saved.getId());
        return mapper.toResponse(saved);
    }

    @Override
    @Cacheable(value = CacheNames.EMPLOYEES, key = "#id")
    @Auditable(action = AuditAction.READ, entity = "Employee")
    public EmployeeResponse findById(Long id) {
        log.info("Find employee by id {}", id);
        return mapper.toResponse(getEmployeeOrThrow(id));
    }

    @Override
    @Cacheable(value = CacheNames.EMPLOYEES, key = "'all'")
    @Auditable(action = AuditAction.READ, entity = "Employee")
    public List<EmployeeResponse> fetchAllEmployees() {
        log.info("Fetch all employees");
        return mapper.toResponseList(repository.findAllByOrderBySortOrderAsc());
    }

    @Override
    @Cacheable(value = CacheNames.EMPLOYEES, key = "'lang_' + #lang.name()")
    @Auditable(action = AuditAction.READ, entity = "Employee")
    public List<EmployeeLocalizedResponse> fetchAllEmployeesByLang(Language lang) {
        log.info("Fetch all employees by language {}", lang);
        return mapper.toLocalizedResponseList(repository.findAllByOrderBySortOrderAsc(), lang);
    }

    @Override
    @Cacheable(value = CacheNames.EMPLOYEES, key = "'center_' + #centerId + '_lang_' + #lang.name()")
    @Auditable(action = AuditAction.READ, entity = "Employee")
    public List<EmployeeLocalizedResponse> fetchByCenterIdByLang(Long centerId, Language lang) {
        log.info("Fetch employees by center id {} and language {}", centerId, lang);
        return mapper.toLocalizedResponseList(repository.findAllByCenterIdOrderBySortOrderAsc(centerId), lang);
    }

    @Override
    @CacheEvict(value = CacheNames.EMPLOYEES, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "Employee")
    public EmployeeResponse update(Long id, EmployeeDTO dto) {
        log.info("Update employee with id {}", id);

        Employee employee = getEmployeeOrThrow(id);

        if (!employee.getEmail().equalsIgnoreCase(dto.getEmail()) && repository.existsByEmailIgnoreCase(dto.getEmail())) {
            throw new BadRequestException("Employee with this email already exists");
        }

        Center center = getCenterOrThrow(dto.getCenterId());

        employee.setFullNameUz(dto.getFullNameUz());
        employee.setFullNameRu(dto.getFullNameRu());
        employee.setFullNameEn(dto.getFullNameEn());
        employee.setPhoneNumber(dto.getPhoneNumber());
        employee.setEmail(dto.getEmail());
        employee.setPositionTitleUz(dto.getPositionTitleUz());
        employee.setPositionTitleRu(dto.getPositionTitleRu());
        employee.setPositionTitleEn(dto.getPositionTitleEn());
        employee.setSortOrder(dto.getSortOrder());
        employee.setCenter(center);

        String photoLink = storeFile(dto.getPhoto());
        if (photoLink != null) {
            employee.setPhotoLink(photoLink);
        }

        String cvLink = storeFile(dto.getCv());
        if (cvLink != null) {
            employee.setCvLink(cvLink);
        }

        Employee updated = repository.save(employee);
        return mapper.toResponse(updated);
    }

    @Override
    @CacheEvict(value = CacheNames.EMPLOYEES, allEntries = true)
    @Auditable(action = AuditAction.DELETE, entity = "Employee")
    public void delete(Long id) {
        log.info("Delete employee by id {}", id);
        Employee employee = getEmployeeOrThrow(id);
        employee.setStatus(Status.DELETED);
    }

    @Override
    @CacheEvict(value = CacheNames.EMPLOYEES, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "Employee")
    public void activeOrDisabledEmployee(Long id) {
        log.info("Disable or active employee with id {}", id);
        Employee employee = getEmployeeOrThrow(id);
        if (employee.getStatus() == Status.ACTIVE) {
            log.info("Disabled employee {}", employee.getFullNameUz());
            employee.setStatus(Status.DISABLED);
        } else if (employee.getStatus() == Status.DISABLED) {
            log.info("Activate employee {}", employee.getFullNameUz());
            employee.setStatus(Status.ACTIVE);
        }
    }

    private Employee getEmployeeOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id = " + id));
    }

    private Center getCenterOrThrow(Long centerId) {
        return centerRepository.findById(centerId)
                .orElseThrow(() -> new ResourceNotFoundException("Center not found with id = " + centerId));
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
