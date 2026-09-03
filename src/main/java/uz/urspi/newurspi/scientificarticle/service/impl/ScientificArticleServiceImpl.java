package uz.urspi.newurspi.scientificarticle.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.urspi.newurspi.audit.AuditAction;
import uz.urspi.newurspi.audit.Auditable;
import uz.urspi.newurspi.exceptions.ResourceNotFoundException;
import uz.urspi.newurspi.scientificarticle.ScientificArticle;
import uz.urspi.newurspi.scientificarticle.dto.ScientificArticleDTO;
import uz.urspi.newurspi.scientificarticle.mapper.ScientificArticleMapper;
import uz.urspi.newurspi.scientificarticle.repository.ScientificArticleRepository;
import uz.urspi.newurspi.scientificarticle.response.ScientificArticleResponse;
import uz.urspi.newurspi.scientificarticle.service.ScientificArticleService;
import uz.urspi.newurspi.storage.StorageService;
import uz.urspi.newurspi.teacher.Teacher;
import uz.urspi.newurspi.teacher.repository.TeacherRepository;
import uz.urspi.newurspi.utils.CacheNames;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ScientificArticleServiceImpl implements ScientificArticleService {
    private final ScientificArticleRepository repository;
    private final ScientificArticleMapper mapper;
    private final TeacherRepository teacherRepository;
    private final StorageService storageService;

    @Override
    @CacheEvict(value = {CacheNames.SCIENTIFIC_ARTICLES, CacheNames.TEACHERS}, allEntries = true)
    @Auditable(action = AuditAction.CREATE, entity = "ScientificArticle")
    public ScientificArticleResponse create(ScientificArticleDTO dto) {
        Teacher teacher = getTeacherOrThrow(dto.getTeacherId());

        ScientificArticle article = ScientificArticle.builder()
                .teacher(teacher)
                .title(dto.getTitle())
                .type(dto.getType())
                .publicationYear(dto.getPublicationYear())
                .journalName(dto.getJournalName())
                .articleUrl(dto.getArticleUrl())
                .fileLink(storeFile(dto.getFile()))
                .status(Status.ACTIVE)
                .createdUsername(currentUsername())
                .build();

        ScientificArticle saved = repository.save(article);
        log.info("Scientific article created with id {}", saved.getId());
        return mapper.toResponse(saved);
    }

    @Override
    @Cacheable(value = CacheNames.SCIENTIFIC_ARTICLES, key = "#id")
    @Auditable(action = AuditAction.READ, entity = "ScientificArticle")
    public ScientificArticleResponse findById(Long id) {
        log.info("Find scientific article by id {}", id);
        return mapper.toResponse(getArticleOrThrow(id));
    }

    @Override
    @Cacheable(value = CacheNames.SCIENTIFIC_ARTICLES, key = "'all'")
    @Auditable(action = AuditAction.READ, entity = "ScientificArticle")
    public List<ScientificArticleResponse> fetchAll() {
        log.info("Fetch all scientific articles");
        return mapper.toResponseList(repository.findAllByOrderByPublicationYearDescIdDesc());
    }

    @Override
    @Cacheable(value = CacheNames.SCIENTIFIC_ARTICLES, key = "'teacher:' + #teacherId")
    @Auditable(action = AuditAction.READ, entity = "ScientificArticle")
    public List<ScientificArticleResponse> fetchByTeacherId(Long teacherId) {
        log.info("Fetch scientific articles by teacher id {}", teacherId);
        getTeacherOrThrow(teacherId);
        return mapper.toResponseList(
                repository.findAllByTeacherIdOrderByPublicationYearDescIdDesc(teacherId)
        );
    }

    @Override
    @CacheEvict(value = {CacheNames.SCIENTIFIC_ARTICLES, CacheNames.TEACHERS}, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "ScientificArticle")
    public ScientificArticleResponse update(Long id, ScientificArticleDTO dto) {
        log.info("Update scientific article with id {}", id);
        ScientificArticle article = getArticleOrThrow(id);
        Teacher teacher = getTeacherOrThrow(dto.getTeacherId());

        article.setTeacher(teacher);
        article.setTitle(dto.getTitle());
        article.setType(dto.getType());
        article.setPublicationYear(dto.getPublicationYear());
        article.setJournalName(dto.getJournalName());
        article.setArticleUrl(dto.getArticleUrl());

        String fileLink = storeFile(dto.getFile());
        if (fileLink != null) {
            article.setFileLink(fileLink);
        }

        return mapper.toResponse(repository.save(article));
    }

    @Override
    @CacheEvict(value = {CacheNames.SCIENTIFIC_ARTICLES, CacheNames.TEACHERS}, allEntries = true)
    @Auditable(action = AuditAction.DELETE, entity = "ScientificArticle")
    public void delete(Long id) {
        log.info("Delete scientific article by id {}", id);
        ScientificArticle article = getArticleOrThrow(id);
        article.setStatus(Status.DELETED);
    }

    @Override
    @CacheEvict(value = {CacheNames.SCIENTIFIC_ARTICLES, CacheNames.TEACHERS}, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "ScientificArticle")
    public void activeOrDisabled(Long id) {
        log.info("Disable or active scientific article with id {}", id);
        ScientificArticle article = getArticleOrThrow(id);
        if (article.getStatus() == Status.ACTIVE) {
            article.setStatus(Status.DISABLED);
        } else if (article.getStatus() == Status.DISABLED) {
            article.setStatus(Status.ACTIVE);
        }
    }

    private ScientificArticle getArticleOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Scientific article not found with id = " + id));
    }

    private Teacher getTeacherOrThrow(Long teacherId) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id = " + teacherId));
    }

    private String currentUsername() {
        return Objects.requireNonNull(SecurityContextHolder
                        .getContext()
                        .getAuthentication())
                .getName();
    }

    private String storeFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        return "/api/files/" + storageService.uploadFile(file);
    }
}
