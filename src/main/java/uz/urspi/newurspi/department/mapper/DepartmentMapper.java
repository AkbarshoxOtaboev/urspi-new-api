package uz.urspi.newurspi.department.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.urspi.newurspi.department.Department;
import uz.urspi.newurspi.department.response.DepartmentLocalizedResponse;
import uz.urspi.newurspi.department.response.DepartmentResponse;
import uz.urspi.newurspi.faculty.mapper.FacultyMapper;
import uz.urspi.newurspi.utils.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DepartmentMapper {
    private final FacultyMapper facultyMapper;

    public DepartmentResponse toResponse(Department department) {
        if (department == null) {
            return null;
        }
        return DepartmentResponse.builder()
                .id(department.getId())
                .nameUz(department.getNameUz())
                .nameRu(department.getNameRu())
                .nameEn(department.getNameEn())
                .descriptionUz(department.getDescriptionUz())
                .descriptionRu(department.getDescriptionRu())
                .descriptionEn(department.getDescriptionEn())
                .faculty(facultyMapper.toResponse(department.getFaculty()))
                .status(department.getStatus())
                .createdAt(department.getCreatedAt())
                .updatedAt(department.getUpdatedAt())
                .build();
    }

    public DepartmentLocalizedResponse toLocalizedResponse(Department department, Language lang) {
        if (department == null) {
            return null;
        }
        return DepartmentLocalizedResponse.builder()
                .id(department.getId())
                .name(getLocalizedName(department, lang))
                .description(getLocalizedDescription(department, lang))
                .faculty(facultyMapper.toLocalizedResponse(department.getFaculty(), lang))
                .status(department.getStatus())
                .createdAt(department.getCreatedAt())
                .updatedAt(department.getUpdatedAt())
                .build();
    }

    public List<DepartmentResponse> toResponseList(List<Department> departments) {
        return departments.stream()
                .map(this::toResponse)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<DepartmentLocalizedResponse> toLocalizedResponseList(List<Department> departments, Language lang) {
        return departments.stream()
                .map(d -> toLocalizedResponse(d, lang))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private String getLocalizedName(Department department, Language lang) {
        return switch (lang) {
            case ru -> department.getNameRu();
            case en -> department.getNameEn();
            default -> department.getNameUz();
        };
    }

    private String getLocalizedDescription(Department department, Language lang) {
        return switch (lang) {
            case ru -> department.getDescriptionRu();
            case en -> department.getDescriptionEn();
            default -> department.getDescriptionUz();
        };
    }
}
