package uz.urspi.newurspi.hemis.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.urspi.newurspi.hemis.sync.HemisSyncResource;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "HEMIS sync result")
public class HemisSyncResultResponse {
    private HemisSyncResource resource;
    private boolean success;
    private int fetchedCount;
    private int upsertedCount;
    private String message;
}
