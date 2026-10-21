package uz.urspi.newurspi.menu.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

import java.util.List;

@Schema(description = "Rest api response wrapping Menu list")
public class MenuListResponseApi extends RestApiResponse<List<MenuResponse>> {
}
