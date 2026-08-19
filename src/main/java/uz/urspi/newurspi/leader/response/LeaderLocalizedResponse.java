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
@Schema(description = "Leader localized response")
public class LeaderLocalizedResponse {
    private Long id;
    private String fullName;
    private String positionTitle;
    private String address;
    private String receptionTime;
    private String phoneNumber;
    private String email;
    private String photoLink;
    private Integer sortOrder;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
