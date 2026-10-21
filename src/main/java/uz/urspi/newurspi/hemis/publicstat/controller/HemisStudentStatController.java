package uz.urspi.newurspi.hemis.publicstat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.urspi.newurspi.hemis.publicstat.response.StudentStatResponse;
import uz.urspi.newurspi.hemis.publicstat.response.StudentStatResponseApi;
import uz.urspi.newurspi.hemis.publicstat.service.HemisStudentStatService;
import uz.urspi.newurspi.utils.RestApiResponse;

@RestController
@RequestMapping("/api/hemis/public")
@RequiredArgsConstructor
@Tag(name = "HEMIS Public Stats", description = "HEMIS Public API orqali olinadigan statistika (masalan stat-student)")
public class HemisStudentStatController {

    private final HemisStudentStatService service;

    @GetMapping("/stat-student")
    @Operation(
            summary = "O'quvchilar statistikasi (HEMIS /v1/public/stat-student)",
            description = "Aktiv HEMIS config baseUrl orqali Public API dan ma'lumot oladi. Token talab qilinmaydi."
    )
    @ApiResponse(responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StudentStatResponseApi.class)
            ))
    public ResponseEntity<RestApiResponse<StudentStatResponse>> studentStats(
            @Parameter(description = "Til kodi: uz-UZ, oz-UZ, ru-RU, en-US")
            @RequestParam(required = false) String l
    ) {
        return ResponseEntity.ok(
                RestApiResponse.<StudentStatResponse>builder()
                        .message("HEMIS student statistics fetched")
                        .data(service.fetchStudentStats(l))
                        .build()
        );
    }
}
