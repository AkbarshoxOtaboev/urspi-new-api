package uz.urspi.newurspi.news.service;

import uz.urspi.newurspi.news.dto.NewsDTO;
import uz.urspi.newurspi.news.response.NewsLocalizedResponse;
import uz.urspi.newurspi.news.response.NewsResponse;
import uz.urspi.newurspi.utils.Language;

import java.util.List;

public interface NewsService {
    NewsResponse create(NewsDTO dto);
    NewsResponse findById(Long id);
    List<NewsResponse> fetchAllNews();
    List<NewsLocalizedResponse> fetchAllNewsByLang(Language lang);
    NewsResponse update(Long id, NewsDTO dto);
    void delete(Long id);
    void activeOrDisabledNews(Long id);
}
