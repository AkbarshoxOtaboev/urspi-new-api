package uz.urspi.newurspi.center.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

@Schema(description = "Rest api response wrapping a single Center")
public class CenterResponseApi extends RestApiResponse<CenterResponse> {
}
