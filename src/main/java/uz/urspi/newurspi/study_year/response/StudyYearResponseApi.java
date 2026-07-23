package uz.urspi.newurspi.study_year.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

/**
 * Concrete (non-generic) subclass of {@link RestApiResponse} used only so that Swagger/OpenAPI
 * can render the real {@code data} type ({@link StudyYearResponse}), since
 * {@code RestApiResponse<StudyYearResponse>.class} is not valid Java.
 */
@Schema(description = "Rest api response wrapping a single Study year")
public class StudyYearResponseApi extends RestApiResponse<StudyYearResponse> {
}
