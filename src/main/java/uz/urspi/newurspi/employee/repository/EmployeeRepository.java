package uz.urspi.newurspi.employee.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.urspi.newurspi.employee.Employee;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByEmailIgnoreCase(String email);

    List<Employee> findAllByOrderBySortOrderAsc();

    List<Employee> findAllByCenterIdOrderBySortOrderAsc(Long centerId);

    Page<Employee> findAllByStatusOrderBySortOrderAsc(Status status, Pageable pageable);

    Page<Employee> findAllByStatusAndCenterIdOrderBySortOrderAsc(Status status, Long centerId, Pageable pageable);

    Optional<Employee> findByIdAndStatus(Long id, Status status);
}
