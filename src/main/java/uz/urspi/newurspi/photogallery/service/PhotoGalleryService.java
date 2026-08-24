package uz.urspi.newurspi.photogallery.service;

import uz.urspi.newurspi.photogallery.dto.PhotoGalleryDTO;
import uz.urspi.newurspi.photogallery.response.PhotoGalleryLocalizedResponse;
import uz.urspi.newurspi.photogallery.response.PhotoGalleryResponse;
import uz.urspi.newurspi.utils.Language;

import java.util.List;

public interface PhotoGalleryService {
    PhotoGalleryResponse create(PhotoGalleryDTO dto);
    PhotoGalleryResponse findById(Long id);
    List<PhotoGalleryResponse> fetchAll();
    List<PhotoGalleryLocalizedResponse> fetchAllByLang(Language lang);
    PhotoGalleryResponse update(Long id, PhotoGalleryDTO dto);
    void delete(Long id);
    void activeOrDisabled(Long id);
}
