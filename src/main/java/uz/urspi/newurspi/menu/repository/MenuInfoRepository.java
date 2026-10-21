package uz.urspi.newurspi.menu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.urspi.newurspi.menu.MenuInfo;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuInfoRepository extends JpaRepository<MenuInfo, Long> {
    Optional<MenuInfo> findByIdAndStatus(Long id, Status status);

    List<MenuInfo> findAllByStatusOrderByCreatedAtDesc(Status status);

    List<MenuInfo> findAllBySubCategoryIdAndStatusOrderByCreatedAtDesc(Long subCategoryId, Status status);

    Page<MenuInfo> findAllBySubCategoryIdAndStatusOrderByCreatedAtDesc(Long subCategoryId, Status status, Pageable pageable);
}
