package uz.urspi.newurspi.leader.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

import java.util.List;

@Schema(description = "Rest api response wrapping a list of localized Leaders")
public class LeaderLocalizedListResponseApi extends RestApiResponse<List<LeaderLocalizedResponse>> {
}
