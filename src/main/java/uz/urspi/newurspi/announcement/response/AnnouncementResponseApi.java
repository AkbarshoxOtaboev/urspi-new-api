package uz.urspi.newurspi.announcement.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

@Schema(description = "Rest api response wrapping a single Announcement")
public class AnnouncementResponseApi extends RestApiResponse<AnnouncementResponse> {
}
