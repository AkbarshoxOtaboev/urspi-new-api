package uz.urspi.newurspi.leader.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

@Schema(description = "Rest api response wrapping a single Leader")
public class LeaderResponseApi extends RestApiResponse<LeaderResponse> {
}
