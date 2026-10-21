package uz.urspi.newurspi.hemis.publicstat.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "HEMIS public student statistics")
public class StudentStatResponse {

    @Schema(example = "true")
    private Boolean success;

    private Object error;

    private StudentStatDataResponse data;

    @Schema(description = "HEMIS HTTP status code", example = "200")
    private Integer code;
}
