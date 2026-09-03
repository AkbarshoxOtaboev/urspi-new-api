package uz.urspi.newurspi.landing.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.urspi.newurspi.announcement.Announcement;
import uz.urspi.newurspi.announcement.mapper.AnnouncementMapper;
import uz.urspi.newurspi.announcement.repository.AnnouncementRepository;
import uz.urspi.newurspi.announcement.response.AnnouncementLocalizedResponse;
import uz.urspi.newurspi.center.Center;
import uz.urspi.newurspi.center.mapper.CenterMapper;
import uz.urspi.newurspi.center.repository.CenterRepository;
import uz.urspi.newurspi.center.response.CenterLocalizedResponse;
import uz.urspi.newurspi.department.Department;
import uz.urspi.newurspi.department.mapper.DepartmentMapper;
import uz.urspi.newurspi.department.repository.DepartmentRepository;
import uz.urspi.newurspi.department.response.DepartmentLocalizedResponse;
import uz.urspi.newurspi.dormitory.Dormitory;
import uz.urspi.newurspi.dormitory.mapper.DormitoryMapper;
import uz.urspi.newurspi.dormitory.repository.DormitoryRepository;
import uz.urspi.newurspi.dormitory.response.DormitoryLocalizedResponse;
import uz.urspi.newurspi.employee.Employee;
import uz.urspi.newurspi.employee.mapper.EmployeeMapper;
import uz.urspi.newurspi.employee.repository.EmployeeRepository;
import uz.urspi.newurspi.employee.response.EmployeeLocalizedResponse;
import uz.urspi.newurspi.exceptions.ResourceNotFoundException;
import uz.urspi.newurspi.faculty.Faculty;
import uz.urspi.newurspi.faculty.mapper.FacultyMapper;
import uz.urspi.newurspi.faculty.repository.FacultyRepository;
import uz.urspi.newurspi.faculty.response.FacultyLocalizedResponse;
import uz.urspi.newurspi.facultystaff.FacultyStaff;
import uz.urspi.newurspi.facultystaff.mapper.FacultyStaffMapper;
import uz.urspi.newurspi.facultystaff.repository.FacultyStaffRepository;
import uz.urspi.newurspi.facultystaff.response.FacultyStaffLocalizedResponse;
import uz.urspi.newurspi.greeninstitute.GreenInstitute;
import uz.urspi.newurspi.greeninstitute.mapper.GreenInstituteMapper;
import uz.urspi.newurspi.greeninstitute.repository.GreenInstituteRepository;
import uz.urspi.newurspi.greeninstitute.response.GreenInstituteLocalizedResponse;
import uz.urspi.newurspi.landing.service.LandingService;
import uz.urspi.newurspi.leader.Leader;
import uz.urspi.newurspi.leader.mapper.LeaderMapper;
import uz.urspi.newurspi.leader.repository.LeaderRepository;
import uz.urspi.newurspi.leader.response.LeaderLocalizedResponse;
import uz.urspi.newurspi.news.News;
import uz.urspi.newurspi.news.mapper.NewsMapper;
import uz.urspi.newurspi.news.repository.NewsRepository;
import uz.urspi.newurspi.news.response.NewsLocalizedResponse;
import uz.urspi.newurspi.photogallery.PhotoGallery;
import uz.urspi.newurspi.photogallery.mapper.PhotoGalleryMapper;
import uz.urspi.newurspi.photogallery.repository.PhotoGalleryRepository;
import uz.urspi.newurspi.photogallery.response.PhotoGalleryLocalizedResponse;
import uz.urspi.newurspi.rental.Rental;
import uz.urspi.newurspi.rental.mapper.RentalMapper;
import uz.urspi.newurspi.rental.repository.RentalRepository;
import uz.urspi.newurspi.rental.response.RentalLocalizedResponse;
import uz.urspi.newurspi.scientificarticle.mapper.ScientificArticleMapper;
import uz.urspi.newurspi.scientificarticle.repository.ScientificArticleRepository;
import uz.urspi.newurspi.scientificarticle.response.ScientificArticleResponse;
import uz.urspi.newurspi.teacher.Teacher;
import uz.urspi.newurspi.teacher.mapper.TeacherMapper;
import uz.urspi.newurspi.teacher.repository.TeacherRepository;
import uz.urspi.newurspi.teacher.response.TeacherLocalizedResponse;
import uz.urspi.newurspi.utils.Language;
import uz.urspi.newurspi.utils.PageResponse;
import uz.urspi.newurspi.utils.Status;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LandingServiceImpl implements LandingService {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final AnnouncementRepository announcementRepository;
    private final AnnouncementMapper announcementMapper;
    private final FacultyRepository facultyRepository;
    private final FacultyMapper facultyMapper;
    private final FacultyStaffRepository facultyStaffRepository;
    private final FacultyStaffMapper facultyStaffMapper;
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final ScientificArticleRepository scientificArticleRepository;
    private final ScientificArticleMapper scientificArticleMapper;
    private final CenterRepository centerRepository;
    private final CenterMapper centerMapper;
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final LeaderRepository leaderRepository;
    private final LeaderMapper leaderMapper;
    private final PhotoGalleryRepository photoGalleryRepository;
    private final PhotoGalleryMapper photoGalleryMapper;
    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper;
    private final DormitoryRepository dormitoryRepository;
    private final DormitoryMapper dormitoryMapper;
    private final GreenInstituteRepository greenInstituteRepository;
    private final GreenInstituteMapper greenInstituteMapper;

    @Override
    public PageResponse<NewsLocalizedResponse> news(Language lang, Pageable pageable) {
        Page<News> page = newsRepository.findAllByStatusOrderByPublishedAtDescCreatedAtDesc(Status.ACTIVE, pageable);
        return PageResponse.of(page, newsMapper.toLocalizedResponseList(page.getContent(), lang));
    }

    @Override
    public NewsLocalizedResponse newsById(Long id, Language lang) {
        News news = newsRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("News not found with id = " + id));
        return newsMapper.toLocalizedResponse(news, lang);
    }

    @Override
    public PageResponse<AnnouncementLocalizedResponse> announcements(Language lang, Pageable pageable) {
        Page<Announcement> page = announcementRepository.findAllByStatusOrderByPublishedAtDescCreatedAtDesc(Status.ACTIVE, pageable);
        return PageResponse.of(page, announcementMapper.toLocalizedResponseList(page.getContent(), lang));
    }

    @Override
    public AnnouncementLocalizedResponse announcementById(Long id, Language lang) {
        Announcement announcement = announcementRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Announcement not found with id = " + id));
        return announcementMapper.toLocalizedResponse(announcement, lang);
    }

    @Override
    public PageResponse<FacultyLocalizedResponse> faculties(Language lang, Pageable pageable) {
        Page<Faculty> page = facultyRepository.findAllByStatusOrderByIdAsc(Status.ACTIVE, pageable);
        return PageResponse.of(page, facultyMapper.toLocalizedResponseList(page.getContent(), lang));
    }

    @Override
    public FacultyLocalizedResponse facultyById(Long id, Language lang) {
        Faculty faculty = facultyRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with id = " + id));
        return facultyMapper.toLocalizedResponse(faculty, lang);
    }

    @Override
    public PageResponse<FacultyStaffLocalizedResponse> facultyStaff(Language lang, Long facultyId, Pageable pageable) {
        Page<FacultyStaff> page = facultyId == null
                ? facultyStaffRepository.findAllByStatusOrderBySortOrderAsc(Status.ACTIVE, pageable)
                : facultyStaffRepository.findAllByStatusAndFacultyIdOrderBySortOrderAsc(Status.ACTIVE, facultyId, pageable);
        return PageResponse.of(page, facultyStaffMapper.toLocalizedResponseList(page.getContent(), lang));
    }

    @Override
    public FacultyStaffLocalizedResponse facultyStaffById(Long id, Language lang) {
        FacultyStaff staff = facultyStaffRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty staff not found with id = " + id));
        return facultyStaffMapper.toLocalizedResponse(staff, lang);
    }

    @Override
    public PageResponse<DepartmentLocalizedResponse> departments(Language lang, Long facultyId, Pageable pageable) {
        Page<Department> page = facultyId == null
                ? departmentRepository.findAllByStatusOrderByIdAsc(Status.ACTIVE, pageable)
                : departmentRepository.findAllByStatusAndFacultyIdOrderByIdAsc(Status.ACTIVE, facultyId, pageable);
        return PageResponse.of(page, departmentMapper.toLocalizedResponseList(page.getContent(), lang));
    }

    @Override
    public DepartmentLocalizedResponse departmentById(Long id, Language lang) {
        Department department = departmentRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id = " + id));
        return departmentMapper.toLocalizedResponse(department, lang);
    }

    @Override
    public PageResponse<TeacherLocalizedResponse> teachers(
            Language lang, Long facultyId, Long departmentId, Pageable pageable
    ) {
        Page<Teacher> page;
        if (facultyId != null && departmentId != null) {
            page = teacherRepository.findAllByStatusAndFacultyIdAndDepartmentIdOrderBySortOrderAsc(
                    Status.ACTIVE, facultyId, departmentId, pageable);
        } else if (facultyId != null) {
            page = teacherRepository.findAllByStatusAndFacultyIdOrderBySortOrderAsc(
                    Status.ACTIVE, facultyId, pageable);
        } else if (departmentId != null) {
            page = teacherRepository.findAllByStatusAndDepartmentIdOrderBySortOrderAsc(
                    Status.ACTIVE, departmentId, pageable);
        } else {
            page = teacherRepository.findAllByStatusOrderBySortOrderAsc(Status.ACTIVE, pageable);
        }
        return PageResponse.of(page, teacherMapper.toLocalizedResponseList(page.getContent(), lang));
    }

    @Override
    public TeacherLocalizedResponse teacherById(Long id, Language lang) {
        Teacher teacher = teacherRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id = " + id));
        TeacherLocalizedResponse response = teacherMapper.toLocalizedResponse(teacher, lang);
        response.setScientificArticles(scientificArticleMapper.toResponseList(
                scientificArticleRepository.findAllByTeacherIdAndStatusOrderByPublicationYearDescIdDesc(id, Status.ACTIVE)
        ));
        return response;
    }

    @Override
    public List<ScientificArticleResponse> teacherScientificArticles(Long teacherId) {
        teacherRepository.findByIdAndStatus(teacherId, Status.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id = " + teacherId));
        return scientificArticleMapper.toResponseList(
                scientificArticleRepository.findAllByTeacherIdAndStatusOrderByPublicationYearDescIdDesc(teacherId, Status.ACTIVE)
        );
    }

    @Override
    public PageResponse<CenterLocalizedResponse> centers(Language lang, Pageable pageable) {
        Page<Center> page = centerRepository.findAllByStatusOrderByIdAsc(Status.ACTIVE, pageable);
        return PageResponse.of(page, centerMapper.toLocalizedResponseList(page.getContent(), lang));
    }

    @Override
    public CenterLocalizedResponse centerById(Long id, Language lang) {
        Center center = centerRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Center not found with id = " + id));
        return centerMapper.toLocalizedResponse(center, lang);
    }

    @Override
    public PageResponse<EmployeeLocalizedResponse> employees(Language lang, Long centerId, Pageable pageable) {
        Page<Employee> page = centerId == null
                ? employeeRepository.findAllByStatusOrderBySortOrderAsc(Status.ACTIVE, pageable)
                : employeeRepository.findAllByStatusAndCenterIdOrderBySortOrderAsc(Status.ACTIVE, centerId, pageable);
        return PageResponse.of(page, employeeMapper.toLocalizedResponseList(page.getContent(), lang));
    }

    @Override
    public EmployeeLocalizedResponse employeeById(Long id, Language lang) {
        Employee employee = employeeRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id = " + id));
        return employeeMapper.toLocalizedResponse(employee, lang);
    }

    @Override
    public PageResponse<LeaderLocalizedResponse> leaders(Language lang, Pageable pageable) {
        Page<Leader> page = leaderRepository.findAllByStatusOrderBySortOrderAsc(Status.ACTIVE, pageable);
        return PageResponse.of(page, leaderMapper.toLocalizedResponseList(page.getContent(), lang));
    }

    @Override
    public LeaderLocalizedResponse leaderById(Long id, Language lang) {
        Leader leader = leaderRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Leader not found with id = " + id));
        return leaderMapper.toLocalizedResponse(leader, lang);
    }

    @Override
    public PageResponse<PhotoGalleryLocalizedResponse> photoGalleries(Language lang, Pageable pageable) {
        Page<PhotoGallery> page = photoGalleryRepository.findAllByStatusOrderByCreatedAtDesc(Status.ACTIVE, pageable);
        return PageResponse.of(page, photoGalleryMapper.toLocalizedResponseList(page.getContent(), lang));
    }

    @Override
    public PhotoGalleryLocalizedResponse photoGalleryById(Long id, Language lang) {
        PhotoGallery item = photoGalleryRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Photo gallery not found with id = " + id));
        return photoGalleryMapper.toLocalizedResponse(item, lang);
    }

    @Override
    public PageResponse<RentalLocalizedResponse> rentals(Language lang, Pageable pageable) {
        Page<Rental> page = rentalRepository.findAllByStatusOrderByCreatedAtDesc(Status.ACTIVE, pageable);
        return PageResponse.of(page, rentalMapper.toLocalizedResponseList(page.getContent(), lang));
    }

    @Override
    public RentalLocalizedResponse rentalById(Long id, Language lang) {
        Rental rental = rentalRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found with id = " + id));
        return rentalMapper.toLocalizedResponse(rental, lang);
    }

    @Override
    public PageResponse<DormitoryLocalizedResponse> dormitories(Language lang, Pageable pageable) {
        Page<Dormitory> page = dormitoryRepository.findAllByStatusOrderByCreatedAtDesc(Status.ACTIVE, pageable);
        return PageResponse.of(page, dormitoryMapper.toLocalizedResponseList(page.getContent(), lang));
    }

    @Override
    public DormitoryLocalizedResponse dormitoryById(Long id, Language lang) {
        Dormitory dormitory = dormitoryRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Dormitory not found with id = " + id));
        return dormitoryMapper.toLocalizedResponse(dormitory, lang);
    }

    @Override
    public PageResponse<GreenInstituteLocalizedResponse> greenInstitutes(Language lang, Pageable pageable) {
        Page<GreenInstitute> page = greenInstituteRepository.findAllByStatusOrderByCreatedAtDesc(Status.ACTIVE, pageable);
        return PageResponse.of(page, greenInstituteMapper.toLocalizedResponseList(page.getContent(), lang));
    }

    @Override
    public GreenInstituteLocalizedResponse greenInstituteById(Long id, Language lang) {
        GreenInstitute item = greenInstituteRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Green institute not found with id = " + id));
        return greenInstituteMapper.toLocalizedResponse(item, lang);
    }
}
