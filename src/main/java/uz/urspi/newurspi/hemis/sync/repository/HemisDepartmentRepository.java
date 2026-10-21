package uz.urspi.newurspi.hemis.sync.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.urspi.newurspi.hemis.sync.HemisDepartment;

import java.util.Optional;

@Repository
public interface HemisDepartmentRepository extends JpaRepository<HemisDepartment, Long> {
    Optional<HemisDepartment> findByExternalId(Long externalId);
}
