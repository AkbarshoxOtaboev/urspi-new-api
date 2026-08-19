package uz.urspi.newurspi.employee.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import uz.urspi.newurspi.center.response.CenterResponse;
import uz.urspi.newurspi.utils.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Employee response")
public class EmployeeResponse {
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
    private CenterResponse center;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
