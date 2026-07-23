package uz.urspi.newurspi.semester.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

/**
 * Concrete (non-generic) subclass of {@link RestApiResponse} used only so that Swagger/OpenAPI
 * can render the real {@code data} type ({@link SemesterResponse}), since
 * {@code RestApiResponse<SemesterResponse>.class} is not valid Java.
 */
@Schema(description = "Rest api response wrapping a single Semester")
public class SemesterResponseApi extends RestApiResponse<SemesterResponse> {
}
