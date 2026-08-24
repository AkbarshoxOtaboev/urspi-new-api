package uz.urspi.newurspi.rental.service;

import uz.urspi.newurspi.rental.dto.RentalDTO;
import uz.urspi.newurspi.rental.response.RentalLocalizedResponse;
import uz.urspi.newurspi.rental.response.RentalResponse;
import uz.urspi.newurspi.utils.Language;

import java.util.List;

public interface RentalService {
    RentalResponse create(RentalDTO dto);
    RentalResponse findById(Long id);
    List<RentalResponse> fetchAll();
    List<RentalLocalizedResponse> fetchAllByLang(Language lang);
    RentalResponse update(Long id, RentalDTO dto);
    void delete(Long id);
    void activeOrDisabled(Long id);
}
