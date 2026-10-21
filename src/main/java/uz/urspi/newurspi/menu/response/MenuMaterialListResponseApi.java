package uz.urspi.newurspi.menu.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

import java.util.List;

@Schema(description = "Rest api response wrapping MenuMaterial list")
public class MenuMaterialListResponseApi extends RestApiResponse<List<MenuMaterialResponse>> {
}
