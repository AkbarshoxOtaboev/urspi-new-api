package uz.urspi.newurspi.center.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.urspi.newurspi.center.Center;

public interface CenterRepository extends JpaRepository<Center, Long> {
    boolean existsByNameUzIgnoreCase(String nameUz);
}
