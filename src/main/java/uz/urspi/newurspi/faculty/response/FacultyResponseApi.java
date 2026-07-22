package uz.urspi.newurspi.faculty.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

/**
 * Concrete (non-generic) subclass of {@link RestApiResponse} used only so that Swagger/OpenAPI
 * can render the real {@code data} type ({@link FacultyResponse}), since
 * {@code RestApiResponse<FacultyResponse>.class} is not valid Java.
 */
@Schema(description = "Rest api response wrapping a single Faculty")
public class FacultyResponseApi extends RestApiResponse<FacultyResponse> {
}