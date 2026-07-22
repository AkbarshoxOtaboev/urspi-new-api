package uz.urspi.newurspi.department.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.urspi.newurspi.department.Department;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findAllByFacultyId(Long facultyId);
    boolean existsByNameAndFacultyId(String name, Long facultyId);
}
