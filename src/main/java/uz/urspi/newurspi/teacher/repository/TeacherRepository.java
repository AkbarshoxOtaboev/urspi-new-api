package uz.urspi.newurspi.teacher.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.urspi.newurspi.teacher.Teacher;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    boolean existsByEmailIgnoreCase(String email);

    List<Teacher> findAllByFacultyId(Long facultyId);

    List<Teacher> findAllByDepartmentId(Long departmentId);

    List<Teacher> findAllByPositionId(Long positionId);

    List<Teacher> findAllByAcademicDegreeId(Long academicDegreeId);

    List<Teacher> findAllByOrderBySortOrderAsc();

    List<Teacher> findAllByFacultyIdAndDepartmentIdOrderBySortOrderAsc(Long facultyId, Long departmentId);

    Page<Teacher> findAllByStatusOrderBySortOrderAsc(Status status, Pageable pageable);

    Page<Teacher> findAllByStatusAndFacultyIdOrderBySortOrderAsc(Status status, Long facultyId, Pageable pageable);

    Page<Teacher> findAllByStatusAndDepartmentIdOrderBySortOrderAsc(Status status, Long departmentId, Pageable pageable);

    Page<Teacher> findAllByStatusAndFacultyIdAndDepartmentIdOrderBySortOrderAsc(
            Status status, Long facultyId, Long departmentId, Pageable pageable);

    Optional<Teacher> findByIdAndStatus(Long id, Status status);
}
