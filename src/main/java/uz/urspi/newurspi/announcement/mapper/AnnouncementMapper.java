package uz.urspi.newurspi.announcement.mapper;

import org.springframework.stereotype.Component;
import uz.urspi.newurspi.announcement.Announcement;
import uz.urspi.newurspi.announcement.response.AnnouncementLocalizedResponse;
import uz.urspi.newurspi.announcement.response.AnnouncementResponse;
import uz.urspi.newurspi.utils.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AnnouncementMapper {

    public AnnouncementResponse toResponse(Announcement announcement) {
        if (announcement == null) {
            return null;
        }
        return AnnouncementResponse.builder()
                .id(announcement.getId())
                .titleUz(announcement.getTitleUz())
                .titleRu(announcement.getTitleRu())
                .titleEn(announcement.getTitleEn())
                .contentUz(announcement.getContentUz())
                .contentRu(announcement.getContentRu())
                .contentEn(announcement.getContentEn())
                .publishedAt(announcement.getPublishedAt())
                .imageLink(announcement.getImageLink())
                .status(announcement.getStatus())
                .createdAt(announcement.getCreatedAt())
                .updatedAt(announcement.getUpdatedAt())
                .build();
    }

    public AnnouncementLocalizedResponse toLocalizedResponse(Announcement announcement, Language lang) {
        if (announcement == null) {
            return null;
        }
        return AnnouncementLocalizedResponse.builder()
                .id(announcement.getId())
                .title(getLocalizedTitle(announcement, lang))
                .content(getLocalizedContent(announcement, lang))
                .publishedAt(announcement.getPublishedAt())
                .imageLink(announcement.getImageLink())
                .status(announcement.getStatus())
                .createdAt(announcement.getCreatedAt())
                .updatedAt(announcement.getUpdatedAt())
                .build();
    }

    public List<AnnouncementResponse> toResponseList(List<Announcement> announcements) {
        return announcements.stream()
                .map(this::toResponse)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<AnnouncementLocalizedResponse> toLocalizedResponseList(List<Announcement> announcements, Language lang) {
        return announcements.stream()
                .map(a -> toLocalizedResponse(a, lang))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private String getLocalizedTitle(Announcement announcement, Language lang) {
        return switch (lang) {
            case ru -> announcement.getTitleRu();
            case en -> announcement.getTitleEn();
            default -> announcement.getTitleUz();
        };
    }

    private String getLocalizedContent(Announcement announcement, Language lang) {
        return switch (lang) {
            case ru -> announcement.getContentRu();
            case en -> announcement.getContentEn();
            default -> announcement.getContentUz();
        };
    }
}
