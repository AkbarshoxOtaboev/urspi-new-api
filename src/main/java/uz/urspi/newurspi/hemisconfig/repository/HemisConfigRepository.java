package uz.urspi.newurspi.hemisconfig.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.urspi.newurspi.hemisconfig.HemisConfig;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Optional;

@Repository
public interface HemisConfigRepository extends JpaRepository<HemisConfig, Long> {
    boolean existsByNameIgnoreCase(String name);

    Optional<HemisConfig> findFirstByActiveTrueAndStatus(Status status);

    List<HemisConfig> findAllByActiveTrue();
}
