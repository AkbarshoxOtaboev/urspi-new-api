package uz.urspi.newurspi.hemisconfig.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

@Schema(description = "Rest api response wrapping a single HemisConfig")
public class HemisConfigResponseApi extends RestApiResponse<HemisConfigResponse> {
}
