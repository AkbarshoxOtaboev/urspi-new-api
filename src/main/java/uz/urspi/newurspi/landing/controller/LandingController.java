package uz.urspi.newurspi.landing.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.urspi.newurspi.announcement.response.AnnouncementLocalizedResponse;
import uz.urspi.newurspi.center.response.CenterLocalizedResponse;
import uz.urspi.newurspi.department.response.DepartmentLocalizedResponse;
import uz.urspi.newurspi.employee.response.EmployeeLocalizedResponse;
import uz.urspi.newurspi.faculty.response.FacultyLocalizedResponse;
import uz.urspi.newurspi.landing.service.LandingService;
import uz.urspi.newurspi.leader.response.LeaderLocalizedResponse;
import uz.urspi.newurspi.news.response.NewsLocalizedResponse;
import uz.urspi.newurspi.teacher.response.TeacherLocalizedResponse;
import uz.urspi.newurspi.utils.Language;
import uz.urspi.newurspi.utils.PageResponse;
import uz.urspi.newurspi.utils.RestApiResponse;

@RestController
@RequestMapping("/api/landing")
@RequiredArgsConstructor
@Tag(name = "Landing (public)", description = "Ochiq landing API — token kerak emas")
public class LandingController {

    private final LandingService landingService;

    @GetMapping("/news")
    @Operation(summary = "Yangiliklar (pageable + lang)")
    public ResponseEntity<RestApiResponse<PageResponse<NewsLocalizedResponse>>> news(
            @RequestParam(defaultValue = "uz") Language lang,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(RestApiResponse.<PageResponse<NewsLocalizedResponse>>builder()
                .message("News fetched successfully")
                .data(landingService.news(lang, pageable))
                .build());
    }

    @GetMapping("/news/{id}")
    @Operation(summary = "Yangilik ID bo'yicha")
    public ResponseEntity<RestApiResponse<NewsLocalizedResponse>> newsById(
            @PathVariable Long id,
            @RequestParam(defaultValue = "uz") Language lang
    ) {
        return ResponseEntity.ok(RestApiResponse.<NewsLocalizedResponse>builder()
                .message("News fetched successfully")
                .data(landingService.newsById(id, lang))
                .build());
    }

    @GetMapping("/announcements")
    @Operation(summary = "E'lonlar (pageable + lang)")
    public ResponseEntity<RestApiResponse<PageResponse<AnnouncementLocalizedResponse>>> announcements(
            @RequestParam(defaultValue = "uz") Language lang,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(RestApiResponse.<PageResponse<AnnouncementLocalizedResponse>>builder()
                .message("Announcements fetched successfully")
                .data(landingService.announcements(lang, pageable))
                .build());
    }

    @GetMapping("/announcements/{id}")
    @Operation(summary = "E'lon ID bo'yicha")
    public ResponseEntity<RestApiResponse<AnnouncementLocalizedResponse>> announcementById(
            @PathVariable Long id,
            @RequestParam(defaultValue = "uz") Language lang
    ) {
        return ResponseEntity.ok(RestApiResponse.<AnnouncementLocalizedResponse>builder()
                .message("Announcement fetched successfully")
                .data(landingService.announcementById(id, lang))
                .build());
    }

    @GetMapping("/leaders")
    @Operation(summary = "Rahbariyat (pageable + lang)")
    public ResponseEntity<RestApiResponse<PageResponse<LeaderLocalizedResponse>>> leaders(
            @RequestParam(defaultValue = "uz") Language lang,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(RestApiResponse.<PageResponse<LeaderLocalizedResponse>>builder()
                .message("Leaders fetched successfully")
                .data(landingService.leaders(lang, pageable))
                .build());
    }

    @GetMapping("/leaders/{id}")
    @Operation(summary = "Rahbar ID bo'yicha")
    public ResponseEntity<RestApiResponse<LeaderLocalizedResponse>> leaderById(
            @PathVariable Long id,
            @RequestParam(defaultValue = "uz") Language lang
    ) {
        return ResponseEntity.ok(RestApiResponse.<LeaderLocalizedResponse>builder()
                .message("Leader fetched successfully")
                .data(landingService.leaderById(id, lang))
                .build());
    }

