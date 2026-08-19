package uz.urspi.newurspi.permissions.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import uz.urspi.newurspi.audit.AuditAction;
import uz.urspi.newurspi.audit.Auditable;
import uz.urspi.newurspi.permissions.PermissionsResponse;
import uz.urspi.newurspi.permissions.mapper.PermissionsMapper;
import uz.urspi.newurspi.permissions.PermissionsRepository;
import uz.urspi.newurspi.permissions.service.PermissionsService;
import uz.urspi.newurspi.utils.CacheNames;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PermissionsServiceImpl implements PermissionsService {
    private final PermissionsRepository repository;
    private final PermissionsMapper mapper;

    @Override
    @Cacheable(value = CacheNames.PERMISSIONS, key = "'all'")
    @Auditable(action = AuditAction.READ, entity = "Permission")
    public List<PermissionsResponse> fetchAllPermissions() {
        log.info("Fetch all permissions");
        return mapper.toResponseList(repository.findAll());
    }
}
