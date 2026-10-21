package uz.urspi.newurspi.menu.service.impl;

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
import uz.urspi.newurspi.menu.MenuInfo;
import uz.urspi.newurspi.menu.MenuMaterial;
import uz.urspi.newurspi.menu.dto.MenuMaterialDTO;
import uz.urspi.newurspi.menu.mapper.MenuMapper;
import uz.urspi.newurspi.menu.repository.MenuInfoRepository;
import uz.urspi.newurspi.menu.repository.MenuMaterialRepository;
import uz.urspi.newurspi.menu.response.MenuMaterialResponse;
import uz.urspi.newurspi.menu.service.MenuMaterialService;
import uz.urspi.newurspi.storage.StorageService;
import uz.urspi.newurspi.utils.CacheNames;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MenuMaterialServiceImpl implements MenuMaterialService {

    private final MenuMaterialRepository repository;
    private final MenuInfoRepository infoRepository;
    private final MenuMapper mapper;
    private final StorageService storageService;

    @Override
    @CacheEvict(value = CacheNames.MENU_MATERIALS, allEntries = true)
    @Auditable(action = AuditAction.CREATE, entity = "MenuMaterial")
    public MenuMaterialResponse create(MenuMaterialDTO dto) {
        MenuInfo info = infoRepository.findById(dto.getInfoId())
                .orElseThrow(() -> new ResourceNotFoundException("Menu info not found with id = " + dto.getInfoId()));
        MenuMaterial material = MenuMaterial.builder()
                .info(info)
                .year(dto.getYear())
                .titleUz(dto.getTitleUz())
                .titleRu(dto.getTitleRu())
                .titleEn(dto.getTitleEn())
                .fileLink(store(dto.getFile()))
                .coverLink(store(dto.getCover()))
                .publishedAt(dto.getPublishedAt())
                .status(Status.ACTIVE)
                .createdUsername(currentUsername())
                .build();
        return mapper.toResponse(repository.save(material));
    }

    @Override
    @Cacheable(value = CacheNames.MENU_MATERIALS, key = "#id")
    @Auditable(action = AuditAction.READ, entity = "MenuMaterial")
    public MenuMaterialResponse findById(Long id) {
        return mapper.toResponse(getOrThrow(id));
    }

    @Override
    @Cacheable(value = CacheNames.MENU_MATERIALS, key = "'info_' + #infoId + '_year_' + (#year == null ? 'all' : #year)")
    @Auditable(action = AuditAction.READ, entity = "MenuMaterial")
    public List<MenuMaterialResponse> fetchAll(Long infoId, Integer year) {
        if (infoId == null) {
            return mapper.toMaterialResponseList(repository.findAll());
        }
        List<MenuMaterial> list = year == null
                ? repository.findAllByInfoIdAndStatusOrderByCreatedAtDesc(infoId, Status.ACTIVE)
                : repository.findAllByInfoIdAndYearAndStatusOrderByCreatedAtDesc(infoId, year, Status.ACTIVE);
        return mapper.toMaterialResponseList(list);
    }

    @Override
    @CacheEvict(value = CacheNames.MENU_MATERIALS, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "MenuMaterial")
    public MenuMaterialResponse update(Long id, MenuMaterialDTO dto) {
        MenuMaterial material = getOrThrow(id);
        MenuInfo info = infoRepository.findById(dto.getInfoId())
                .orElseThrow(() -> new ResourceNotFoundException("Menu info not found with id = " + dto.getInfoId()));
        material.setInfo(info);
        material.setYear(dto.getYear());
        material.setTitleUz(dto.getTitleUz());
        material.setTitleRu(dto.getTitleRu());
        material.setTitleEn(dto.getTitleEn());
        material.setPublishedAt(dto.getPublishedAt());
        String file = store(dto.getFile());
        if (file != null) {
            material.setFileLink(file);
        }
        String cover = store(dto.getCover());
        if (cover != null) {
            material.setCoverLink(cover);
        }
        return mapper.toResponse(repository.save(material));
    }

    @Override
    @CacheEvict(value = CacheNames.MENU_MATERIALS, allEntries = true)
    @Auditable(action = AuditAction.DELETE, entity = "MenuMaterial")
    public void delete(Long id) {
        getOrThrow(id).setStatus(Status.DELETED);
    }

    @Override
    @CacheEvict(value = CacheNames.MENU_MATERIALS, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "MenuMaterial")
    public void changeStatus(Long id) {
        MenuMaterial material = getOrThrow(id);
        material.setStatus(material.getStatus() == Status.ACTIVE ? Status.DISABLED : Status.ACTIVE);
    }

    private String store(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        return storageService.uploadFile(file);
    }

    private MenuMaterial getOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu material not found with id = " + id));
    }

    private String currentUsername() {
        return Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
    }
}
