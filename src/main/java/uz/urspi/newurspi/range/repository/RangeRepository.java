package uz.urspi.newurspi.range.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.urspi.newurspi.range.Range;

@Repository
public interface RangeRepository extends JpaRepository<Range, Long> {
    boolean existsByName(String name);
}
