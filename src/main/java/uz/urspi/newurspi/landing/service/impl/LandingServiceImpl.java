package uz.urspi.newurspi.landing.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import uz.urspi.newurspi.menu.Menu;
import uz.urspi.newurspi.menu.MenuCategory;
import uz.urspi.newurspi.menu.MenuContentType;
import uz.urspi.newurspi.menu.MenuInfo;
import uz.urspi.newurspi.menu.MenuMaterial;
import uz.urspi.newurspi.menu.MenuSubCategory;
import uz.urspi.newurspi.menu.mapper.MenuMapper;
import uz.urspi.newurspi.menu.repository.MenuCategoryRepository;
import uz.urspi.newurspi.menu.repository.MenuInfoRepository;
import uz.urspi.newurspi.menu.repository.MenuMaterialRepository;
import uz.urspi.newurspi.menu.repository.MenuRepository;
import uz.urspi.newurspi.menu.repository.MenuSubCategoryRepository;
import uz.urspi.newurspi.menu.response.MenuCategoryLocalizedResponse;
import uz.urspi.newurspi.menu.response.MenuInfoLocalizedResponse;
import uz.urspi.newurspi.menu.response.MenuInfosPageResponse;
import uz.urspi.newurspi.menu.response.MenuLocalizedResponse;
import uz.urspi.newurspi.menu.response.MenuMaterialLocalizedResponse;
import uz.urspi.newurspi.menu.response.MenuSubCategoryLocalizedResponse;
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

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LandingServiceImpl implements LandingService {

    private final MenuRepository menuRepository;
    private final MenuCategoryRepository menuCategoryRepository;
    private final MenuSubCategoryRepository menuSubCategoryRepository;
    private final MenuInfoRepository menuInfoRepository;
    private final MenuMaterialRepository menuMaterialRepository;
    private final MenuMapper menuMapper;
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
        Page<News> page = newsRepository.findAllByStatusOrderByPublishedAtDescCreatedAtDesc(Status.ACTIVE, unsorted(pageable));
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
        Page<Announcement> page = announcementRepository.findAllByStatusOrderByPublishedAtDescCreatedAtDesc(Status.ACTIVE, unsorted(pageable));
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
        Page<Faculty> page = facultyRepository.findAllByStatusOrderByIdAsc(Status.ACTIVE, unsorted(pageable));
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
                ? facultyStaffRepository.findAllByStatusOrderBySortOrderAsc(Status.ACTIVE, unsorted(pageable))
                : facultyStaffRepository.findAllByStatusAndFacultyIdOrderBySortOrderAsc(Status.ACTIVE, facultyId, unsorted(pageable));
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
                ? departmentRepository.findAllByStatusOrderByIdAsc(Status.ACTIVE, unsorted(pageable))
                : departmentRepository.findAllByStatusAndFacultyIdOrderByIdAsc(Status.ACTIVE, facultyId, unsorted(pageable));
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
                    Status.ACTIVE, facultyId, departmentId, unsorted(pageable));
        } else if (facultyId != null) {
            page = teacherRepository.findAllByStatusAndFacultyIdOrderBySortOrderAsc(
                    Status.ACTIVE, facultyId, unsorted(pageable));
        } else if (departmentId != null) {
            page = teacherRepository.findAllByStatusAndDepartmentIdOrderBySortOrderAsc(
                    Status.ACTIVE, departmentId, unsorted(pageable));
        } else {
            page = teacherRepository.findAllByStatusOrderBySortOrderAsc(Status.ACTIVE, unsorted(pageable));
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
        Page<Center> page = centerRepository.findAllByStatusOrderByIdAsc(Status.ACTIVE, unsorted(pageable));
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
                ? employeeRepository.findAllByStatusOrderBySortOrderAsc(Status.ACTIVE, unsorted(pageable))
                : employeeRepository.findAllByStatusAndCenterIdOrderBySortOrderAsc(Status.ACTIVE, centerId, unsorted(pageable));
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
        Page<Leader> page = leaderRepository.findAllByStatusOrderBySortOrderAsc(Status.ACTIVE, unsorted(pageable));
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
        Page<PhotoGallery> page = photoGalleryRepository.findAllByStatusOrderByCreatedAtDesc(Status.ACTIVE, unsorted(pageable));
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
        Page<Rental> page = rentalRepository.findAllByStatusOrderByCreatedAtDesc(Status.ACTIVE, unsorted(pageable));
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
        Page<Dormitory> page = dormitoryRepository.findAllByStatusOrderByCreatedAtDesc(Status.ACTIVE, unsorted(pageable));
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
        Page<GreenInstitute> page = greenInstituteRepository.findAllByStatusOrderByCreatedAtDesc(Status.ACTIVE, unsorted(pageable));
        return PageResponse.of(page, greenInstituteMapper.toLocalizedResponseList(page.getContent(), lang));
    }

    @Override
    public GreenInstituteLocalizedResponse greenInstituteById(Long id, Language lang) {
        GreenInstitute item = greenInstituteRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Green institute not found with id = " + id));
        return greenInstituteMapper.toLocalizedResponse(item, lang);
    }

    @Override
    public List<MenuLocalizedResponse> menus(Language lang) {
        List<Menu> menus = menuRepository.findAllByStatusOrderBySortOrderAscIdAsc(Status.ACTIVE);
        return menuMapper.toMenuLocalizedList(menus, lang);
    }

    @Override
    public List<MenuCategoryLocalizedResponse> menuCategories(Long menuId, Language lang) {
        menuRepository.findByIdAndStatus(menuId, Status.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found with id = " + menuId));
        return menuMapper.toCategoryLocalizedList(
                menuCategoryRepository.findAllByMenuIdAndStatusOrderBySortOrderAscIdAsc(menuId, Status.ACTIVE),
                lang
        );
    }

    @Override
    public List<MenuSubCategoryLocalizedResponse> menuSubCategories(Long categoryId, Language lang) {
        menuCategoryRepository.findByIdAndStatus(categoryId, Status.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Menu category not found with id = " + categoryId));
        return menuMapper.toSubCategoryLocalizedList(
                menuSubCategoryRepository.findAllByCategoryIdAndStatusOrderBySortOrderAscIdAsc(categoryId, Status.ACTIVE),
                lang
        );
    }

    @Override
    public MenuSubCategoryLocalizedResponse menuSubCategoryById(Long id, Language lang) {
        MenuSubCategory sub = menuSubCategoryRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Menu subcategory not found with id = " + id));
        return menuMapper.toLocalized(sub, lang);
    }

    @Override
    public MenuInfosPageResponse menuInfosBySubCategory(Long subCategoryId, Language lang, Pageable pageable) {
        MenuSubCategory sub = menuSubCategoryRepository.findByIdAndStatus(subCategoryId, Status.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Menu subcategory not found with id = " + subCategoryId));
        MenuSubCategoryLocalizedResponse subLocalized = menuMapper.toLocalized(sub, lang);
        if (sub.getContentType() == MenuContentType.LINK) {
            return MenuInfosPageResponse.builder()
                    .subCategory(subLocalized)
                    .contentType(sub.getContentType())
                    .linkPath(sub.getLinkPath())
                    .linkTargetType(sub.getLinkTargetType())
                    .infos(Collections.emptyList())
                    .totalElements(0)
                    .totalPages(0)
                    .number(pageable.getPageNumber())
                    .size(pageable.getPageSize())
                    .build();
        }
        Page<MenuInfo> page = menuInfoRepository.findAllBySubCategoryIdAndStatusOrderByCreatedAtDesc(
                subCategoryId, Status.ACTIVE, unsorted(pageable));
        return MenuInfosPageResponse.builder()
                .subCategory(subLocalized)
                .contentType(sub.getContentType())
                .linkPath(null)
                .linkTargetType(null)
                .infos(menuMapper.toInfoLocalizedList(page.getContent(), lang))
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .number(page.getNumber())
                .size(page.getSize())
                .build();
    }

    @Override
    public MenuInfoLocalizedResponse menuInfoById(Long id, Language lang) {
        MenuInfo info = menuInfoRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Menu info not found with id = " + id));
        List<Integer> years = menuMaterialRepository.findDistinctYearsByInfoId(id, Status.ACTIVE);
        return menuMapper.toLocalized(info, lang, years);
    }

    @Override
    public PageResponse<MenuMaterialLocalizedResponse> menuMaterials(
            Long infoId, Integer year, Language lang, Pageable pageable
    ) {
        menuInfoRepository.findByIdAndStatus(infoId, Status.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Menu info not found with id = " + infoId));
        Pageable p = unsorted(pageable);
        Page<MenuMaterial> page = year == null
                ? menuMaterialRepository.findAllByInfoIdAndStatusOrderByCreatedAtDesc(infoId, Status.ACTIVE, p)
                : menuMaterialRepository.findAllByInfoIdAndYearAndStatusOrderByCreatedAtDesc(infoId, year, Status.ACTIVE, p);
        return PageResponse.of(page, menuMapper.toMaterialLocalizedList(page.getContent(), lang));
    }

    /**
     * Strip client Sort (Swagger often sends sort=string) so repository OrderBy* methods control ordering.
     */
    private static Pageable unsorted(Pageable pageable) {
        if (pageable == null || pageable.isUnpaged()) {
            return pageable;
        }
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
    }
}
