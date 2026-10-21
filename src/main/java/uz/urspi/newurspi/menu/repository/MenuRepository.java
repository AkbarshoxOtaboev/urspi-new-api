package uz.urspi.newurspi.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.urspi.newurspi.menu.Menu;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    boolean existsBySlugIgnoreCase(String slug);

    Optional<Menu> findByIdAndStatus(Long id, Status status);

    List<Menu> findAllByStatusOrderBySortOrderAscIdAsc(Status status);
}
