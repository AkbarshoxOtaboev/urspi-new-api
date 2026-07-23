package uz.urspi.newurspi.semester.service;

import uz.urspi.newurspi.semester.dto.SemesterDTO;
import uz.urspi.newurspi.semester.response.SemesterResponse;

import java.util.List;

public interface SemesterService {
    SemesterResponse create(SemesterDTO dto);
    SemesterResponse findById(Long id);
    List<SemesterResponse> fetchAllSemesters();
    SemesterResponse update(Long id, SemesterDTO dto);
    void delete(Long id);
    void activeOrDisabledSemester(Long id);
}
