package uz.urspi.newurspi.facultystaff.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

import java.util.List;

@Schema(description = "Rest api response wrapping localized FacultyStaff list")
public class FacultyStaffLocalizedListResponseApi extends RestApiResponse<List<FacultyStaffLocalizedResponse>> {
}
