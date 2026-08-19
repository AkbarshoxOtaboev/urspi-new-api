package uz.urspi.newurspi.center.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

import java.util.List;

@Schema(description = "Rest api response wrapping a list of Centers")
public class CenterListResponseApi extends RestApiResponse<List<CenterResponse>> {
}