    @GetMapping("/faculties")
    @Operation(summary = "Fakultetlar (pageable + lang)")
    public ResponseEntity<RestApiResponse<PageResponse<FacultyLocalizedResponse>>> faculties(
            @RequestParam(defaultValue = "uz") Language lang,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(RestApiResponse.<PageResponse<FacultyLocalizedResponse>>builder()
                .message("Faculties fetched successfully")
                .data(landingService.faculties(lang, pageable))
                .build());
    }

    @GetMapping("/faculties/{id}")
    @Operation(summary = "Fakultet ID bo'yicha")
    public ResponseEntity<RestApiResponse<FacultyLocalizedResponse>> facultyById(
            @PathVariable Long id,
            @RequestParam(defaultValue = "uz") Language lang
    ) {
        return ResponseEntity.ok(RestApiResponse.<FacultyLocalizedResponse>builder()
                .message("Faculty fetched successfully")
                .data(landingService.facultyById(id, lang))
                .build());
    }

    @GetMapping("/faculties/{facultyId}/departments")
    @Operation(summary = "Fakultetga tegishli kafedralar")
    public ResponseEntity<RestApiResponse<PageResponse<DepartmentLocalizedResponse>>> departmentsByFaculty(
            @PathVariable Long facultyId,
            @RequestParam(defaultValue = "uz") Language lang,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(RestApiResponse.<PageResponse<DepartmentLocalizedResponse>>builder()
                .message("Departments fetched successfully")
                .data(landingService.departments(lang, facultyId, pageable))
                .build());
    }

    @GetMapping("/faculties/{facultyId}/teachers")
    @Operation(summary = "Fakultetga tegishli o'qituvchilar")
    public ResponseEntity<RestApiResponse<PageResponse<TeacherLocalizedResponse>>> teachersByFaculty(
            @PathVariable Long facultyId,
            @RequestParam(defaultValue = "uz") Language lang,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(RestApiResponse.<PageResponse<TeacherLocalizedResponse>>builder()
                .message("Teachers fetched successfully")
                .data(landingService.teachers(lang, facultyId, null, pageable))
                .build());
    }

    @GetMapping("/departments")
    @Operation(summary = "Kafedralar (pageable + lang, optional facultyId)")
    public ResponseEntity<RestApiResponse<PageResponse<DepartmentLocalizedResponse>>> departments(
            @RequestParam(defaultValue = "uz") Language lang,
            @RequestParam(required = false) Long facultyId,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(RestApiResponse.<PageResponse<DepartmentLocalizedResponse>>builder()
                .message("Departments fetched successfully")
                .data(landingService.departments(lang, facultyId, pageable))
                .build());
    }

    @GetMapping("/departments/{id}")
    @Operation(summary = "Kafedra ID bo'yicha")
    public ResponseEntity<RestApiResponse<DepartmentLocalizedResponse>> departmentById(
            @PathVariable Long id,
            @RequestParam(defaultValue = "uz") Language lang
    ) {
        return ResponseEntity.ok(RestApiResponse.<DepartmentLocalizedResponse>builder()
                .message("Department fetched successfully")
                .data(landingService.departmentById(id, lang))
                .build());
    }

    @GetMapping("/departments/{departmentId}/teachers")
    @Operation(summary = "Kafedraga tegishli o'qituvchilar")
    public ResponseEntity<RestApiResponse<PageResponse<TeacherLocalizedResponse>>> teachersByDepartment(
            @PathVariable Long departmentId,
            @RequestParam(defaultValue = "uz") Language lang,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(RestApiResponse.<PageResponse<TeacherLocalizedResponse>>builder()
                .message("Teachers fetched successfully")
                .data(landingService.teachers(lang, null, departmentId, pageable))
                .build());
    }

