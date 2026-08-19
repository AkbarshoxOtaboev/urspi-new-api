package uz.urspi.newurspi.leader.mapper;

import org.springframework.stereotype.Component;
import uz.urspi.newurspi.leader.Leader;
import uz.urspi.newurspi.leader.response.LeaderLocalizedResponse;
import uz.urspi.newurspi.leader.response.LeaderResponse;
import uz.urspi.newurspi.utils.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LeaderMapper {

    public LeaderResponse toResponse(Leader leader) {
        if (leader == null) {
            return null;
        }
        return LeaderResponse.builder()
                .id(leader.getId())
                .fullNameUz(leader.getFullNameUz())
                .fullNameRu(leader.getFullNameRu())
                .fullNameEn(leader.getFullNameEn())
                .positionTitleUz(leader.getPositionTitleUz())
                .positionTitleRu(leader.getPositionTitleRu())
                .positionTitleEn(leader.getPositionTitleEn())
                .addressUz(leader.getAddressUz())
                .addressRu(leader.getAddressRu())
                .addressEn(leader.getAddressEn())
                .receptionTimeUz(leader.getReceptionTimeUz())
                .receptionTimeRu(leader.getReceptionTimeRu())
                .receptionTimeEn(leader.getReceptionTimeEn())
                .phoneNumber(leader.getPhoneNumber())
                .email(leader.getEmail())
                .photoLink(leader.getPhotoLink())
                .sortOrder(leader.getSortOrder())
                .status(leader.getStatus())
                .createdAt(leader.getCreatedAt())
                .updatedAt(leader.getUpdatedAt())
                .build();
    }

    public LeaderLocalizedResponse toLocalizedResponse(Leader leader, Language lang) {
        if (leader == null) {
            return null;
        }
        return LeaderLocalizedResponse.builder()
                .id(leader.getId())
                .fullName(getLocalizedFullName(leader, lang))
                .positionTitle(getLocalizedPositionTitle(leader, lang))
                .address(getLocalizedAddress(leader, lang))
                .receptionTime(getLocalizedReceptionTime(leader, lang))
                .phoneNumber(leader.getPhoneNumber())
                .email(leader.getEmail())
                .photoLink(leader.getPhotoLink())
                .sortOrder(leader.getSortOrder())
                .status(leader.getStatus())
                .createdAt(leader.getCreatedAt())
                .updatedAt(leader.getUpdatedAt())
                .build();
    }

    public List<LeaderResponse> toResponseList(List<Leader> leaders) {
        return leaders.stream()
                .map(this::toResponse)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<LeaderLocalizedResponse> toLocalizedResponseList(List<Leader> leaders, Language lang) {
        return leaders.stream()
                .map(l -> toLocalizedResponse(l, lang))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private String getLocalizedFullName(Leader leader, Language lang) {
        return switch (lang) {
            case ru -> leader.getFullNameRu();
            case en -> leader.getFullNameEn();
            default -> leader.getFullNameUz();
        };
    }

    private String getLocalizedPositionTitle(Leader leader, Language lang) {
        return switch (lang) {
            case ru -> leader.getPositionTitleRu();
            case en -> leader.getPositionTitleEn();
            default -> leader.getPositionTitleUz();
        };
    }

    private String getLocalizedAddress(Leader leader, Language lang) {
        return switch (lang) {
            case ru -> leader.getAddressRu();
            case en -> leader.getAddressEn();
            default -> leader.getAddressUz();
        };
    }

    private String getLocalizedReceptionTime(Leader leader, Language lang) {
        return switch (lang) {
            case ru -> leader.getReceptionTimeRu();
            case en -> leader.getReceptionTimeEn();
            default -> leader.getReceptionTimeUz();
        };
    }
}
