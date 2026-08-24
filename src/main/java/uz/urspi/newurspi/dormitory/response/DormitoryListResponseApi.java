package uz.urspi.newurspi.dormitory.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

import java.util.List;

@Schema(description = "Rest api response wrapping a list of Dormitories")
public class DormitoryListResponseApi extends RestApiResponse<List<DormitoryResponse>> {
}
