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
import uz.urspi.newurspi.exceptions.BadRequestException;
import uz.urspi.newurspi.exceptions.ResourceNotFoundException;
import uz.urspi.newurspi.menu.MenuContentType;
import uz.urspi.newurspi.menu.MenuInfo;
import uz.urspi.newurspi.menu.MenuSubCategory;
import uz.urspi.newurspi.menu.dto.MenuInfoDTO;
import uz.urspi.newurspi.menu.mapper.MenuMapper;
import uz.urspi.newurspi.menu.repository.MenuInfoRepository;
import uz.urspi.newurspi.menu.repository.MenuSubCategoryRepository;
import uz.urspi.newurspi.menu.response.MenuInfoResponse;
import uz.urspi.newurspi.menu.service.MenuInfoService;
import uz.urspi.newurspi.storage.StorageService;
import uz.urspi.newurspi.utils.CacheNames;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MenuInfoServiceImpl implements MenuInfoService {

    private final MenuInfoRepository repository;
    private final MenuSubCategoryRepository subCategoryRepository;
    private final MenuMapper mapper;
    private final StorageService storageService;

    @Override
    @CacheEvict(value = CacheNames.MENU_INFOS, allEntries = true)
    @Auditable(action = AuditAction.CREATE, entity = "MenuInfo")
    public MenuInfoResponse create(MenuInfoDTO dto) {
        MenuSubCategory sub = subCategoryRepository.findById(dto.getSubCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Menu subcategory not found with id = " + dto.getSubCategoryId()));
        if (sub.getContentType() == MenuContentType.LINK) {
            throw new BadRequestException("Cannot create Info under LINK subcategory");
        }
        MenuInfo info = MenuInfo.builder()
                .subCategory(sub)
                .titleUz(dto.getTitleUz())
                .titleRu(dto.getTitleRu())
                .titleEn(dto.getTitleEn())
                .contentUz(dto.getContentUz())
                .contentRu(dto.getContentRu())
                .contentEn(dto.getContentEn())
                .photoLink(store(dto.getPhoto()))
                .publishedAt(dto.getPublishedAt())
                .sortOrder(dto.getSortOrder() != null ? dto.getSortOrder() : 0)
                .status(Status.ACTIVE)
                .createdUsername(currentUsername())
                .build();
        return mapper.toResponse(repository.save(info));
    }

    @Override
    @Cacheable(value = CacheNames.MENU_INFOS, key = "#id")
    @Auditable(action = AuditAction.READ, entity = "MenuInfo")
    public MenuInfoResponse findById(Long id) {
        return mapper.toResponse(getOrThrow(id));
    }

    @Override
    @Cacheable(value = CacheNames.MENU_INFOS, key = "'sub_' + (#subCategoryId == null ? 'all' : #subCategoryId)")
    @Auditable(action = AuditAction.READ, entity = "MenuInfo")
    public List<MenuInfoResponse> fetchAll(Long subCategoryId) {
        List<MenuInfo> list = subCategoryId == null
                ? repository.findAllByStatusOrderByCreatedAtDesc(Status.ACTIVE)
                : repository.findAllBySubCategoryIdAndStatusOrderByCreatedAtDesc(subCategoryId, Status.ACTIVE);
        return mapper.toInfoResponseList(list);
    }

    @Override
    @CacheEvict(value = CacheNames.MENU_INFOS, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "MenuInfo")
    public MenuInfoResponse update(Long id, MenuInfoDTO dto) {
        MenuInfo info = getOrThrow(id);
        MenuSubCategory sub = subCategoryRepository.findById(dto.getSubCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Menu subcategory not found with id = " + dto.getSubCategoryId()));
        if (sub.getContentType() == MenuContentType.LINK) {
            throw new BadRequestException("Cannot attach Info to LINK subcategory");
        }
        info.setSubCategory(sub);
        info.setTitleUz(dto.getTitleUz());
        info.setTitleRu(dto.getTitleRu());
        info.setTitleEn(dto.getTitleEn());
        info.setContentUz(dto.getContentUz());
        info.setContentRu(dto.getContentRu());
        info.setContentEn(dto.getContentEn());
        info.setPublishedAt(dto.getPublishedAt());
        info.setSortOrder(dto.getSortOrder() != null ? dto.getSortOrder() : info.getSortOrder());
        String photo = store(dto.getPhoto());
        if (photo != null) {
            info.setPhotoLink(photo);
        }
        return mapper.toResponse(repository.save(info));
    }

    @Override
    @CacheEvict(value = CacheNames.MENU_INFOS, allEntries = true)
    @Auditable(action = AuditAction.DELETE, entity = "MenuInfo")
    public void delete(Long id) {
        getOrThrow(id).setStatus(Status.DELETED);
    }

    @Override
    @CacheEvict(value = CacheNames.MENU_INFOS, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "MenuInfo")
    public void changeStatus(Long id) {
        MenuInfo info = getOrThrow(id);
        info.setStatus(info.getStatus() == Status.ACTIVE ? Status.DISABLED : Status.ACTIVE);
    }

    private String store(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        return storageService.uploadFile(file);
    }

    private MenuInfo getOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu info not found with id = " + id));
    }

    private String currentUsername() {
        return Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
    }
}
