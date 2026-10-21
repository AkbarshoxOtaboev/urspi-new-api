package uz.urspi.newurspi.menu.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuMaterialLocalizedResponse {
    private Long id;
    private Long infoId;
    private Integer year;
    private String title;
    private String fileLink;
    private String coverLink;
    private LocalDate publishedAt;
    private LocalDateTime createdAt;
}
