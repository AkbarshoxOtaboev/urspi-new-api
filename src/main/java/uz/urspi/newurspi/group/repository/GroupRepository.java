package uz.urspi.newurspi.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.urspi.newurspi.group.Group;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findAllByFacultyId(Long facultyId);
    List<Group> findAllByDepartmentId(Long departmentId);
    boolean existsByNameAndFacultyId(String name, Long facultyId);
}
