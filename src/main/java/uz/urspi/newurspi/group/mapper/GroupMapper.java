package uz.urspi.newurspi.group.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.urspi.newurspi.department.mapper.DepartmentMapper;
import uz.urspi.newurspi.faculty.mapper.FacultyMapper;
import uz.urspi.newurspi.group.Group;
import uz.urspi.newurspi.group.response.GroupResponse;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GroupMapper {
    private final FacultyMapper facultyMapper;
    private final DepartmentMapper departmentMapper;

    public GroupResponse toResponse(Group group) {
        if (group == null) {
            return null;
        }
        return GroupResponse.builder()
                .id(group.getId())
                .name(group.getName())
                .description(group.getDescription())
                .faculty(facultyMapper.toResponse(group.getFaculty()))
                .department(departmentMapper.toResponse(group.getDepartment()))
                .status(group.getStatus())
                .createdAt(group.getCreatedAt())
                .updatedAt(group.getUpdatedAt())
                .build();
    }

    public List<GroupResponse> toResponseList(List<Group> groups) {
        return groups.stream().map(this::toResponse).toList();
    }
}
