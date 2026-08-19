package uz.urspi.newurspi.center.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

import java.util.List;

@Schema(description = "Rest api response wrapping a list of localized Centers")
public class CenterLocalizedListResponseApi extends RestApiResponse<List<CenterLocalizedResponse>> {
}
