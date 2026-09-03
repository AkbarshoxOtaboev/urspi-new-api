package uz.urspi.newurspi.announcement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.urspi.newurspi.announcement.Announcement;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findAllByOrderByPublishedAtDescCreatedAtDesc();

    Page<Announcement> findAllByStatusOrderByPublishedAtDescCreatedAtDesc(Status status, Pageable pageable);

    Optional<Announcement> findByIdAndStatus(Long id, Status status);
}
