package uz.urspi.newurspi.hemis.publicstat.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

@Schema(description = "Rest api response wrapping HEMIS student statistics")
public class StudentStatResponseApi extends RestApiResponse<StudentStatResponse> {
}
