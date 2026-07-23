package uz.urspi.newurspi.study_year.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.urspi.newurspi.study_year.StudyYear;

@Repository
public interface StudyYearRepository extends JpaRepository<StudyYear, Long> {
    boolean existsByYear(String year);
}
