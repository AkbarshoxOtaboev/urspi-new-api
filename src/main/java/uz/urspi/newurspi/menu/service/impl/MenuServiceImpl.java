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
import uz.urspi.newurspi.menu.dto.MenuDTO;
import uz.urspi.newurspi.menu.mapper.MenuMapper;
import uz.urspi.newurspi.menu.repository.MenuRepository;
import uz.urspi.newurspi.menu.response.MenuResponse;
import uz.urspi.newurspi.menu.service.MenuService;
import uz.urspi.newurspi.utils.CacheNames;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MenuServiceImpl implements MenuService {

    private final MenuRepository repository;
    private final MenuMapper mapper;

    @Override
    @CacheEvict(value = CacheNames.MENUS, allEntries = true)
    @Auditable(action = AuditAction.CREATE, entity = "Menu")
    public MenuResponse create(MenuDTO dto) {
        if (repository.existsBySlugIgnoreCase(dto.getSlug())) {
            throw new BadRequestException("Menu with this slug already exists");
        }
        Menu menu = Menu.builder()
                .nameUz(dto.getNameUz())
                .nameRu(dto.getNameRu())
                .nameEn(dto.getNameEn())
                .slug(dto.getSlug().trim())
                .sortOrder(dto.getSortOrder() != null ? dto.getSortOrder() : 0)
                .status(Status.ACTIVE)
                .createdUsername(currentUsername())
                .build();
        return mapper.toResponse(repository.save(menu));
    }

    @Override
    @Cacheable(value = CacheNames.MENUS, key = "#id")
    @Auditable(action = AuditAction.READ, entity = "Menu")
    public MenuResponse findById(Long id) {
        return mapper.toResponse(getOrThrow(id));
    }

    @Override
    @Cacheable(value = CacheNames.MENUS, key = "'all'")
    @Auditable(action = AuditAction.READ, entity = "Menu")
    public List<MenuResponse> fetchAll() {
        return mapper.toMenuResponseList(repository.findAllByStatusOrderBySortOrderAscIdAsc(Status.ACTIVE));
    }

    @Override
    @CacheEvict(value = CacheNames.MENUS, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "Menu")
    public MenuResponse update(Long id, MenuDTO dto) {
        Menu menu = getOrThrow(id);
        if (!menu.getSlug().equalsIgnoreCase(dto.getSlug()) && repository.existsBySlugIgnoreCase(dto.getSlug())) {
            throw new BadRequestException("Menu with this slug already exists");
        }
        menu.setNameUz(dto.getNameUz());
        menu.setNameRu(dto.getNameRu());
        menu.setNameEn(dto.getNameEn());
        menu.setSlug(dto.getSlug().trim());
        menu.setSortOrder(dto.getSortOrder() != null ? dto.getSortOrder() : menu.getSortOrder());
        return mapper.toResponse(repository.save(menu));
    }

    @Override
    @CacheEvict(value = CacheNames.MENUS, allEntries = true)
    @Auditable(action = AuditAction.DELETE, entity = "Menu")
    public void delete(Long id) {
        getOrThrow(id).setStatus(Status.DELETED);
    }

    @Override
    @CacheEvict(value = CacheNames.MENUS, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "Menu")
    public void changeStatus(Long id) {
        Menu menu = getOrThrow(id);
        menu.setStatus(menu.getStatus() == Status.ACTIVE ? Status.DISABLED : Status.ACTIVE);
    }

    private Menu getOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found with id = " + id));
    }

    private String currentUsername() {
        return Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
    }
}
