package uz.urspi.newurspi.leader.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.urspi.newurspi.utils.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Leader response")
public class LeaderResponse {
    private Long id;
    private String fullNameUz;
    private String fullNameRu;
    private String fullNameEn;
    private String positionTitleUz;
    private String positionTitleRu;
    private String positionTitleEn;
    private String addressUz;
    private String addressRu;
    private String addressEn;
    private String receptionTimeUz;
    private String receptionTimeRu;
    private String receptionTimeEn;
    private String phoneNumber;
    private String email;
    private String photoLink;
    private Integer sortOrder;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
