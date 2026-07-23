package uz.urspi.newurspi.range.service;

import uz.urspi.newurspi.range.dto.RangeDTO;
import uz.urspi.newurspi.range.response.RangeResponse;

import java.util.List;

public interface RangeService {
    RangeResponse create(RangeDTO dto);
    RangeResponse findById(Long id);
    List<RangeResponse> fetchAllRanges();
    RangeResponse update(Long id, RangeDTO dto);
    void delete(Long id);
    void activeOrDisabledRange(Long id);
}
