package uz.urspi.newurspi.hemisconfig.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "HEMIS config create/update dto")
public class HemisConfigDTO {

    @NotEmpty(message = "Config name cannot be empty")
    @Schema(description = "Config nomi", example = "production")
    private String name;

    @NotEmpty(message = "Base URL cannot be empty")
    @Schema(description = "HEMIS API base URL", example = "https://student.urspi.uz")
    private String baseUrl;

    @Schema(description = "Bearer token (update da bo'sh qoldirilsa eski token saqlanadi)", example = "auth_token_from_backend_dashboard")
    private String token;

    @Schema(description = "Default til kodi", example = "uz-UZ")
    private String defaultLang;

    @Schema(description = "Faol config (bir vaqtda bitta)", example = "true")
    private Boolean active;
}
