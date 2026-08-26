package uz.urspi.newurspi.facultystaff.service.impl;

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
import uz.urspi.newurspi.faculty.repository.FacultyRepository;
import uz.urspi.newurspi.facultystaff.FacultyStaff;
import uz.urspi.newurspi.facultystaff.dto.FacultyStaffDTO;
import uz.urspi.newurspi.facultystaff.mapper.FacultyStaffMapper;
import uz.urspi.newurspi.facultystaff.repository.FacultyStaffRepository;
import uz.urspi.newurspi.facultystaff.response.FacultyStaffLocalizedResponse;
import uz.urspi.newurspi.facultystaff.response.FacultyStaffResponse;
import uz.urspi.newurspi.facultystaff.service.FacultyStaffService;
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
public class FacultyStaffServiceImpl implements FacultyStaffService {
    private final FacultyStaffRepository repository;
    private final FacultyRepository facultyRepository;
    private final FacultyStaffMapper mapper;
    private final StorageService storageService;

    @Override
    @CacheEvict(value = CacheNames.FACULTY_STAFF, allEntries = true)
    @Auditable(action = AuditAction.CREATE, entity = "FacultyStaff")
    public FacultyStaffResponse create(FacultyStaffDTO dto) {
        if (dto.getEmail() != null && !dto.getEmail().isBlank()
                && repository.existsByEmailIgnoreCase(dto.getEmail())) {
            throw new BadRequestException("Faculty staff with this email already exists");
        }

        Faculty faculty = getFacultyOrThrow(dto.getFacultyId());

        FacultyStaff saved = repository.save(FacultyStaff.builder()
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
                .sortOrder(dto.getSortOrder() != null ? dto.getSortOrder() : 0)
                .faculty(faculty)
                .status(Status.ACTIVE)
                .createdUsername(currentUsername())
                .build());

        log.info("FacultyStaff created with id {}", saved.getId());
        return mapper.toResponse(saved);
    }

    @Override
    @Cacheable(value = CacheNames.FACULTY_STAFF, key = "#id")
    @Auditable(action = AuditAction.READ, entity = "FacultyStaff")
    public FacultyStaffResponse findById(Long id) {
        return mapper.toResponse(getOrThrow(id));
    }

    @Override
    @Cacheable(value = CacheNames.FACULTY_STAFF, key = "'all'")
    @Auditable(action = AuditAction.READ, entity = "FacultyStaff")
    public List<FacultyStaffResponse> fetchAll() {
        return mapper.toResponseList(repository.findAllByOrderBySortOrderAsc());
    }

    @Override
    @Cacheable(value = CacheNames.FACULTY_STAFF, key = "'lang_' + #lang.name()")
    @Auditable(action = AuditAction.READ, entity = "FacultyStaff")
    public List<FacultyStaffLocalizedResponse> fetchAllByLang(Language lang) {
        return mapper.toLocalizedResponseList(repository.findAllByOrderBySortOrderAsc(), lang);
    }

    @Override
    @Cacheable(value = CacheNames.FACULTY_STAFF, key = "'faculty_' + #facultyId + '_lang_' + #lang.name()")
    @Auditable(action = AuditAction.READ, entity = "FacultyStaff")
    public List<FacultyStaffLocalizedResponse> fetchByFacultyIdByLang(Long facultyId, Language lang) {
        getFacultyOrThrow(facultyId);
        return mapper.toLocalizedResponseList(
                repository.findAllByFacultyIdOrderBySortOrderAsc(facultyId), lang);
    }

    @Override
    @CacheEvict(value = CacheNames.FACULTY_STAFF, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "FacultyStaff")
    public FacultyStaffResponse update(Long id, FacultyStaffDTO dto) {
        FacultyStaff staff = getOrThrow(id);

        if (dto.getEmail() != null && !dto.getEmail().isBlank()
                && (staff.getEmail() == null || !staff.getEmail().equalsIgnoreCase(dto.getEmail()))
                && repository.existsByEmailIgnoreCase(dto.getEmail())) {
            throw new BadRequestException("Faculty staff with this email already exists");
        }

        Faculty faculty = getFacultyOrThrow(dto.getFacultyId());

        staff.setFullNameUz(dto.getFullNameUz());
        staff.setFullNameRu(dto.getFullNameRu());
        staff.setFullNameEn(dto.getFullNameEn());
        staff.setPhoneNumber(dto.getPhoneNumber());
        staff.setEmail(dto.getEmail());
        staff.setPositionTitleUz(dto.getPositionTitleUz());
        staff.setPositionTitleRu(dto.getPositionTitleRu());
        staff.setPositionTitleEn(dto.getPositionTitleEn());
        staff.setSortOrder(dto.getSortOrder() != null ? dto.getSortOrder() : 0);
        staff.setFaculty(faculty);

        String photoLink = storeFile(dto.getPhoto());
        if (photoLink != null) {
            staff.setPhotoLink(photoLink);
        }
        String cvLink = storeFile(dto.getCv());
        if (cvLink != null) {
            staff.setCvLink(cvLink);
        }

        return mapper.toResponse(repository.save(staff));
    }

    @Override
    @CacheEvict(value = CacheNames.FACULTY_STAFF, allEntries = true)
    @Auditable(action = AuditAction.DELETE, entity = "FacultyStaff")
    public void delete(Long id) {
        getOrThrow(id).setStatus(Status.DELETED);
    }

    @Override
    @CacheEvict(value = CacheNames.FACULTY_STAFF, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "FacultyStaff")
    public void activeOrDisabled(Long id) {
        FacultyStaff staff = getOrThrow(id);
        if (staff.getStatus() == Status.ACTIVE) {
            staff.setStatus(Status.DISABLED);
        } else if (staff.getStatus() == Status.DISABLED) {
            staff.setStatus(Status.ACTIVE);
        }
    }

    private FacultyStaff getOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty staff not found with id = " + id));
    }

    private Faculty getFacultyOrThrow(Long facultyId) {
        return facultyRepository.findById(facultyId)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with id = " + facultyId));
    }

    private String currentUsername() {
        return Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
    }

    private String storeFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        return "/api/files/" + storageService.uploadFile(file);
    }
}
