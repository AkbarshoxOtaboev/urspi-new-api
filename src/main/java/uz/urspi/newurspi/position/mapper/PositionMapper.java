package uz.urspi.newurspi.position.mapper;

import org.springframework.stereotype.Component;
import uz.urspi.newurspi.position.Position;
import uz.urspi.newurspi.position.response.PositionResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PositionMapper {

    public PositionResponse toResponse(Position position) {
        if (position == null) {
            return null;
        }
        return PositionResponse.builder()
                .id(position.getId())
                .name(position.getName())
                .description(position.getDescription())
                .status(position.getStatus())
                .createdAt(position.getCreatedAt())
                .updatedAt(position.getUpdatedAt())
                .build();
    }

    public List<PositionResponse> toResponseList(List<Position> positions) {
        return positions.stream()
                .map(this::toResponse)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
