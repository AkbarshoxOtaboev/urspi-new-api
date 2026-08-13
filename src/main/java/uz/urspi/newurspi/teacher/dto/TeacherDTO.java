package uz.urspi.newurspi.teacher.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Schema(description = "Teacher create/update dto")
public class TeacherDTO {

    @NotEmpty(message = "Full name cannot be empty")
    @Schema(description = "To'liq ism", example = "Aliyev Vali G'aniyevich")
    private String fullName;

    @NotEmpty(message = "Phone number cannot be empty")
    @Schema(description = "Telefon raqam", example = "+998901234567")
    private String phoneNumber;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email is not valid")
    @Schema(description = "Email", example = "aliyev@urspi.uz")
    private String email;

    @Schema(description = "O'qituvchi rasmi")
    private MultipartFile photo;

    @Schema(description = "CV fayl")
    private MultipartFile cv;

    @NotNull(message = "Faculty is required")
    @Schema(description = "Fakultet ID", example = "1")
    private Long facultyId;

    @NotNull(message = "Department is required")
    @Schema(description = "Kafedra ID", example = "1")
    private Long departmentId;

    @NotNull(message = "Position is required")
    @Schema(description = "Lavozim ID", example = "1")
    private Long positionId;

    @NotNull(message = "Academic degree is required")
    @Schema(description = "Ilmiy daraja ID", example = "1")
    private Long academicDegreeId;
}
