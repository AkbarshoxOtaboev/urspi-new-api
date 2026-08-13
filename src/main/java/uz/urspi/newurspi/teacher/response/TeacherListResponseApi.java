package uz.urspi.newurspi.teacher.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

import java.util.List;

@Schema(description = "Rest api response wrapping a list of Teachers")
public class TeacherListResponseApi extends RestApiResponse<List<TeacherResponse>> {
}
