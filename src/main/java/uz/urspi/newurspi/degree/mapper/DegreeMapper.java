package uz.urspi.newurspi.degree.mapper;

import org.springframework.stereotype.Component;
import uz.urspi.newurspi.degree.Degree;
import uz.urspi.newurspi.degree.response.DegreeResponse;

import java.util.List;

@Component
public class DegreeMapper {

    public DegreeResponse toResponse(Degree degree) {
        if (degree == null) {
            return null;
        }
        return DegreeResponse.builder()
                .id(degree.getId())
                .name(degree.getName())
                .status(degree.getStatus())
                .createdAt(degree.getCreatedAt())
                .updatedAt(degree.getUpdatedAt())
                .build();
    }

    public List<DegreeResponse> toResponseList(List<Degree> degrees) {
        return degrees.stream().map(this::toResponse).toList();
    }
}
