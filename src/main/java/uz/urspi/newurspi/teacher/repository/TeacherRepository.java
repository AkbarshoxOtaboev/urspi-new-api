package uz.urspi.newurspi.teacher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.urspi.newurspi.teacher.Teacher;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    boolean existsByEmailIgnoreCase(String email);
    List<Teacher> findAllByFacultyId(Long facultyId);
    List<Teacher> findAllByDepartmentId(Long departmentId);
    List<Teacher> findAllByPositionId(Long positionId);
    List<Teacher> findAllByAcademicDegreeId(Long academicDegreeId);
    List<Teacher> findAllByOrderBySortOrderAsc();
    List<Teacher> findAllByFacultyIdAndDepartmentIdOrderBySortOrderAsc(Long facultyId, Long departmentId);
}
