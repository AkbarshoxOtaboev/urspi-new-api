package uz.urspi.newurspi.photogallery.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.urspi.newurspi.photogallery.PhotoGallery;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotoGalleryRepository extends JpaRepository<PhotoGallery, Long> {
    List<PhotoGallery> findAllByOrderByCreatedAtDesc();

    Page<PhotoGallery> findAllByStatusOrderByCreatedAtDesc(Status status, Pageable pageable);

    Optional<PhotoGallery> findByIdAndStatus(Long id, Status status);
}
