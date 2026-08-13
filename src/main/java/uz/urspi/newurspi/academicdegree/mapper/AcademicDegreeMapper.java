package uz.urspi.newurspi.academicdegree.mapper;

import org.springframework.stereotype.Component;
import uz.urspi.newurspi.academicdegree.AcademicDegree;
import uz.urspi.newurspi.academicdegree.response.AcademicDegreeResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AcademicDegreeMapper {

    public AcademicDegreeResponse toResponse(AcademicDegree academicDegree) {
        if (academicDegree == null) {
            return null;
        }
        return AcademicDegreeResponse.builder()
                .id(academicDegree.getId())
                .name(academicDegree.getName())
                .description(academicDegree.getDescription())
                .status(academicDegree.getStatus())
                .createdAt(academicDegree.getCreatedAt())
                .updatedAt(academicDegree.getUpdatedAt())
                .build();
    }

    public List<AcademicDegreeResponse> toResponseList(List<AcademicDegree> academicDegrees) {
        return academicDegrees.stream()
                .map(this::toResponse)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
