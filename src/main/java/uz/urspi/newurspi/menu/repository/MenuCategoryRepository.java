package uz.urspi.newurspi.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.urspi.newurspi.menu.MenuCategory;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {
    boolean existsByMenuIdAndSlugIgnoreCase(Long menuId, String slug);

    Optional<MenuCategory> findByIdAndStatus(Long id, Status status);

    List<MenuCategory> findAllByStatusOrderBySortOrderAscIdAsc(Status status);

    List<MenuCategory> findAllByMenuIdAndStatusOrderBySortOrderAscIdAsc(Long menuId, Status status);
}
