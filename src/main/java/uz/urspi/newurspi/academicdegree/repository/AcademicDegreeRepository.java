package uz.urspi.newurspi.academicdegree.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.urspi.newurspi.academicdegree.AcademicDegree;

@Repository
public interface AcademicDegreeRepository extends JpaRepository<AcademicDegree, Long> {
    boolean existsByNameIgnoreCase(String name);
}
