package uz.urspi.newurspi.hemis.sync.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.urspi.newurspi.hemis.sync.HemisEmployee;

import java.util.Optional;

@Repository
public interface HemisEmployeeRepository extends JpaRepository<HemisEmployee, Long> {
    Optional<HemisEmployee> findByExternalId(Long externalId);
}