    @GetMapping("/faculties/{facultyId}/departments/{departmentId}/teachers")
    @Operation(summary = "Fakultet va kafedraga tegishli o'qituvchilar")
    public ResponseEntity<RestApiResponse<PageResponse<TeacherLocalizedResponse>>> teachersByFacultyAndDepartment(
            @PathVariable Long facultyId,
            @PathVariable Long departmentId,
            @RequestParam(defaultValue = "uz") Language lang,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(RestApiResponse.<PageResponse<TeacherLocalizedResponse>>builder()
                .message("Teachers fetched successfully")
                .data(landingService.teachers(lang, facultyId, departmentId, pageable))
                .build());
    }

    @GetMapping("/teachers")
    @Operation(summary = "O'qituvchilar (pageable + lang, optional facultyId/departmentId)")
    public ResponseEntity<RestApiResponse<PageResponse<TeacherLocalizedResponse>>> teachers(
            @RequestParam(defaultValue = "uz") Language lang,
            @RequestParam(required = false) Long facultyId,
            @RequestParam(required = false) Long departmentId,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(RestApiResponse.<PageResponse<TeacherLocalizedResponse>>builder()
                .message("Teachers fetched successfully")
                .data(landingService.teachers(lang, facultyId, departmentId, pageable))
                .build());
    }

    @GetMapping("/teachers/{id}")
    @Operation(summary = "O'qituvchi ID bo'yicha")
    public ResponseEntity<RestApiResponse<TeacherLocalizedResponse>> teacherById(
            @PathVariable Long id,
            @RequestParam(defaultValue = "uz") Language lang
    ) {
        return ResponseEntity.ok(RestApiResponse.<TeacherLocalizedResponse>builder()
                .message("Teacher fetched successfully")
                .data(landingService.teacherById(id, lang))
                .build());
    }

    @GetMapping("/centers")
    @Operation(summary = "Markaz va bo'limlar (pageable + lang)")
    public ResponseEntity<RestApiResponse<PageResponse<CenterLocalizedResponse>>> centers(
            @RequestParam(defaultValue = "uz") Language lang,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(RestApiResponse.<PageResponse<CenterLocalizedResponse>>builder()
                .message("Centers fetched successfully")
                .data(landingService.centers(lang, pageable))
                .build());
    }

    @GetMapping("/centers/{id}")
    @Operation(summary = "Markaz/bo'lim ID bo'yicha")
    public ResponseEntity<RestApiResponse<CenterLocalizedResponse>> centerById(
            @PathVariable Long id,
            @RequestParam(defaultValue = "uz") Language lang
    ) {
        return ResponseEntity.ok(RestApiResponse.<CenterLocalizedResponse>builder()
                .message("Center fetched successfully")
                .data(landingService.centerById(id, lang))
                .build());
    }

    @GetMapping("/centers/{centerId}/employees")
    @Operation(summary = "Markazga tegishli hodimlar")
    public ResponseEntity<RestApiResponse<PageResponse<EmployeeLocalizedResponse>>> employeesByCenter(
            @PathVariable Long centerId,
            @RequestParam(defaultValue = "uz") Language lang,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(RestApiResponse.<PageResponse<EmployeeLocalizedResponse>>builder()
                .message("Employees fetched successfully")
                .data(landingService.employees(lang, centerId, pageable))
                .build());
    }

    @GetMapping("/employees")
    @Operation(summary = "Hodimlar (pageable + lang, optional centerId)")
    public ResponseEntity<RestApiResponse<PageResponse<EmployeeLocalizedResponse>>> employees(
            @RequestParam(defaultValue = "uz") Language lang,
            @RequestParam(required = false) Long centerId,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(RestApiResponse.<PageResponse<EmployeeLocalizedResponse>>builder()
                .message("Employees fetched successfully")
                .data(landingService.employees(lang, centerId, pageable))
                .build());
    }

    @GetMapping("/employees/{id}")
    @Operation(summary = "Hodim ID bo'yicha")
    public ResponseEntity<RestApiResponse<EmployeeLocalizedResponse>> employeeById(
            @PathVariable Long id,
            @RequestParam(defaultValue = "uz") Language lang
    ) {
        return ResponseEntity.ok(RestApiResponse.<EmployeeLocalizedResponse>builder()
                .message("Employee fetched successfully")
                .data(landingService.employeeById(id, lang))
                .build());
    }
}
