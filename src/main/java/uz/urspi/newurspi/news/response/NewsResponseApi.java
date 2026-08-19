package uz.urspi.newurspi.news.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

@Schema(description = "Rest api response wrapping a single News")
public class NewsResponseApi extends RestApiResponse<NewsResponse> {
}
