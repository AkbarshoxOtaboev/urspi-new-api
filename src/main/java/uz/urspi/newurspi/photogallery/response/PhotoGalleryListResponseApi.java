package uz.urspi.newurspi.photogallery.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

import java.util.List;

@Schema(description = "Rest api response wrapping a list of PhotoGalleries")
public class PhotoGalleryListResponseApi extends RestApiResponse<List<PhotoGalleryResponse>> {
}
