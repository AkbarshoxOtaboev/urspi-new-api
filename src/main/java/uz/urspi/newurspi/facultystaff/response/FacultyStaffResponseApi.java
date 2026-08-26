package uz.urspi.newurspi.facultystaff.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

@Schema(description = "Rest api response wrapping a single FacultyStaff")
public class FacultyStaffResponseApi extends RestApiResponse<FacultyStaffResponse> {
}
