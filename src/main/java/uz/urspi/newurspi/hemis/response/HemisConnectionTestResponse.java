package uz.urspi.newurspi.hemis.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "HEMIS connection test result")
public class HemisConnectionTestResponse {
    private boolean success;
    private String baseUrl;
    private String path;
    private Integer httpLikeStatus;
    private String message;
    private Integer sampleItemCount;
}
