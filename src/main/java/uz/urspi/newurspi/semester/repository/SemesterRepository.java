package uz.urspi.newurspi.semester.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.urspi.newurspi.semester.Semester;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long> {
    boolean existsByName(String name);
}
