package uz.urspi.newurspi.scientificarticle.mapper;

import org.springframework.stereotype.Component;
import uz.urspi.newurspi.scientificarticle.ScientificArticle;
import uz.urspi.newurspi.scientificarticle.response.ScientificArticleResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScientificArticleMapper {

    public ScientificArticleResponse toResponse(ScientificArticle article) {
        if (article == null) {
            return null;
        }
        return ScientificArticleResponse.builder()
                .id(article.getId())
                .teacherId(article.getTeacher() != null ? article.getTeacher().getId() : null)
                .title(article.getTitle())
                .type(article.getType())
                .publicationYear(article.getPublicationYear())
                .journalName(article.getJournalName())
                .articleUrl(article.getArticleUrl())
                .fileLink(article.getFileLink())
                .status(article.getStatus())
                .createdAt(article.getCreatedAt())
                .updatedAt(article.getUpdatedAt())
                .build();
    }

    public List<ScientificArticleResponse> toResponseList(List<ScientificArticle> articles) {
        return articles.stream()
                .map(this::toResponse)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
