package uz.urspi.newurspi.scientificarticle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.urspi.newurspi.scientificarticle.ScientificArticle;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScientificArticleRepository extends JpaRepository<ScientificArticle, Long> {
    List<ScientificArticle> findAllByOrderByPublicationYearDescIdDesc();

    List<ScientificArticle> findAllByTeacherIdOrderByPublicationYearDescIdDesc(Long teacherId);

    List<ScientificArticle> findAllByTeacherIdAndStatusOrderByPublicationYearDescIdDesc(Long teacherId, Status status);

    Optional<ScientificArticle> findByIdAndStatus(Long id, Status status);
}
