package uz.urspi.newurspi.academicdegree.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

@Schema(description = "Rest api response wrapping a single AcademicDegree")
public class AcademicDegreeResponseApi extends RestApiResponse<AcademicDegreeResponse> {
}
