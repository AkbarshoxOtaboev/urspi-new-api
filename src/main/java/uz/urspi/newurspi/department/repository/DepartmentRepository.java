package uz.urspi.newurspi.department.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.urspi.newurspi.department.Department;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findAllByFacultyId(Long facultyId);

    boolean existsByNameUzAndFacultyId(String nameUz, Long facultyId);

    Page<Department> findAllByStatusOrderByIdAsc(Status status, Pageable pageable);

    Page<Department> findAllByStatusAndFacultyIdOrderByIdAsc(Status status, Long facultyId, Pageable pageable);

    Optional<Department> findByIdAndStatus(Long id, Status status);
}
