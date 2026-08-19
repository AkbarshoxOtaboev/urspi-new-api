package uz.urspi.newurspi.employee.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.urspi.newurspi.center.mapper.CenterMapper;
import uz.urspi.newurspi.employee.Employee;
import uz.urspi.newurspi.employee.response.EmployeeLocalizedResponse;
import uz.urspi.newurspi.employee.response.EmployeeResponse;
import uz.urspi.newurspi.utils.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EmployeeMapper {
    private final CenterMapper centerMapper;

    public EmployeeResponse toResponse(Employee employee) {
        if (employee == null) {
            return null;
        }
        return EmployeeResponse.builder()
                .id(employee.getId())
                .fullNameUz(employee.getFullNameUz())
                .fullNameRu(employee.getFullNameRu())
                .fullNameEn(employee.getFullNameEn())
                .phoneNumber(employee.getPhoneNumber())
                .email(employee.getEmail())
                .photoLink(employee.getPhotoLink())
                .cvLink(employee.getCvLink())
                .positionTitleUz(employee.getPositionTitleUz())
                .positionTitleRu(employee.getPositionTitleRu())
                .positionTitleEn(employee.getPositionTitleEn())
                .sortOrder(employee.getSortOrder())
                .center(centerMapper.toResponse(employee.getCenter()))
                .status(employee.getStatus())
                .createdAt(employee.getCreatedAt())
                .updatedAt(employee.getUpdatedAt())
                .build();
    }

    public EmployeeLocalizedResponse toLocalizedResponse(Employee employee, Language lang) {
        if (employee == null) {
            return null;
        }
        return EmployeeLocalizedResponse.builder()
                .id(employee.getId())
                .fullName(getLocalizedFullName(employee, lang))
                .phoneNumber(employee.getPhoneNumber())
                .email(employee.getEmail())
                .photoLink(employee.getPhotoLink())
                .cvLink(employee.getCvLink())
                .positionTitle(getLocalizedPositionTitle(employee, lang))
                .sortOrder(employee.getSortOrder())
                .status(employee.getStatus())
                .createdAt(employee.getCreatedAt())
                .updatedAt(employee.getUpdatedAt())
                .build();
    }

    public List<EmployeeResponse> toResponseList(List<Employee> employees) {
        return employees.stream()
                .map(this::toResponse)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<EmployeeLocalizedResponse> toLocalizedResponseList(List<Employee> employees, Language lang) {
        return employees.stream()
                .map(e -> toLocalizedResponse(e, lang))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private String getLocalizedFullName(Employee employee, Language lang) {
        return switch (lang) {
            case ru -> employee.getFullNameRu();
            case en -> employee.getFullNameEn();
            default -> employee.getFullNameUz();
        };
    }

    private String getLocalizedPositionTitle(Employee employee, Language lang) {
        return switch (lang) {
            case ru -> employee.getPositionTitleRu();
            case en -> employee.getPositionTitleEn();
            default -> employee.getPositionTitleUz();
        };
    }
}
