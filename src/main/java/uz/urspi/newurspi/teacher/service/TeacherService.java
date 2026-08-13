package uz.urspi.newurspi.teacher.service;

import uz.urspi.newurspi.teacher.dto.TeacherDTO;
import uz.urspi.newurspi.teacher.response.TeacherResponse;

import java.util.List;

public interface TeacherService {
    TeacherResponse create(TeacherDTO dto);
    TeacherResponse findById(Long id);
    List<TeacherResponse> fetchAllTeachers();
    List<TeacherResponse> fetchByFacultyId(Long facultyId);
    List<TeacherResponse> fetchByDepartmentId(Long departmentId);
    List<TeacherResponse> fetchByPositionId(Long positionId);
    List<TeacherResponse> fetchByAcademicDegreeId(Long academicDegreeId);
    TeacherResponse update(Long id, TeacherDTO dto);
    void delete(Long id);
    void activeOrDisabledTeacher(Long id);
}
