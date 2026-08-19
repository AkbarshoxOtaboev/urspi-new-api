package uz.urspi.newurspi.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.urspi.newurspi.employee.Employee;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByEmailIgnoreCase(String email);

    List<Employee> findAllByOrderBySortOrderAsc();

    List<Employee> findAllByCenterIdOrderBySortOrderAsc(Long centerId);
}
