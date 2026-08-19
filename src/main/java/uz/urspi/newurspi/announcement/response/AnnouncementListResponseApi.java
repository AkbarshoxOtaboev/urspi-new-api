package uz.urspi.newurspi.announcement.response;

import io.swagger.v3.oas.annotations.media.Schema;
import uz.urspi.newurspi.utils.RestApiResponse;

import java.util.List;

@Schema(description = "Rest api response wrapping a list of Announcements")
public class AnnouncementListResponseApi extends RestApiResponse<List<AnnouncementResponse>> {
}
