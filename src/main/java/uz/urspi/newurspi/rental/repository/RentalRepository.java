package uz.urspi.newurspi.rental.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.urspi.newurspi.rental.Rental;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findAllByOrderByCreatedAtDesc();

    Page<Rental> findAllByStatusOrderByCreatedAtDesc(Status status, Pageable pageable);

    Optional<Rental> findByIdAndStatus(Long id, Status status);
}
