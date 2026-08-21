package uz.urspi.newurspi.news.mapper;

import org.springframework.stereotype.Component;
import uz.urspi.newurspi.news.News;
import uz.urspi.newurspi.news.response.NewsLocalizedResponse;
import uz.urspi.newurspi.news.response.NewsResponse;
import uz.urspi.newurspi.utils.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NewsMapper {

    public NewsResponse toResponse(News news) {
        if (news == null) {
            return null;
        }
        return NewsResponse.builder()
                .id(news.getId())
                .titleUz(news.getTitleUz())
                .titleRu(news.getTitleRu())
                .titleEn(news.getTitleEn())
                .contentUz(news.getContentUz())
                .contentRu(news.getContentRu())
                .contentEn(news.getContentEn())
                .author(news.getAuthor())
                .mainImageLink(news.getMainImageLink())
                .imageLinks(copyImageLinks(news))
                .status(news.getStatus())
                .createdAt(news.getCreatedAt())
                .updatedAt(news.getUpdatedAt())
                .build();
    }

    public NewsLocalizedResponse toLocalizedResponse(News news, Language lang) {
        if (news == null) {
            return null;
        }
        return NewsLocalizedResponse.builder()
                .id(news.getId())
                .title(getLocalizedTitle(news, lang))
                .content(getLocalizedContent(news, lang))
                .author(news.getAuthor())
                .mainImageLink(news.getMainImageLink())
                .imageLinks(copyImageLinks(news))
                .status(news.getStatus())
                .createdAt(news.getCreatedAt())
                .updatedAt(news.getUpdatedAt())
                .build();
    }

    private List<String> copyImageLinks(News news) {
        if (news.getImageLinks() == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(news.getImageLinks());
    }

    public List<NewsResponse> toResponseList(List<News> newsList) {
        return newsList.stream()
                .map(this::toResponse)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<NewsLocalizedResponse> toLocalizedResponseList(List<News> newsList, Language lang) {
        return newsList.stream()
                .map(n -> toLocalizedResponse(n, lang))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private String getLocalizedTitle(News news, Language lang) {
        return switch (lang) {
            case ru -> news.getTitleRu();
            case en -> news.getTitleEn();
            default -> news.getTitleUz();
        };
    }

    private String getLocalizedContent(News news, Language lang) {
        return switch (lang) {
            case ru -> news.getContentRu();
            case en -> news.getContentEn();
            default -> news.getContentUz();
        };
    }
}
