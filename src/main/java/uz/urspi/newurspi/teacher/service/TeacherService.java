package uz.urspi.newurspi.teacher.service;

import uz.urspi.newurspi.teacher.dto.TeacherDTO;
import uz.urspi.newurspi.teacher.response.TeacherLocalizedResponse;
import uz.urspi.newurspi.teacher.response.TeacherResponse;
import uz.urspi.newurspi.utils.Language;

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
    List<TeacherLocalizedResponse> fetchAllTeachersByLang(Language lang);
    List<TeacherLocalizedResponse> fetchByFacultyAndDepartmentByLang(Long facultyId, Long departmentId, Language lang);
    List<TeacherResponse> fetchByFacultyIdAndDepartmentId(Long facultyId, Long departmentId);
}
