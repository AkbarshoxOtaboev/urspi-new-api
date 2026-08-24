package uz.urspi.newurspi.greeninstitute.mapper;

import org.springframework.stereotype.Component;
import uz.urspi.newurspi.greeninstitute.GreenInstitute;
import uz.urspi.newurspi.greeninstitute.response.GreenInstituteLocalizedResponse;
import uz.urspi.newurspi.greeninstitute.response.GreenInstituteResponse;
import uz.urspi.newurspi.utils.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GreenInstituteMapper {

    public GreenInstituteResponse toResponse(GreenInstitute entity) {
        if (entity == null) {
            return null;
        }
        return GreenInstituteResponse.builder()
                .id(entity.getId())
                .titleUz(entity.getTitleUz())
                .titleRu(entity.getTitleRu())
                .titleEn(entity.getTitleEn())
                .imageLinks(copyLinks(entity.getImageLinks()))
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public GreenInstituteLocalizedResponse toLocalizedResponse(GreenInstitute entity, Language lang) {
        if (entity == null) {
            return null;
        }
        return GreenInstituteLocalizedResponse.builder()
                .id(entity.getId())
                .title(switch (lang) {
                    case ru -> entity.getTitleRu();
                    case en -> entity.getTitleEn();
                    default -> entity.getTitleUz();
                })
                .imageLinks(copyLinks(entity.getImageLinks()))
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public List<GreenInstituteResponse> toResponseList(List<GreenInstitute> list) {
        return list.stream().map(this::toResponse).collect(Collectors.toCollection(ArrayList::new));
    }

    public List<GreenInstituteLocalizedResponse> toLocalizedResponseList(List<GreenInstitute> list, Language lang) {
        return list.stream().map(e -> toLocalizedResponse(e, lang)).collect(Collectors.toCollection(ArrayList::new));
    }

    private List<String> copyLinks(List<String> links) {
        return links == null ? new ArrayList<>() : new ArrayList<>(links);
    }
}
