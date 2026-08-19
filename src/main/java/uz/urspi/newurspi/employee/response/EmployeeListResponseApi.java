package uz.urspi.newurspi.employee.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

import java.util.List;

@Schema(description = "Rest api response wrapping a list of Employees")
public class EmployeeListResponseApi extends RestApiResponse<List<EmployeeResponse>> {
}
