package uz.urspi.newurspi.facultystaff.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.urspi.newurspi.faculty.mapper.FacultyMapper;
import uz.urspi.newurspi.facultystaff.FacultyStaff;
import uz.urspi.newurspi.facultystaff.response.FacultyStaffLocalizedResponse;
import uz.urspi.newurspi.facultystaff.response.FacultyStaffResponse;
import uz.urspi.newurspi.utils.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FacultyStaffMapper {
    private final FacultyMapper facultyMapper;

    public FacultyStaffResponse toResponse(FacultyStaff staff) {
        if (staff == null) {
            return null;
        }
        return FacultyStaffResponse.builder()
                .id(staff.getId())
                .fullNameUz(staff.getFullNameUz())
                .fullNameRu(staff.getFullNameRu())
                .fullNameEn(staff.getFullNameEn())
                .phoneNumber(staff.getPhoneNumber())
                .email(staff.getEmail())
                .photoLink(staff.getPhotoLink())
                .cvLink(staff.getCvLink())
                .positionTitleUz(staff.getPositionTitleUz())
                .positionTitleRu(staff.getPositionTitleRu())
                .positionTitleEn(staff.getPositionTitleEn())
                .sortOrder(staff.getSortOrder())
                .faculty(facultyMapper.toResponse(staff.getFaculty()))
                .status(staff.getStatus())
                .createdAt(staff.getCreatedAt())
                .updatedAt(staff.getUpdatedAt())
                .build();
    }

    public FacultyStaffLocalizedResponse toLocalizedResponse(FacultyStaff staff, Language lang) {
        if (staff == null) {
            return null;
        }
        return FacultyStaffLocalizedResponse.builder()
                .id(staff.getId())
                .fullName(localizedFullName(staff, lang))
                .phoneNumber(staff.getPhoneNumber())
                .email(staff.getEmail())
                .photoLink(staff.getPhotoLink())
                .cvLink(staff.getCvLink())
                .positionTitle(localizedPositionTitle(staff, lang))
                .sortOrder(staff.getSortOrder())
                .status(staff.getStatus())
                .createdAt(staff.getCreatedAt())
                .updatedAt(staff.getUpdatedAt())
                .build();
    }

    public List<FacultyStaffResponse> toResponseList(List<FacultyStaff> list) {
        return list.stream().map(this::toResponse).collect(Collectors.toCollection(ArrayList::new));
    }

    public List<FacultyStaffLocalizedResponse> toLocalizedResponseList(List<FacultyStaff> list, Language lang) {
        return list.stream().map(s -> toLocalizedResponse(s, lang)).collect(Collectors.toCollection(ArrayList::new));
    }

    private String localizedFullName(FacultyStaff staff, Language lang) {
        return switch (lang) {
            case ru -> staff.getFullNameRu() != null ? staff.getFullNameRu() : staff.getFullNameUz();
            case en -> staff.getFullNameEn() != null ? staff.getFullNameEn() : staff.getFullNameUz();
            default -> staff.getFullNameUz();
        };
    }

    private String localizedPositionTitle(FacultyStaff staff, Language lang) {
        return switch (lang) {
            case ru -> staff.getPositionTitleRu() != null ? staff.getPositionTitleRu() : staff.getPositionTitleUz();
            case en -> staff.getPositionTitleEn() != null ? staff.getPositionTitleEn() : staff.getPositionTitleUz();
            default -> staff.getPositionTitleUz();
        };
    }
}
