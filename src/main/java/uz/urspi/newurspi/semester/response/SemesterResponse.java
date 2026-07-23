package uz.urspi.newurspi.semester.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uz.urspi.newurspi.utils.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Schema(description = "Semester response")
public class SemesterResponse {
    private Long id;
    private String name;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
