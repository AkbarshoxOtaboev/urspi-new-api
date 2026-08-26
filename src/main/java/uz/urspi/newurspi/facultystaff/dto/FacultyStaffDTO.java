package uz.urspi.newurspi.facultystaff.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Schema(description = "Faculty staff create/update dto (Dekan, zam-dekan, ...)")
public class FacultyStaffDTO {

    @NotEmpty(message = "Full name (uz) cannot be empty")
    private String fullNameUz;

    private String fullNameRu;
    private String fullNameEn;

    private String phoneNumber;

    @Email(message = "Email format is invalid")
    private String email;

    private MultipartFile photo;
    private MultipartFile cv;

    @NotEmpty(message = "Position title (uz) cannot be empty")
    @Schema(description = "Lavozim, masalan: Dekan, Zam-dekan")
    private String positionTitleUz;

    private String positionTitleRu;
    private String positionTitleEn;

    @Schema(description = "Navbat raqami: 1 = dekan, 2 = zam, ...")
    private Integer sortOrder;

    @NotNull(message = "Faculty id cannot be null")
    private Long facultyId;
}
