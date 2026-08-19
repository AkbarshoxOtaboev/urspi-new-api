package uz.urspi.newurspi.leader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.urspi.newurspi.leader.Leader;

import java.util.List;

@Repository
public interface LeaderRepository extends JpaRepository<Leader, Long> {
    List<Leader> findAllByOrderBySortOrderAsc();
}
