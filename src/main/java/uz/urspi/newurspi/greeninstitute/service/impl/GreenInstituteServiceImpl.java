package uz.urspi.newurspi.greeninstitute.service.impl;

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
import uz.urspi.newurspi.greeninstitute.GreenInstitute;
import uz.urspi.newurspi.greeninstitute.dto.GreenInstituteDTO;
import uz.urspi.newurspi.greeninstitute.mapper.GreenInstituteMapper;
import uz.urspi.newurspi.greeninstitute.repository.GreenInstituteRepository;
import uz.urspi.newurspi.greeninstitute.response.GreenInstituteLocalizedResponse;
import uz.urspi.newurspi.greeninstitute.response.GreenInstituteResponse;
import uz.urspi.newurspi.greeninstitute.service.GreenInstituteService;
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
public class GreenInstituteServiceImpl implements GreenInstituteService {
    private final GreenInstituteRepository repository;
    private final GreenInstituteMapper mapper;
    private final StorageService storageService;

    @Override
    @CacheEvict(value = CacheNames.GREEN_INSTITUTES, allEntries = true)
    @Auditable(action = AuditAction.CREATE, entity = "GreenInstitute")
    public GreenInstituteResponse create(GreenInstituteDTO dto) {
        return mapper.toResponse(repository.save(GreenInstitute.builder()
                .titleUz(dto.getTitleUz())
                .titleRu(dto.getTitleRu())
                .titleEn(dto.getTitleEn())
                .imageLinks(storeFiles(dto.getImages()))
                .status(Status.ACTIVE)
                .createdUsername(currentUsername())
                .build()));
    }

    @Override
    @Cacheable(value = CacheNames.GREEN_INSTITUTES, key = "#id")
    @Auditable(action = AuditAction.READ, entity = "GreenInstitute")
    public GreenInstituteResponse findById(Long id) {
        return mapper.toResponse(getOrThrow(id));
    }

    @Override
    @Cacheable(value = CacheNames.GREEN_INSTITUTES, key = "'all'")
    @Auditable(action = AuditAction.READ, entity = "GreenInstitute")
    public List<GreenInstituteResponse> fetchAll() {
        return mapper.toResponseList(repository.findAllByOrderByCreatedAtDesc());
    }

    @Override
    @Cacheable(value = CacheNames.GREEN_INSTITUTES, key = "'lang_' + #lang.name()")
    @Auditable(action = AuditAction.READ, entity = "GreenInstitute")
    public List<GreenInstituteLocalizedResponse> fetchAllByLang(Language lang) {
        return mapper.toLocalizedResponseList(repository.findAllByOrderByCreatedAtDesc(), lang);
    }

    @Override
    @CacheEvict(value = CacheNames.GREEN_INSTITUTES, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "GreenInstitute")
    public GreenInstituteResponse update(Long id, GreenInstituteDTO dto) {
        GreenInstitute entity = getOrThrow(id);
        entity.setTitleUz(dto.getTitleUz());
        entity.setTitleRu(dto.getTitleRu());
        entity.setTitleEn(dto.getTitleEn());
        List<String> newLinks = storeFiles(dto.getImages());
        if (!newLinks.isEmpty()) {
            entity.setImageLinks(newLinks);
        }
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @CacheEvict(value = CacheNames.GREEN_INSTITUTES, allEntries = true)
    @Auditable(action = AuditAction.DELETE, entity = "GreenInstitute")
    public void delete(Long id) {
        getOrThrow(id).setStatus(Status.DELETED);
    }

    @Override
    @CacheEvict(value = CacheNames.GREEN_INSTITUTES, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "GreenInstitute")
    public void activeOrDisabled(Long id) {
        GreenInstitute entity = getOrThrow(id);
        if (entity.getStatus() == Status.ACTIVE) {
            entity.setStatus(Status.DISABLED);
        } else if (entity.getStatus() == Status.DISABLED) {
            entity.setStatus(Status.ACTIVE);
        }
    }

    private GreenInstitute getOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Green institute not found with id = " + id));
    }

    private String currentUsername() {
        return Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
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
