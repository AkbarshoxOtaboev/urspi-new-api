package uz.urspi.newurspi.photogallery.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

@Schema(description = "Rest api response wrapping a single PhotoGallery")
public class PhotoGalleryResponseApi extends RestApiResponse<PhotoGalleryResponse> {
}
