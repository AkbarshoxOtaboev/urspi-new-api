package uz.urspi.newurspi.rental.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

@Schema(description = "Rest api response wrapping a single Rental")
public class RentalResponseApi extends RestApiResponse<RentalResponse> {
}
