package uz.urspi.newurspi.center.mapper;

import org.springframework.stereotype.Component;
import uz.urspi.newurspi.center.Center;
import uz.urspi.newurspi.center.response.CenterLocalizedResponse;
import uz.urspi.newurspi.center.response.CenterResponse;
import uz.urspi.newurspi.utils.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CenterMapper {

    public CenterResponse toResponse(Center center) {
        if (center == null) {
            return null;
        }
        return CenterResponse.builder()
                .id(center.getId())
                .nameUz(center.getNameUz())
                .nameRu(center.getNameRu())
                .nameEn(center.getNameEn())
                .descriptionUz(center.getDescriptionUz())
                .descriptionRu(center.getDescriptionRu())
                .descriptionEn(center.getDescriptionEn())
                .status(center.getStatus())
                .createdAt(center.getCreatedAt())
                .updatedAt(center.getUpdatedAt())
                .build();
    }

    public CenterLocalizedResponse toLocalizedResponse(Center center, Language lang) {
        if (center == null) {
            return null;
        }
        String name;
        String description;
        switch (lang) {
            case uz -> {
                name = center.getNameUz();
                description = center.getDescriptionUz();
            }
            case ru -> {
                name = center.getNameRu();
                description = center.getDescriptionRu();
            }
            case en -> {
                name = center.getNameEn();
                description = center.getDescriptionEn();
            }
            default -> {
                name = center.getNameUz();
                description = center.getDescriptionUz();
            }
        }
        return CenterLocalizedResponse.builder()
                .id(center.getId())
                .name(name)
                .description(description)
                .status(center.getStatus())
                .createdAt(center.getCreatedAt())
                .updatedAt(center.getUpdatedAt())
                .build();
    }

    public List<CenterResponse> toResponseList(List<Center> centers) {
        return centers.stream()
                .map(this::toResponse)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<CenterLocalizedResponse> toLocalizedResponseList(List<Center> centers, Language lang) {
        return centers.stream()
                .map(c -> toLocalizedResponse(c, lang))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
