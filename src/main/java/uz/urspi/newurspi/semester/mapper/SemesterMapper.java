package uz.urspi.newurspi.semester.mapper;

import org.springframework.stereotype.Component;
import uz.urspi.newurspi.semester.Semester;
import uz.urspi.newurspi.semester.response.SemesterResponse;

import java.util.List;

@Component
public class SemesterMapper {

    public SemesterResponse toResponse(Semester semester) {
        if (semester == null) {
            return null;
        }
        return SemesterResponse.builder()
                .id(semester.getId())
                .name(semester.getName())
                .status(semester.getStatus())
                .createdAt(semester.getCreatedAt())
                .updatedAt(semester.getUpdatedAt())
                .build();
    }

    public List<SemesterResponse> toResponseList(List<Semester> semesters) {
        return semesters.stream().map(this::toResponse).toList();
    }
}
