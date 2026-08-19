package uz.urspi.newurspi.news.service.impl;

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
import uz.urspi.newurspi.news.News;
import uz.urspi.newurspi.news.dto.NewsDTO;
import uz.urspi.newurspi.news.mapper.NewsMapper;
import uz.urspi.newurspi.news.repository.NewsRepository;
import uz.urspi.newurspi.news.response.NewsLocalizedResponse;
import uz.urspi.newurspi.news.response.NewsResponse;
import uz.urspi.newurspi.news.service.NewsService;
import uz.urspi.newurspi.storage.StorageService;
import uz.urspi.newurspi.utils.CacheNames;
import uz.urspi.newurspi.utils.Language;
import uz.urspi.newurspi.utils.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NewsServiceImpl implements NewsService {
    private final NewsRepository repository;
    private final NewsMapper mapper;
    private final StorageService storageService;

    @Override
    @CacheEvict(value = CacheNames.NEWS, allEntries = true)
    @Auditable(action = AuditAction.CREATE, entity = "News")
    public NewsResponse create(NewsDTO dto) {
        String username = currentUsername();

        News news = News.builder()
                .titleUz(dto.getTitleUz())
                .titleRu(dto.getTitleRu())
                .titleEn(dto.getTitleEn())
                .contentUz(dto.getContentUz())
                .contentRu(dto.getContentRu())
                .contentEn(dto.getContentEn())
                .author(dto.getAuthor())
                .mainImageLink(storeFile(dto.getMainImage()))
                .imageLinks(storeFiles(dto.getImages()))
                .status(Status.ACTIVE)
                .createdUsername(username)
                .build();

        News saved = repository.save(news);
        log.info("News created with id {}", saved.getId());
        return mapper.toResponse(saved);
    }

    @Override
    @Cacheable(value = CacheNames.NEWS, key = "#id")
    @Auditable(action = AuditAction.READ, entity = "News")
    public NewsResponse findById(Long id) {
        log.info("Find news by id {}", id);
        return mapper.toResponse(getNewsOrThrow(id));
    }

    @Override
    @Cacheable(value = CacheNames.NEWS, key = "'all'")
    @Auditable(action = AuditAction.READ, entity = "News")
    public List<NewsResponse> fetchAllNews() {
        log.info("Fetch all news");
        return mapper.toResponseList(repository.findAllByOrderByCreatedAtDesc());
    }

    @Override
    @Cacheable(value = CacheNames.NEWS, key = "'lang_' + #lang.name()")
    @Auditable(action = AuditAction.READ, entity = "News")
    public List<NewsLocalizedResponse> fetchAllNewsByLang(Language lang) {
        log.info("Fetch all news by language {}", lang);
        return mapper.toLocalizedResponseList(repository.findAllByOrderByCreatedAtDesc(), lang);
    }

    @Override
    @CacheEvict(value = CacheNames.NEWS, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "News")
    public NewsResponse update(Long id, NewsDTO dto) {
        log.info("Update news with id {}", id);

        News news = getNewsOrThrow(id);

        news.setTitleUz(dto.getTitleUz());
        news.setTitleRu(dto.getTitleRu());
        news.setTitleEn(dto.getTitleEn());
        news.setContentUz(dto.getContentUz());
        news.setContentRu(dto.getContentRu());
        news.setContentEn(dto.getContentEn());
        news.setAuthor(dto.getAuthor());

        String mainImageLink = storeFile(dto.getMainImage());
        if (mainImageLink != null) {
            news.setMainImageLink(mainImageLink);
        }

        List<String> newImageLinks = storeFiles(dto.getImages());
        if (!newImageLinks.isEmpty()) {
            news.setImageLinks(newImageLinks);
        }

        News updated = repository.save(news);
        return mapper.toResponse(updated);
    }

    @Override
    @CacheEvict(value = CacheNames.NEWS, allEntries = true)
    @Auditable(action = AuditAction.DELETE, entity = "News")
    public void delete(Long id) {
        log.info("Delete news by id {}", id);
        News news = getNewsOrThrow(id);
        news.setStatus(Status.DELETED);
    }

    @Override
    @CacheEvict(value = CacheNames.NEWS, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "News")
    public void activeOrDisabledNews(Long id) {
        log.info("Disable or active news with id {}", id);
        News news = getNewsOrThrow(id);
        if (news.getStatus() == Status.ACTIVE) {
            log.info("Disabled news {}", news.getTitleUz());
            news.setStatus(Status.DISABLED);
        } else if (news.getStatus() == Status.DISABLED) {
            log.info("Activate news {}", news.getTitleUz());
            news.setStatus(Status.ACTIVE);
        }
    }

    private News getNewsOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("News not found with id = " + id));
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

    private List<String> storeFiles(List<MultipartFile> files) {
        if (files == null || files.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> links = new ArrayList<>();
        for (MultipartFile file : files) {
            String link = storeFile(file);
            if (link != null) {
                links.add(link);
            }
        }
        return links;
    }
}
