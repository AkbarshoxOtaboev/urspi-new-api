package uz.urspi.newurspi.hemisconfig.service;

import uz.urspi.newurspi.hemisconfig.HemisConfig;
import uz.urspi.newurspi.hemisconfig.dto.HemisConfigDTO;
import uz.urspi.newurspi.hemisconfig.response.HemisConfigResponse;

import java.util.List;

public interface HemisConfigService {
    HemisConfigResponse create(HemisConfigDTO dto);

    HemisConfigResponse findById(Long id);

    List<HemisConfigResponse> fetchAll();

    HemisConfigResponse update(Long id, HemisConfigDTO dto);

    void delete(Long id);

    void activeOrDisabled(Long id);

    HemisConfig getActiveConfigEntity();
}
