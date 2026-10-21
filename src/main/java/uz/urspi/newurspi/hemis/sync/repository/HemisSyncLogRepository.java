package uz.urspi.newurspi.hemis.sync.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.urspi.newurspi.hemis.sync.HemisSyncLog;

@Repository
public interface HemisSyncLogRepository extends JpaRepository<HemisSyncLog, Long> {
}
