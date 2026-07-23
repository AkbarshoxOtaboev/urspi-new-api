package uz.urspi.newurspi.study_year.mapper;

import org.springframework.stereotype.Component;
import uz.urspi.newurspi.study_year.StudyYear;
import uz.urspi.newurspi.study_year.response.StudyYearResponse;

import java.util.List;

@Component
public class StudyYearMapper {

    public StudyYearResponse toResponse(StudyYear studyYear) {
        if (studyYear == null) {
            return null;
        }
        return StudyYearResponse.builder()
                .id(studyYear.getId())
                .year(studyYear.getYear())
                .status(studyYear.getStatus())
                .createdAt(studyYear.getCreatedAt())
                .updatedAt(studyYear.getUpdatedAt())
                .build();
    }

    public List<StudyYearResponse> toResponseList(List<StudyYear> studyYears) {
        return studyYears.stream().map(this::toResponse).toList();
    }
}
