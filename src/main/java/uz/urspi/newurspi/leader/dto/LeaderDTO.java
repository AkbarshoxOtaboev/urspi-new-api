package uz.urspi.newurspi.leader.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Schema(description = "Leader create/update dto")
public class LeaderDTO {

    @NotEmpty(message = "Full name (uz) cannot be empty")
    @Schema(description = "Full name in Uzbek")
    private String fullNameUz;

    @Schema(description = "Full name in Russian")
    private String fullNameRu;

    @Schema(description = "Full name in English")
    private String fullNameEn;

    @NotEmpty(message = "Position title (uz) cannot be empty")
    @Schema(description = "Position title in Uzbek")
    private String positionTitleUz;

    @Schema(description = "Position title in Russian")
    private String positionTitleRu;

    @Schema(description = "Position title in English")
    private String positionTitleEn;

    @Schema(description = "Address in Uzbek")
    private String addressUz;

    @Schema(description = "Address in Russian")
    private String addressRu;

    @Schema(description = "Address in English")
    private String addressEn;

    @Schema(description = "Reception time in Uzbek")
    private String receptionTimeUz;

    @Schema(description = "Reception time in Russian")
    private String receptionTimeRu;

    @Schema(description = "Reception time in English")
    private String receptionTimeEn;

    @Schema(description = "Phone number")
    private String phoneNumber;

    @Schema(description = "Email")
    private String email;

    @Schema(description = "Photo file")
    private MultipartFile photo;

    @Schema(description = "Sort order")
    private Integer sortOrder;
}
