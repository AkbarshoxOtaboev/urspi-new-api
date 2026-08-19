package uz.urspi.newurspi.employee.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Schema(description = "Employee create/update dto")
public class EmployeeDTO {

    @NotEmpty(message = "Full name (uz) cannot be empty")
    private String fullNameUz;

    private String fullNameRu;
    private String fullNameEn;

    @NotEmpty(message = "Phone number cannot be empty")
    private String phoneNumber;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email format is invalid")
    private String email;

    private MultipartFile photo;
    private MultipartFile cv;

    @NotEmpty(message = "Position title (uz) cannot be empty")
    private String positionTitleUz;

    private String positionTitleRu;
    private String positionTitleEn;

    private Integer sortOrder;

    @NotNull(message = "Center id cannot be null")
    private Long centerId;
}
