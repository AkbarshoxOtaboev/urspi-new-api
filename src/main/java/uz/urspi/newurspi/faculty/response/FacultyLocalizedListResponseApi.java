package uz.urspi.newurspi.faculty.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

import java.util.List;

@Schema(description = "Rest api response wrapping a list of localized Faculties")
public class FacultyLocalizedListResponseApi extends RestApiResponse<List<FacultyLocalizedResponse>> {
}
