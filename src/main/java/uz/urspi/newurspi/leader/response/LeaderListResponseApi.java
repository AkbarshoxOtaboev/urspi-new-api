package uz.urspi.newurspi.leader.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

import java.util.List;

@Schema(description = "Rest api response wrapping a list of Leaders")
public class LeaderListResponseApi extends RestApiResponse<List<LeaderResponse>> {
}
