package uz.urspi.newurspi.rental.mapper;

import org.springframework.stereotype.Component;
import uz.urspi.newurspi.rental.Rental;
import uz.urspi.newurspi.rental.response.RentalLocalizedResponse;
import uz.urspi.newurspi.rental.response.RentalResponse;
import uz.urspi.newurspi.utils.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RentalMapper {

    public RentalResponse toResponse(Rental entity) {
        if (entity == null) {
            return null;
        }
        return RentalResponse.builder()
                .id(entity.getId())
                .titleUz(entity.getTitleUz())
                .titleRu(entity.getTitleRu())
                .titleEn(entity.getTitleEn())
                .addressUz(entity.getAddressUz())
                .addressRu(entity.getAddressRu())
                .addressEn(entity.getAddressEn())
                .priceUz(entity.getPriceUz())
                .priceRu(entity.getPriceRu())
                .priceEn(entity.getPriceEn())
                .phoneNumber(entity.getPhoneNumber())
                .imageLinks(copyLinks(entity.getImageLinks()))
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public RentalLocalizedResponse toLocalizedResponse(Rental entity, Language lang) {
        if (entity == null) {
            return null;
        }
        return RentalLocalizedResponse.builder()
                .id(entity.getId())
                .title(switch (lang) {
                    case ru -> entity.getTitleRu();
                    case en -> entity.getTitleEn();
                    default -> entity.getTitleUz();
                })
                .address(switch (lang) {
                    case ru -> entity.getAddressRu();
                    case en -> entity.getAddressEn();
                    default -> entity.getAddressUz();
                })
                .price(switch (lang) {
                    case ru -> entity.getPriceRu();
                    case en -> entity.getPriceEn();
                    default -> entity.getPriceUz();
                })
                .phoneNumber(entity.getPhoneNumber())
                .imageLinks(copyLinks(entity.getImageLinks()))
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public List<RentalResponse> toResponseList(List<Rental> list) {
        return list.stream().map(this::toResponse).collect(Collectors.toCollection(ArrayList::new));
    }

    public List<RentalLocalizedResponse> toLocalizedResponseList(List<Rental> list, Language lang) {
        return list.stream().map(e -> toLocalizedResponse(e, lang)).collect(Collectors.toCollection(ArrayList::new));
    }

    private List<String> copyLinks(List<String> links) {
        return links == null ? new ArrayList<>() : new ArrayList<>(links);
    }
}
