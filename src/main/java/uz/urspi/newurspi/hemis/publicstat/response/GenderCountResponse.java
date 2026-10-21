package uz.urspi.newurspi.hemis.publicstat.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@Schema(description = "Erkak/Ayol soni")
public class GenderCountResponse {
    @JsonProperty("Erkak")
    @Schema(example = "1298")
    private Integer erkak;

    @JsonProperty("Ayol")
    @Schema(example = "8214")
    private Integer ayol;
}
