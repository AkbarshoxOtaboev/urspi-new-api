package uz.urspi.newurspi.group.service;

import uz.urspi.newurspi.group.dto.GroupDTO;
import uz.urspi.newurspi.group.response.GroupResponse;

import java.util.List;

public interface GroupService {
    GroupResponse create(GroupDTO dto);
    GroupResponse findById(Long id);
    List<GroupResponse> fetchAllGroups();
    List<GroupResponse> fetchByFacultyId(Long facultyId);
    List<GroupResponse> fetchByDepartmentId(Long departmentId);
    GroupResponse update(Long id, GroupDTO dto);
    void delete(Long id);
    void activeOrDisabledGroup(Long id);
}
