package uz.urspi.newurspi.dormitory.mapper;

import org.springframework.stereotype.Component;
import uz.urspi.newurspi.dormitory.Dormitory;
import uz.urspi.newurspi.dormitory.response.DormitoryLocalizedResponse;
import uz.urspi.newurspi.dormitory.response.DormitoryResponse;
import uz.urspi.newurspi.utils.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DormitoryMapper {

    public DormitoryResponse toResponse(Dormitory entity) {
        if (entity == null) {
            return null;
        }
        return DormitoryResponse.builder()
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

    public DormitoryLocalizedResponse toLocalizedResponse(Dormitory entity, Language lang) {
        if (entity == null) {
            return null;
        }
        return DormitoryLocalizedResponse.builder()
                .id(entity.getId())
                .title(switch (lang) {
                    case ru -> entity.getTitleRu();
                    case en -> entity.getTitleEn();
                    default -> entity.getTitleUz();
                })
                .description(switch (lang) {
                    case ru -> entity.getDescriptionRu();
                    case en -> entity.getDescriptionEn();
                    default -> entity.getDescriptionUz();
                })
                .imageLink(entity.getImageLink())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public List<DormitoryResponse> toResponseList(List<Dormitory> list) {
        return list.stream().map(this::toResponse).collect(Collectors.toCollection(ArrayList::new));
    }

    public List<DormitoryLocalizedResponse> toLocalizedResponseList(List<Dormitory> list, Language lang) {
        return list.stream().map(e -> toLocalizedResponse(e, lang)).collect(Collectors.toCollection(ArrayList::new));
    }
}
