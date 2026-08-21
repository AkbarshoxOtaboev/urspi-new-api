package uz.urspi.newurspi.news.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.urspi.newurspi.news.News;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findAllByOrderByCreatedAtDesc();

    Page<News> findAllByStatusOrderByCreatedAtDesc(Status status, Pageable pageable);

    Optional<News> findByIdAndStatus(Long id, Status status);
}
