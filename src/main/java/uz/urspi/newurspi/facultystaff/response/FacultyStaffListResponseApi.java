package uz.urspi.newurspi.facultystaff.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

import java.util.List;

@Schema(description = "Rest api response wrapping a list of FacultyStaff")
public class FacultyStaffListResponseApi extends RestApiResponse<List<FacultyStaffResponse>> {
}
