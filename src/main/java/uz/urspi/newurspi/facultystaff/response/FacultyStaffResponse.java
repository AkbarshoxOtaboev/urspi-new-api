package uz.urspi.newurspi.facultystaff.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import uz.urspi.newurspi.faculty.response.FacultyResponse;
import uz.urspi.newurspi.utils.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Faculty staff response")
public class FacultyStaffResponse {
    private Long id;
    private String fullNameUz;
    private String fullNameRu;
    private String fullNameEn;
    private String phoneNumber;
    private String email;
    private String photoLink;
    private String cvLink;
    private String positionTitleUz;
    private String positionTitleRu;
    private String positionTitleEn;
    private Integer sortOrder;
    private FacultyResponse faculty;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
