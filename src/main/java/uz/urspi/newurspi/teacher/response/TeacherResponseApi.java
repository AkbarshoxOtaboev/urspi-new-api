package uz.urspi.newurspi.teacher.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

@Schema(description = "Rest api response wrapping a single Teacher")
public class TeacherResponseApi extends RestApiResponse<TeacherResponse> {
}
