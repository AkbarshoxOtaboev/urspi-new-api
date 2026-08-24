package uz.urspi.newurspi.greeninstitute.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

import java.util.List;

@Schema(description = "Rest api response wrapping localized GreenInstitutes")
public class GreenInstituteLocalizedListResponseApi extends RestApiResponse<List<GreenInstituteLocalizedResponse>> {
}
