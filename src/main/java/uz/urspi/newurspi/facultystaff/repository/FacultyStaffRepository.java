package uz.urspi.newurspi.facultystaff.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.urspi.newurspi.facultystaff.FacultyStaff;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacultyStaffRepository extends JpaRepository<FacultyStaff, Long> {
    boolean existsByEmailIgnoreCase(String email);

    List<FacultyStaff> findAllByOrderBySortOrderAsc();

    List<FacultyStaff> findAllByFacultyIdOrderBySortOrderAsc(Long facultyId);

    Page<FacultyStaff> findAllByStatusOrderBySortOrderAsc(Status status, Pageable pageable);

    Page<FacultyStaff> findAllByStatusAndFacultyIdOrderBySortOrderAsc(Status status, Long facultyId, Pageable pageable);

    Optional<FacultyStaff> findByIdAndStatus(Long id, Status status);
}
