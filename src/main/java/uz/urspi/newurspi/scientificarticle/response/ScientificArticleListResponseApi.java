package uz.urspi.newurspi.scientificarticle.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

import java.util.List;

@Schema(description = "Rest api response wrapping a list of ScientificArticles")
public class ScientificArticleListResponseApi extends RestApiResponse<List<ScientificArticleResponse>> {
}
