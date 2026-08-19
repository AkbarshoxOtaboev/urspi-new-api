package uz.urspi.newurspi.department.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.urspi.newurspi.faculty.response.FacultyLocalizedResponse;
import uz.urspi.newurspi.utils.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Department localized response")
public class DepartmentLocalizedResponse {
    private Long id;
    private String name;
    private String description;
    private FacultyLocalizedResponse faculty;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
