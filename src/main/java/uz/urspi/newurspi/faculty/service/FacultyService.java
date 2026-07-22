package uz.urspi.newurspi.faculty.service;

import uz.urspi.newurspi.faculty.dto.FacultyDTO;
import uz.urspi.newurspi.faculty.response.FacultyResponse;

import java.util.List;

public interface FacultyService {
    FacultyResponse create(FacultyDTO dto);
    FacultyResponse findById(Long id);
    List<FacultyResponse> fetchAllFaculties();
    FacultyResponse update(Long id, FacultyDTO dto);
    void delete(Long id);
    void activeOrDisabledFaculty(Long id);
}
