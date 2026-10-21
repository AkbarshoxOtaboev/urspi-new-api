package uz.urspi.newurspi.hemis.publicstat.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "HEMIS /v1/public/stat-student data bloki")
public class StudentStatDataResponse {

    @JsonProperty("education_type")
    @Schema(description = "Ta'lim turi bo'yicha (Bakalavr/Magistr/Jami)")
    private Map<String, GenderCountResponse> educationType;

    @Schema(description = "Yosh bo'yicha statistika")
    private Map<String, Object> age;

    @Schema(description = "To'lov turi bo'yicha")
    private Map<String, Object> payment;

    @Schema(description = "Hudud bo'yicha")
    private Map<String, Object> region;

    @Schema(description = "Fuqarolik bo'yicha")
    private Map<String, Object> citizenship;

    @Schema(description = "Yashash joyi bo'yicha")
    private Map<String, Object> accommodation;

    @JsonProperty("education_form")
    @Schema(description = "Ta'lim shakli bo'yicha")
    private Map<String, Object> educationForm;

    @Schema(description = "Kurs/bosqich bo'yicha")
    private Map<String, Object> level;
}
