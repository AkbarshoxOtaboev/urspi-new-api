package uz.urspi.newurspi.position.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

import java.util.List;

@Schema(description = "Rest api response wrapping a list of Positions")
public class PositionListResponseApi extends RestApiResponse<List<PositionResponse>> {
}
