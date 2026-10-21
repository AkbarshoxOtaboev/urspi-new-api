package uz.urspi.newurspi.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.urspi.newurspi.menu.MenuSubCategory;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuSubCategoryRepository extends JpaRepository<MenuSubCategory, Long> {
    boolean existsByCategoryIdAndSlugIgnoreCase(Long categoryId, String slug);

    Optional<MenuSubCategory> findByIdAndStatus(Long id, Status status);

    List<MenuSubCategory> findAllByStatusOrderBySortOrderAscIdAsc(Status status);

    List<MenuSubCategory> findAllByCategoryIdAndStatusOrderBySortOrderAscIdAsc(Long categoryId, Status status);
}
