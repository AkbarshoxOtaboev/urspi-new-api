package uz.urspi.newurspi.teacher.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.urspi.newurspi.academicdegree.response.AcademicDegreeResponse;
import uz.urspi.newurspi.department.response.DepartmentResponse;
import uz.urspi.newurspi.faculty.response.FacultyResponse;
import uz.urspi.newurspi.position.response.PositionResponse;
import uz.urspi.newurspi.scientificarticle.response.ScientificArticleResponse;
import uz.urspi.newurspi.utils.Status;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Teacher response")
public class TeacherResponse {
    private Long id;
    private String fullNameUz;
    private String fullNameRu;
    private String fullNameEn;
    private String phoneNumber;
    private String email;
    private String photoLink;
    private String cvLink;
    private Integer sortOrder;
    private FacultyResponse faculty;
    private DepartmentResponse department;
    private PositionResponse position;
    private AcademicDegreeResponse academicDegree;
    private List<ScientificArticleResponse> scientificArticles;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
