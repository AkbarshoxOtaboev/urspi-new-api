package uz.urspi.newurspi.rental.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import uz.urspi.newurspi.utils.Status;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Rental response")
public class RentalResponse {
    private Long id;
    private String titleUz;
    private String titleRu;
    private String titleEn;
    private String addressUz;
    private String addressRu;
    private String addressEn;
    private String priceUz;
    private String priceRu;
    private String priceEn;
    private String phoneNumber;
    private List<String> imageLinks;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
