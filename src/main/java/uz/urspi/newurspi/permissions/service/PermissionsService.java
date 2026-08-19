package uz.urspi.newurspi.permissions.service;

import uz.urspi.newurspi.permissions.PermissionsResponse;

import java.util.List;

public interface PermissionsService {
    List<PermissionsResponse> fetchAllPermissions();
}
