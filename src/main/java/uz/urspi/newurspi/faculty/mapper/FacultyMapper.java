package uz.urspi.newurspi.faculty.mapper;

import org.springframework.stereotype.Component;
import uz.urspi.newurspi.faculty.Faculty;
import uz.urspi.newurspi.faculty.response.FacultyLocalizedResponse;
import uz.urspi.newurspi.faculty.response.FacultyResponse;
import uz.urspi.newurspi.utils.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FacultyMapper {

    public FacultyResponse toResponse(Faculty faculty) {
        if (faculty == null) {
            return null;
        }
        return FacultyResponse.builder()
                .id(faculty.getId())
                .code(faculty.getCode())
                .logoLink(faculty.getLogoLink())
                .nameUz(faculty.getNameUz())
                .nameRu(faculty.getNameRu())
                .nameEn(faculty.getNameEn())
                .descriptionUz(faculty.getDescriptionUz())
                .descriptionRu(faculty.getDescriptionRu())
                .descriptionEn(faculty.getDescriptionEn())
                .status(faculty.getStatus())
                .createdAt(faculty.getCreatedAt())
                .updatedAt(faculty.getUpdatedAt())
                .build();
    }

    public FacultyLocalizedResponse toLocalizedResponse(Faculty faculty, Language lang) {
        if (faculty == null) {
            return null;
        }
        return FacultyLocalizedResponse.builder()
                .id(faculty.getId())
                .code(faculty.getCode())
                .logoLink(faculty.getLogoLink())
                .name(getLocalizedName(faculty, lang))
                .description(getLocalizedDescription(faculty, lang))
                .status(faculty.getStatus())
                .createdAt(faculty.getCreatedAt())
                .updatedAt(faculty.getUpdatedAt())
                .build();
    }

    public List<FacultyResponse> toResponseList(List<Faculty> faculties) {
        return faculties.stream()
                .map(this::toResponse)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<FacultyLocalizedResponse> toLocalizedResponseList(List<Faculty> faculties, Language lang) {
        return faculties.stream()
                .map(f -> toLocalizedResponse(f, lang))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private String getLocalizedName(Faculty faculty, Language lang) {
        return switch (lang) {
            case ru -> faculty.getNameRu();
            case en -> faculty.getNameEn();
            default -> faculty.getNameUz();
        };
    }

    private String getLocalizedDescription(Faculty faculty, Language lang) {
        return switch (lang) {
            case ru -> faculty.getDescriptionRu();
            case en -> faculty.getDescriptionEn();
            default -> faculty.getDescriptionUz();
        };
    }
}
