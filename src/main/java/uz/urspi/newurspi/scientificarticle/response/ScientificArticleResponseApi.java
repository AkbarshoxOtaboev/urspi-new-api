package uz.urspi.newurspi.scientificarticle.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

@Schema(description = "Rest api response wrapping a single ScientificArticle")
public class ScientificArticleResponseApi extends RestApiResponse<ScientificArticleResponse> {
}
