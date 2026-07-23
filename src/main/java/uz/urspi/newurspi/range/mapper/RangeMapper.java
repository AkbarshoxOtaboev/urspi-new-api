package uz.urspi.newurspi.range.mapper;

import org.springframework.stereotype.Component;
import uz.urspi.newurspi.range.Range;
import uz.urspi.newurspi.range.response.RangeResponse;

import java.util.List;

@Component
public class RangeMapper {

    public RangeResponse toResponse(Range range) {
        if (range == null) {
            return null;
        }
        return RangeResponse.builder()
                .id(range.getId())
                .name(range.getName())
                .status(range.getStatus())
                .createdAt(range.getCreatedAt())
                .updatedAt(range.getUpdatedAt())
                .build();
    }

    public List<RangeResponse> toResponseList(List<Range> ranges) {
        return ranges.stream().map(this::toResponse).toList();
    }
}
