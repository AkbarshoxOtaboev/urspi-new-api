package uz.urspi.newurspi.greeninstitute.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

@Schema(description = "Rest api response wrapping a single GreenInstitute")
public class GreenInstituteResponseApi extends RestApiResponse<GreenInstituteResponse> {
}
