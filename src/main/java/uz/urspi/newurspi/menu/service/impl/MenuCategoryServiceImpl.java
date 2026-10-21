package uz.urspi.newurspi.menu.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.urspi.newurspi.audit.AuditAction;
import uz.urspi.newurspi.audit.Auditable;
import uz.urspi.newurspi.exceptions.BadRequestException;
import uz.urspi.newurspi.exceptions.ResourceNotFoundException;
import uz.urspi.newurspi.menu.Menu;
import uz.urspi.newurspi.menu.MenuCategory;
import uz.urspi.newurspi.menu.dto.MenuCategoryDTO;
import uz.urspi.newurspi.menu.mapper.MenuMapper;
import uz.urspi.newurspi.menu.repository.MenuCategoryRepository;
import uz.urspi.newurspi.menu.repository.MenuRepository;
import uz.urspi.newurspi.menu.response.MenuCategoryResponse;
import uz.urspi.newurspi.menu.service.MenuCategoryService;
import uz.urspi.newurspi.utils.CacheNames;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MenuCategoryServiceImpl implements MenuCategoryService {

    private final MenuCategoryRepository repository;
    private final MenuRepository menuRepository;
    private final MenuMapper mapper;

    @Override
    @CacheEvict(value = {CacheNames.MENU_CATEGORIES, CacheNames.MENUS}, allEntries = true)
    @Auditable(action = AuditAction.CREATE, entity = "MenuCategory")
    public MenuCategoryResponse create(MenuCategoryDTO dto) {
        Menu menu = menuRepository.findById(dto.getMenuId())
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found with id = " + dto.getMenuId()));
        if (repository.existsByMenuIdAndSlugIgnoreCase(menu.getId(), dto.getSlug())) {
            throw new BadRequestException("Category with this slug already exists in menu");
        }
        MenuCategory category = MenuCategory.builder()
                .menu(menu)
                .nameUz(dto.getNameUz())
                .nameRu(dto.getNameRu())
                .nameEn(dto.getNameEn())
                .slug(dto.getSlug().trim())
                .sortOrder(dto.getSortOrder() != null ? dto.getSortOrder() : 0)
                .status(Status.ACTIVE)
                .createdUsername(currentUsername())
                .build();
        return mapper.toResponse(repository.save(category));
    }

    @Override
    @Cacheable(value = CacheNames.MENU_CATEGORIES, key = "#id")
    @Auditable(action = AuditAction.READ, entity = "MenuCategory")
    public MenuCategoryResponse findById(Long id) {
        return mapper.toResponse(getOrThrow(id));
    }

    @Override
    @Cacheable(value = CacheNames.MENU_CATEGORIES, key = "'menu_' + (#menuId == null ? 'all' : #menuId)")
    @Auditable(action = AuditAction.READ, entity = "MenuCategory")
    public List<MenuCategoryResponse> fetchAll(Long menuId) {
        List<MenuCategory> list = menuId == null
                ? repository.findAllByStatusOrderBySortOrderAscIdAsc(Status.ACTIVE)
                : repository.findAllByMenuIdAndStatusOrderBySortOrderAscIdAsc(menuId, Status.ACTIVE);
        return mapper.toCategoryResponseList(list);
    }

    @Override
    @CacheEvict(value = {CacheNames.MENU_CATEGORIES, CacheNames.MENUS}, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "MenuCategory")
    public MenuCategoryResponse update(Long id, MenuCategoryDTO dto) {
        MenuCategory category = getOrThrow(id);
        Menu menu = menuRepository.findById(dto.getMenuId())
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found with id = " + dto.getMenuId()));
        if ((!category.getSlug().equalsIgnoreCase(dto.getSlug()) || !category.getMenu().getId().equals(menu.getId()))
                && repository.existsByMenuIdAndSlugIgnoreCase(menu.getId(), dto.getSlug())) {
            throw new BadRequestException("Category with this slug already exists in menu");
        }
        category.setMenu(menu);
        category.setNameUz(dto.getNameUz());
        category.setNameRu(dto.getNameRu());
        category.setNameEn(dto.getNameEn());
        category.setSlug(dto.getSlug().trim());
        category.setSortOrder(dto.getSortOrder() != null ? dto.getSortOrder() : category.getSortOrder());
        return mapper.toResponse(repository.save(category));
    }

    @Override
    @CacheEvict(value = {CacheNames.MENU_CATEGORIES, CacheNames.MENUS}, allEntries = true)
    @Auditable(action = AuditAction.DELETE, entity = "MenuCategory")
    public void delete(Long id) {
        getOrThrow(id).setStatus(Status.DELETED);
    }

    @Override
    @CacheEvict(value = {CacheNames.MENU_CATEGORIES, CacheNames.MENUS}, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "MenuCategory")
    public void changeStatus(Long id) {
        MenuCategory category = getOrThrow(id);
        category.setStatus(category.getStatus() == Status.ACTIVE ? Status.DISABLED : Status.ACTIVE);
    }

    private MenuCategory getOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu category not found with id = " + id));
    }

    private String currentUsername() {
        return Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
    }
}
