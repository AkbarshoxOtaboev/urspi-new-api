package uz.urspi.newurspi.announcement.service;

import uz.urspi.newurspi.announcement.dto.AnnouncementDTO;
import uz.urspi.newurspi.announcement.response.AnnouncementLocalizedResponse;
import uz.urspi.newurspi.announcement.response.AnnouncementResponse;
import uz.urspi.newurspi.utils.Language;

import java.util.List;

public interface AnnouncementService {
    AnnouncementResponse create(AnnouncementDTO dto);
    AnnouncementResponse findById(Long id);
    List<AnnouncementResponse> fetchAllAnnouncements();
    List<AnnouncementLocalizedResponse> fetchAllAnnouncementsByLang(Language lang);
    AnnouncementResponse update(Long id, AnnouncementDTO dto);
    void delete(Long id);
    void activeOrDisabledAnnouncement(Long id);
}
