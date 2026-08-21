package uz.urspi.newurspi.center.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.urspi.newurspi.center.Center;
import uz.urspi.newurspi.utils.Status;

import java.util.Optional;

public interface CenterRepository extends JpaRepository<Center, Long> {
    boolean existsByNameUzIgnoreCase(String nameUz);

    Page<Center> findAllByStatusOrderByIdAsc(Status status, Pageable pageable);

    Optional<Center> findByIdAndStatus(Long id, Status status);
}
