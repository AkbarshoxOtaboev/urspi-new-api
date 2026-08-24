package uz.urspi.newurspi.rental.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

import java.util.List;

@Schema(description = "Rest api response wrapping localized Rentals")
public class RentalLocalizedListResponseApi extends RestApiResponse<List<RentalLocalizedResponse>> {
}
