package uz.urspi.newurspi.photogallery.mapper;

import org.springframework.stereotype.Component;
import uz.urspi.newurspi.photogallery.PhotoGallery;
import uz.urspi.newurspi.photogallery.response.PhotoGalleryLocalizedResponse;
import uz.urspi.newurspi.photogallery.response.PhotoGalleryResponse;
import uz.urspi.newurspi.utils.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PhotoGalleryMapper {

    public PhotoGalleryResponse toResponse(PhotoGallery entity) {
        if (entity == null) {
            return null;
        }
        return PhotoGalleryResponse.builder()
                .id(entity.getId())
                .titleUz(entity.getTitleUz())
                .titleRu(entity.getTitleRu())
                .titleEn(entity.getTitleEn())
                .descriptionUz(entity.getDescriptionUz())
                .descriptionRu(entity.getDescriptionRu())
                .descriptionEn(entity.getDescriptionEn())
                .imageLink(entity.getImageLink())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public PhotoGalleryLocalizedResponse toLocalizedResponse(PhotoGallery entity, Language lang) {
        if (entity == null) {
            return null;
        }
        return PhotoGalleryLocalizedResponse.builder()
                .id(entity.getId())
                .title(localizedTitle(entity, lang))
                .description(localizedDescription(entity, lang))
                .imageLink(entity.getImageLink())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public List<PhotoGalleryResponse> toResponseList(List<PhotoGallery> list) {
        return list.stream().map(this::toResponse).collect(Collectors.toCollection(ArrayList::new));
    }

    public List<PhotoGalleryLocalizedResponse> toLocalizedResponseList(List<PhotoGallery> list, Language lang) {
        return list.stream().map(e -> toLocalizedResponse(e, lang)).collect(Collectors.toCollection(ArrayList::new));
    }

    private String localizedTitle(PhotoGallery entity, Language lang) {
        return switch (lang) {
            case ru -> entity.getTitleRu();
            case en -> entity.getTitleEn();
            default -> entity.getTitleUz();
        };
    }

    private String localizedDescription(PhotoGallery entity, Language lang) {
        return switch (lang) {
            case ru -> entity.getDescriptionRu();
            case en -> entity.getDescriptionEn();
            default -> entity.getDescriptionUz();
        };
    }
}
