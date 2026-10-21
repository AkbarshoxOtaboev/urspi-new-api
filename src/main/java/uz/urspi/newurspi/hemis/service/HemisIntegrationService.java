package uz.urspi.newurspi.hemis.service;

import uz.urspi.newurspi.hemis.response.HemisConnectionTestResponse;
import uz.urspi.newurspi.hemis.response.HemisSyncResultResponse;
import uz.urspi.newurspi.hemis.sync.HemisSyncResource;

public interface HemisIntegrationService {
    HemisConnectionTestResponse testConnection();

    HemisSyncResultResponse sync(HemisSyncResource resource);
}
