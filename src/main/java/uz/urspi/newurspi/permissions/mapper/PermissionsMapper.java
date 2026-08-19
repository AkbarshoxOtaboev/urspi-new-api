package uz.urspi.newurspi.permissions.mapper;

import org.springframework.stereotype.Component;
import uz.urspi.newurspi.permissions.Permissions;
import uz.urspi.newurspi.permissions.PermissionsResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissionsMapper {

    public PermissionsResponse toResponse(Permissions permission) {
        if (permission == null) {
            return null;
        }
        return PermissionsResponse.builder()
                .id(permission.getId())
                .name(permission.getName())
                .resource(permission.getResource())
                .action(permission.getAction())
                .status(permission.getStatus())
                .createdAt(permission.getCreatedAt())
                .updatedAt(permission.getUpdatedAt())
                .build();
    }

    public List<PermissionsResponse> toResponseList(List<Permissions> permissions) {
        return permissions.stream()
                .map(this::toResponse)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
