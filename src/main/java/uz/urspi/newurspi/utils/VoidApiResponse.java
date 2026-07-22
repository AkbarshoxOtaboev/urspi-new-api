package uz.urspi.newurspi.utils;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Concrete (non-generic) subclass of {@link RestApiResponse} used only so that Swagger/OpenAPI
 * can render a proper schema for endpoints that return no payload (delete, status change, etc.),
 * since {@code RestApiResponse<Void>.class} is not valid Java and {@code RestApiResponse.class}
 * alone loses the generic type information.
 */
@Schema(description = "Rest api response without data payload")
public class VoidApiResponse extends RestApiResponse<Void> {
}