package uz.urspi.newurspi.announcement.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

import java.util.List;

@Schema(description = "Rest api response wrapping a list of localized Announcements")
public class AnnouncementLocalizedListResponseApi extends RestApiResponse<List<AnnouncementLocalizedResponse>> {
}
