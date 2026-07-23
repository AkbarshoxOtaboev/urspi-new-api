package uz.urspi.newurspi.study_year.service;

import uz.urspi.newurspi.study_year.dto.StudyYearDTO;
import uz.urspi.newurspi.study_year.response.StudyYearResponse;

import java.util.List;

public interface StudyYearService {
    StudyYearResponse create(StudyYearDTO dto);
    StudyYearResponse findById(Long id);
    List<StudyYearResponse> fetchAllStudyYears();
    StudyYearResponse update(Long id, StudyYearDTO dto);
    void delete(Long id);
    void activeOrDisabledStudyYear(Long id);
}
