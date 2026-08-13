package uz.urspi.newurspi.position.service;

import uz.urspi.newurspi.position.dto.PositionDTO;
import uz.urspi.newurspi.position.response.PositionResponse;

import java.util.List;

public interface PositionService {
    PositionResponse create(PositionDTO dto);
    PositionResponse findById(Long id);
    List<PositionResponse> fetchAllPositions();
    PositionResponse update(Long id, PositionDTO dto);
    void delete(Long id);
    void activeOrDisabledPosition(Long id);
}
