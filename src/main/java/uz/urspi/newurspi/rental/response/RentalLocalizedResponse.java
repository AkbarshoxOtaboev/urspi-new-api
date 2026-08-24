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
@Schema(description = "Rental localized response")
public class RentalLocalizedResponse {
    private Long id;
    private String title;
    private String address;
    private String price;
    private String phoneNumber;
    private List<String> imageLinks;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
