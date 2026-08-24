package uz.urspi.newurspi.dormitory.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

@Schema(description = "Rest api response wrapping a single Dormitory")
public class DormitoryResponseApi extends RestApiResponse<DormitoryResponse> {
}
