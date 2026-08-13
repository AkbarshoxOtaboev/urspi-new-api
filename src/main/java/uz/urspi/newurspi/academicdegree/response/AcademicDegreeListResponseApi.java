package uz.urspi.newurspi.academicdegree.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

import java.util.List;

@Schema(description = "Rest api response wrapping a list of AcademicDegrees")
public class AcademicDegreeListResponseApi extends RestApiResponse<List<AcademicDegreeResponse>> {
}
