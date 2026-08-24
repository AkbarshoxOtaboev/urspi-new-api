package uz.urspi.newurspi.rental.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

import java.util.List;

@Schema(description = "Rest api response wrapping a list of Rentals")
public class RentalListResponseApi extends RestApiResponse<List<RentalResponse>> {
}
