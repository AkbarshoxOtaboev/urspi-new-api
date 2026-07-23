package uz.urspi.newurspi.range.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

/**
 * Concrete (non-generic) subclass of {@link RestApiResponse} used only so that Swagger/OpenAPI
 * can render the real {@code data} type ({@link RangeResponse}), since
 * {@code RestApiResponse<RangeResponse>.class} is not valid Java.
 */
@Schema(description = "Rest api response wrapping a single Range")
public class RangeResponseApi extends RestApiResponse<RangeResponse> {
}
