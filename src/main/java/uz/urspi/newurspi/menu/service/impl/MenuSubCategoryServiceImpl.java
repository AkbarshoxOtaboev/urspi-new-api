package uz.urspi.newurspi.menu.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import uz.urspi.newurspi.audit.AuditAction;
import uz.urspi.newurspi.audit.Auditable;
import uz.urspi.newurspi.exceptions.BadRequestException;
import uz.urspi.newurspi.exceptions.ResourceNotFoundException;
import uz.urspi.newurspi.menu.MenuCategory;
import uz.urspi.newurspi.menu.MenuContentType;
import uz.urspi.newurspi.menu.MenuSubCategory;
import uz.urspi.newurspi.menu.dto.MenuSubCategoryDTO;
import uz.urspi.newurspi.menu.mapper.MenuMapper;
import uz.urspi.newurspi.menu.repository.MenuCategoryRepository;
import uz.urspi.newurspi.menu.repository.MenuSubCategoryRepository;
import uz.urspi.newurspi.menu.response.MenuSubCategoryResponse;
import uz.urspi.newurspi.menu.service.MenuSubCategoryService;
import uz.urspi.newurspi.utils.CacheNames;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MenuSubCategoryServiceImpl implements MenuSubCategoryService {

    private final MenuSubCategoryRepository repository;
    private final MenuCategoryRepository categoryRepository;
    private final MenuMapper mapper;

    @Override
    @CacheEvict(value = CacheNames.MENU_SUBCATEGORIES, allEntries = true)
    @Auditable(action = AuditAction.CREATE, entity = "MenuSubCategory")
    public MenuSubCategoryResponse create(MenuSubCategoryDTO dto) {
        MenuCategory category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Menu category not found with id = " + dto.getCategoryId()));
        validateContentType(dto);
        if (repository.existsByCategoryIdAndSlugIgnoreCase(category.getId(), dto.getSlug())) {
            throw new BadRequestException("SubCategory with this slug already exists in category");
        }
        MenuSubCategory sub = MenuSubCategory.builder()
                .category(category)
                .nameUz(dto.getNameUz())
                .nameRu(dto.getNameRu())
                .nameEn(dto.getNameEn())
                .slug(dto.getSlug().trim())
                .contentType(dto.getContentType())
                .tableColumns(dto.getTableColumns())
                .linkPath(dto.getLinkPath())
                .linkTargetType(dto.getLinkTargetType())
                .sortOrder(dto.getSortOrder() != null ? dto.getSortOrder() : 0)
                .status(Status.ACTIVE)
                .createdUsername(currentUsername())
                .build();
        return mapper.toResponse(repository.save(sub));
    }

    @Override
    @Cacheable(value = CacheNames.MENU_SUBCATEGORIES, key = "#id")
    @Auditable(action = AuditAction.READ, entity = "MenuSubCategory")
    public MenuSubCategoryResponse findById(Long id) {
        return mapper.toResponse(getOrThrow(id));
    }

    @Override
    @Cacheable(value = CacheNames.MENU_SUBCATEGORIES, key = "'cat_' + (#categoryId == null ? 'all' : #categoryId)")
    @Auditable(action = AuditAction.READ, entity = "MenuSubCategory")
    public List<MenuSubCategoryResponse> fetchAll(Long categoryId) {
        List<MenuSubCategory> list = categoryId == null
                ? repository.findAllByStatusOrderBySortOrderAscIdAsc(Status.ACTIVE)
                : repository.findAllByCategoryIdAndStatusOrderBySortOrderAscIdAsc(categoryId, Status.ACTIVE);
        return mapper.toSubCategoryResponseList(list);
    }

    @Override
    @CacheEvict(value = CacheNames.MENU_SUBCATEGORIES, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "MenuSubCategory")
    public MenuSubCategoryResponse update(Long id, MenuSubCategoryDTO dto) {
        MenuSubCategory sub = getOrThrow(id);
        MenuCategory category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Menu category not found with id = " + dto.getCategoryId()));
        validateContentType(dto);
        if ((!sub.getSlug().equalsIgnoreCase(dto.getSlug()) || !sub.getCategory().getId().equals(category.getId()))
                && repository.existsByCategoryIdAndSlugIgnoreCase(category.getId(), dto.getSlug())) {
            throw new BadRequestException("SubCategory with this slug already exists in category");
        }
        sub.setCategory(category);
        sub.setNameUz(dto.getNameUz());
        sub.setNameRu(dto.getNameRu());
        sub.setNameEn(dto.getNameEn());
        sub.setSlug(dto.getSlug().trim());
        sub.setContentType(dto.getContentType());
        sub.setTableColumns(dto.getTableColumns());
        sub.setLinkPath(dto.getLinkPath());
        sub.setLinkTargetType(dto.getLinkTargetType());
        sub.setSortOrder(dto.getSortOrder() != null ? dto.getSortOrder() : sub.getSortOrder());
        return mapper.toResponse(repository.save(sub));
    }

    @Override
    @CacheEvict(value = CacheNames.MENU_SUBCATEGORIES, allEntries = true)
    @Auditable(action = AuditAction.DELETE, entity = "MenuSubCategory")
    public void delete(Long id) {
        getOrThrow(id).setStatus(Status.DELETED);
    }

    @Override
    @CacheEvict(value = CacheNames.MENU_SUBCATEGORIES, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "MenuSubCategory")
    public void changeStatus(Long id) {
        MenuSubCategory sub = getOrThrow(id);
        sub.setStatus(sub.getStatus() == Status.ACTIVE ? Status.DISABLED : Status.ACTIVE);
    }

    private void validateContentType(MenuSubCategoryDTO dto) {
        if (dto.getContentType() == MenuContentType.LINK && !StringUtils.hasText(dto.getLinkPath())) {
            throw new BadRequestException("linkPath is required when contentType is LINK");
        }
    }

    private MenuSubCategory getOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu subcategory not found with id = " + id));
    }

    private String currentUsername() {
        return Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
    }
}
