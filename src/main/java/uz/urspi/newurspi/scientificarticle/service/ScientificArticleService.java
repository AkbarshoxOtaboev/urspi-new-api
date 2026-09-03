package uz.urspi.newurspi.scientificarticle.service;

import uz.urspi.newurspi.scientificarticle.dto.ScientificArticleDTO;
import uz.urspi.newurspi.scientificarticle.response.ScientificArticleResponse;

import java.util.List;

public interface ScientificArticleService {
    ScientificArticleResponse create(ScientificArticleDTO dto);

    ScientificArticleResponse findById(Long id);

    List<ScientificArticleResponse> fetchAll();

    List<ScientificArticleResponse> fetchByTeacherId(Long teacherId);

    ScientificArticleResponse update(Long id, ScientificArticleDTO dto);

    void delete(Long id);

    void activeOrDisabled(Long id);
}
