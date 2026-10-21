package uz.urspi.newurspi.menu.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

@Schema(description = "Rest api response wrapping MenuMaterial")
public class MenuMaterialResponseApi extends RestApiResponse<MenuMaterialResponse> {
}
