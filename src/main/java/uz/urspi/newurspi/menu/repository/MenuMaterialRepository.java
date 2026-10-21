package uz.urspi.newurspi.menu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.urspi.newurspi.menu.MenuMaterial;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuMaterialRepository extends JpaRepository<MenuMaterial, Long> {
    Optional<MenuMaterial> findByIdAndStatus(Long id, Status status);

    List<MenuMaterial> findAllByInfoIdAndStatusOrderByCreatedAtDesc(Long infoId, Status status);

    List<MenuMaterial> findAllByInfoIdAndYearAndStatusOrderByCreatedAtDesc(Long infoId, Integer year, Status status);

    Page<MenuMaterial> findAllByInfoIdAndStatusOrderByCreatedAtDesc(Long infoId, Status status, Pageable pageable);

    Page<MenuMaterial> findAllByInfoIdAndYearAndStatusOrderByCreatedAtDesc(
            Long infoId, Integer year, Status status, Pageable pageable
    );

    @Query("""
            select distinct m.year from MenuMaterial m
            where m.info.id = :infoId and m.status = :status and m.year is not null
            order by m.year desc
            """)
    List<Integer> findDistinctYearsByInfoId(@Param("infoId") Long infoId, @Param("status") Status status);
}
