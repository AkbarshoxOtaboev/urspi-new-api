package uz.urspi.newurspi.dormitory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.urspi.newurspi.dormitory.Dormitory;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Optional;

@Repository
public interface DormitoryRepository extends JpaRepository<Dormitory, Long> {
    List<Dormitory> findAllByOrderByCreatedAtDesc();

    Page<Dormitory> findAllByStatusOrderByCreatedAtDesc(Status status, Pageable pageable);

    Optional<Dormitory> findByIdAndStatus(Long id, Status status);
}
