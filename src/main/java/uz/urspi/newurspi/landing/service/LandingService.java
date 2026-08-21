package uz.urspi.newurspi.landing.service;

import org.springframework.data.domain.Pageable;
import uz.urspi.newurspi.announcement.response.AnnouncementLocalizedResponse;
import uz.urspi.newurspi.center.response.CenterLocalizedResponse;
import uz.urspi.newurspi.department.response.DepartmentLocalizedResponse;
import uz.urspi.newurspi.employee.response.EmployeeLocalizedResponse;
import uz.urspi.newurspi.faculty.response.FacultyLocalizedResponse;
import uz.urspi.newurspi.leader.response.LeaderLocalizedResponse;
import uz.urspi.newurspi.news.response.NewsLocalizedResponse;
import uz.urspi.newurspi.teacher.response.TeacherLocalizedResponse;
import uz.urspi.newurspi.utils.Language;
import uz.urspi.newurspi.utils.PageResponse;

public interface LandingService {

    PageResponse<NewsLocalizedResponse> news(Language lang, Pageable pageable);

    NewsLocalizedResponse newsById(Long id, Language lang);

    PageResponse<AnnouncementLocalizedResponse> announcements(Language lang, Pageable pageable);

    AnnouncementLocalizedResponse announcementById(Long id, Language lang);

    PageResponse<FacultyLocalizedResponse> faculties(Language lang, Pageable pageable);

    FacultyLocalizedResponse facultyById(Long id, Language lang);

    PageResponse<DepartmentLocalizedResponse> departments(Language lang, Long facultyId, Pageable pageable);

    DepartmentLocalizedResponse departmentById(Long id, Language lang);

    PageResponse<TeacherLocalizedResponse> teachers(Language lang, Long facultyId, Long departmentId, Pageable pageable);

    TeacherLocalizedResponse teacherById(Long id, Language lang);

    PageResponse<CenterLocalizedResponse> centers(Language lang, Pageable pageable);

    CenterLocalizedResponse centerById(Long id, Language lang);

    PageResponse<EmployeeLocalizedResponse> employees(Language lang, Long centerId, Pageable pageable);

    EmployeeLocalizedResponse employeeById(Long id, Language lang);

    PageResponse<LeaderLocalizedResponse> leaders(Language lang, Pageable pageable);

    LeaderLocalizedResponse leaderById(Long id, Language lang);
}
