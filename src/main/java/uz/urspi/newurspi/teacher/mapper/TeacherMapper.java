package uz.urspi.newurspi.teacher.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.urspi.newurspi.academicdegree.mapper.AcademicDegreeMapper;
import uz.urspi.newurspi.department.mapper.DepartmentMapper;
import uz.urspi.newurspi.faculty.mapper.FacultyMapper;
import uz.urspi.newurspi.position.mapper.PositionMapper;
import uz.urspi.newurspi.teacher.Teacher;
import uz.urspi.newurspi.teacher.response.TeacherResponse;

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
                .fullName(teacher.getFullName())
                .phoneNumber(teacher.getPhoneNumber())
                .email(teacher.getEmail())
                .photoLink(teacher.getPhotoLink())
                .cvLink(teacher.getCvLink())
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
}
