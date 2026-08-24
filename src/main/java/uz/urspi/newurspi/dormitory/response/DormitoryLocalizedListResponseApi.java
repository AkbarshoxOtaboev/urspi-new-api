package uz.urspi.newurspi.dormitory.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

import java.util.List;

@Schema(description = "Rest api response wrapping localized Dormitories")
public class DormitoryLocalizedListResponseApi extends RestApiResponse<List<DormitoryLocalizedResponse>> {
}
