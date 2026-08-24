package uz.urspi.newurspi.greeninstitute.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.urspi.newurspi.greeninstitute.GreenInstitute;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Optional;

@Repository
public interface GreenInstituteRepository extends JpaRepository<GreenInstitute, Long> {
    List<GreenInstitute> findAllByOrderByCreatedAtDesc();

    Page<GreenInstitute> findAllByStatusOrderByCreatedAtDesc(Status status, Pageable pageable);

    Optional<GreenInstitute> findByIdAndStatus(Long id, Status status);
}
