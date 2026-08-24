package uz.urspi.newurspi.rental.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Schema(description = "Student rental create/update dto")
public class RentalDTO {

    @NotEmpty(message = "Rental title (uz) cannot be empty")
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

    @Schema(description = "House photos (max 10)")
    private List<MultipartFile> images;
}
