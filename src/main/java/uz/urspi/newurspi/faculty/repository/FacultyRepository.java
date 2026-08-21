package uz.urspi.newurspi.faculty.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.urspi.newurspi.faculty.Faculty;
import uz.urspi.newurspi.utils.Status;

import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    boolean existsByCode(String code);

    Optional<Faculty> findByCode(String code);

    Page<Faculty> findAllByStatusOrderByIdAsc(Status status, Pageable pageable);

    Optional<Faculty> findByIdAndStatus(Long id, Status status);
}
