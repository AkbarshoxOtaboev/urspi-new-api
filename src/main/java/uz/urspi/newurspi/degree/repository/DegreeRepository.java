package uz.urspi.newurspi.degree.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.urspi.newurspi.degree.Degree;

@Repository
public interface DegreeRepository extends JpaRepository<Degree, Long> {
    boolean existsByName(String name);
}
