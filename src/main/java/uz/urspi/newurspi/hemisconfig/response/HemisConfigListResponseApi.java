package uz.urspi.newurspi.hemisconfig.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

import java.util.List;

@Schema(description = "Rest api response wrapping a list of HemisConfigs")
public class HemisConfigListResponseApi extends RestApiResponse<List<HemisConfigResponse>> {
}
