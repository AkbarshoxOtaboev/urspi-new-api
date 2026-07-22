package uz.urspi.newurspi.faculty.mapper;

import org.springframework.stereotype.Component;
import uz.urspi.newurspi.faculty.Faculty;
import uz.urspi.newurspi.faculty.response.FacultyResponse;

import java.util.List;

@Component
public class FacultyMapper {

    public FacultyResponse toResponse(Faculty faculty) {
        if (faculty == null) {
            return null;
        }
        return FacultyResponse.builder()
                .id(faculty.getId())
                .code(faculty.getCode())
                .name(faculty.getName())
                .description(faculty.getDescription())
                .status(faculty.getStatus())
                .createdAt(faculty.getCreatedAt())
                .updatedAt(faculty.getUpdatedAt())
                .build();
    }

    public List<FacultyResponse> toResponseList(List<Faculty> faculties) {
        return faculties.stream().map(this::toResponse).toList();
    }
}
