package uz.urspi.newurspi.news.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

import java.util.List;

@Schema(description = "Rest api response wrapping a list of News")
public class NewsListResponseApi extends RestApiResponse<List<NewsResponse>> {
}
