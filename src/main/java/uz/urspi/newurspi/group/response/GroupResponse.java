package uz.urspi.newurspi.group.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uz.urspi.newurspi.department.response.DepartmentResponse;
import uz.urspi.newurspi.faculty.response.FacultyResponse;
import uz.urspi.newurspi.utils.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Schema(description = "Group response")
public class GroupResponse {
    private Long id;
    private String name;
    private String description;
    private FacultyResponse faculty;
    private DepartmentResponse department;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
