package  uz.urspi.newurspi.permissions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import  uz.urspi.newurspi.utils.Action;
import  uz.urspi.newurspi.utils.Resource;

import java.util.List;

@Repository
public interface PermissionsRepository extends JpaRepository<Permissions, Long> {
    boolean existsByResourceAndAction(Resource resource, Action action);
    @Query("""
        select p
        from Role r
        join r.permissions p
        where r.id = :roleId
    """)
    List<Permissions> findAllByRoleId(Long roleId);
//    Optional<Permissions> findByName(String name);
}
