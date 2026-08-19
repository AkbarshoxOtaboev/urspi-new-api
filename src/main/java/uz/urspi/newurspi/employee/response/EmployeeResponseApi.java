package uz.urspi.newurspi.employee.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

@Schema(description = "Rest api response wrapping a single Employee")
public class EmployeeResponseApi extends RestApiResponse<EmployeeResponse> {
}
