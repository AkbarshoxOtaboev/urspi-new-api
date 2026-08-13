package uz.urspi.newurspi.department.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.urspi.newurspi.department.Department;
import uz.urspi.newurspi.department.response.DepartmentResponse;
import uz.urspi.newurspi.faculty.mapper.FacultyMapper;

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
                .name(department.getName())
                .description(department.getDescription())
                .faculty(facultyMapper.toResponse(department.getFaculty()))
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
}
