package uz.urspi.newurspi.leader.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.urspi.newurspi.leader.Leader;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaderRepository extends JpaRepository<Leader, Long> {
    List<Leader> findAllByOrderBySortOrderAsc();

    Page<Leader> findAllByStatusOrderBySortOrderAsc(Status status, Pageable pageable);

    Optional<Leader> findByIdAndStatus(Long id, Status status);
}
