package uz.urspi.newurspi.hemis.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

@Schema(description = "Rest api response wrapping HEMIS connection test")
public class HemisConnectionTestResponseApi extends RestApiResponse<HemisConnectionTestResponse> {
}
