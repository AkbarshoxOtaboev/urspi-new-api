package uz.urspi.newurspi.position.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

@Schema(description = "Rest api response wrapping a single Position")
public class PositionResponseApi extends RestApiResponse<PositionResponse> {
}
