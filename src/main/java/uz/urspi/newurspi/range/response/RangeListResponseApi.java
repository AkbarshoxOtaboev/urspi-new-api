package uz.urspi.newurspi.range.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

import java.util.List;

/**
 * Concrete (non-generic) subclass of {@link RestApiResponse} used only so that Swagger/OpenAPI
 * can render the real {@code data} type ({@code List<RangeResponse>}).
 */
@Schema(description = "Rest api response wrapping a list of Ranges")
public class RangeListResponseApi extends RestApiResponse<List<RangeResponse>> {
}
