package uz.urspi.newurspi.teacher.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.urspi.newurspi.academicdegree.mapper.AcademicDegreeMapper;
import uz.urspi.newurspi.department.mapper.DepartmentMapper;
import uz.urspi.newurspi.faculty.mapper.FacultyMapper;
import uz.urspi.newurspi.position.mapper.PositionMapper;
import uz.urspi.newurspi.teacher.Teacher;
import uz.urspi.newurspi.teacher.response.TeacherLocalizedResponse;
import uz.urspi.newurspi.teacher.response.TeacherResponse;
import uz.urspi.newurspi.utils.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TeacherMapper {
    private final FacultyMapper facultyMapper;
    private final DepartmentMapper departmentMapper;
    private final PositionMapper positionMapper;
    private final AcademicDegreeMapper academicDegreeMapper;

    public TeacherResponse toResponse(Teacher teacher) {
        if (teacher == null) {
            return null;
        }
        return TeacherResponse.builder()
                .id(teacher.getId())
                .fullNameUz(teacher.getFullNameUz())
                .fullNameRu(teacher.getFullNameRu())
                .fullNameEn(teacher.getFullNameEn())
                .phoneNumber(teacher.getPhoneNumber())
                .email(teacher.getEmail())
                .photoLink(teacher.getPhotoLink())
                .cvLink(teacher.getCvLink())
                .sortOrder(teacher.getSortOrder())
                .faculty(facultyMapper.toResponse(teacher.getFaculty()))
                .department(departmentMapper.toResponse(teacher.getDepartment()))
                .position(positionMapper.toResponse(teacher.getPosition()))
                .academicDegree(academicDegreeMapper.toResponse(teacher.getAcademicDegree()))
                .status(teacher.getStatus())
                .createdAt(teacher.getCreatedAt())
                .updatedAt(teacher.getUpdatedAt())
                .build();
    }

    public List<TeacherResponse> toResponseList(List<Teacher> teachers) {
        return teachers.stream()
                .map(this::toResponse)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public TeacherLocalizedResponse toLocalizedResponse(Teacher teacher, Language lang) {
        if (teacher == null) {
            return null;
        }
        String fullName = switch (lang) {
            case ru -> teacher.getFullNameRu() != null ? teacher.getFullNameRu() : teacher.getFullNameUz();
            case en -> teacher.getFullNameEn() != null ? teacher.getFullNameEn() : teacher.getFullNameUz();
            default -> teacher.getFullNameUz();
        };
        return TeacherLocalizedResponse.builder()
                .id(teacher.getId())
                .fullName(fullName)
                .phoneNumber(teacher.getPhoneNumber())
                .email(teacher.getEmail())
                .photoLink(teacher.getPhotoLink())
                .cvLink(teacher.getCvLink())
                .sortOrder(teacher.getSortOrder())
                .status(teacher.getStatus())
                .createdAt(teacher.getCreatedAt())
                .updatedAt(teacher.getUpdatedAt())
                .build();
    }

    public List<TeacherLocalizedResponse> toLocalizedResponseList(List<Teacher> teachers, Language lang) {
        return teachers.stream()
                .map(t -> toLocalizedResponse(t, lang))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
