package uz.urspi.newurspi.group.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

/**
 * Concrete (non-generic) subclass of {@link RestApiResponse} used only so that Swagger/OpenAPI
 * can render the real {@code data} type ({@link GroupResponse}), since
 * {@code RestApiResponse<GroupResponse>.class} is not valid Java.
 */
@Schema(description = "Rest api response wrapping a single Group")
public class GroupResponseApi extends RestApiResponse<GroupResponse> {
}