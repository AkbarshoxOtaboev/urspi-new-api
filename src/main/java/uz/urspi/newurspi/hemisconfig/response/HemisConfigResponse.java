package uz.urspi.newurspi.hemisconfig.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.urspi.newurspi.utils.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "HEMIS config response (token masked)")
public class HemisConfigResponse {
    private Long id;
    private String name;
    private String baseUrl;
    private String tokenMasked;
    private String defaultLang;
    private boolean active;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
