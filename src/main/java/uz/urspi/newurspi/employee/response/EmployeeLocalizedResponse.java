package uz.urspi.newurspi.employee.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import uz.urspi.newurspi.utils.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Employee localized response")
public class EmployeeLocalizedResponse {
    private Long id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String photoLink;
    private String cvLink;
    private String positionTitle;
    private Integer sortOrder;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
