package uz.urspi.newurspi.roles;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.urspi.newurspi.audit.AuditAction;
import uz.urspi.newurspi.audit.Auditable;
import uz.urspi.newurspi.exceptions.AlreadyExistsException;
import uz.urspi.newurspi.exceptions.ResourceNotFoundException;
import uz.urspi.newurspi.permissions.Permissions;
import uz.urspi.newurspi.permissions.PermissionsRepository;
import uz.urspi.newurspi.permissions.PermissionsResponse;
import uz.urspi.newurspi.permissions.mapper.PermissionsMapper;
import uz.urspi.newurspi.utils.CacheNames;
import uz.urspi.newurspi.utils.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleServiceImplement implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionsRepository permissionsRepository;
    private final PermissionsMapper permissionsMapper;

    @Override
    @CacheEvict(value = {CacheNames.ROLES, CacheNames.USERS}, allEntries = true)
    @Auditable(
            action = AuditAction.CREATE,
            entity = "Role"
    )
    public RoleResponse create(RoleDTO dto) {

        log.info("Create role with name: {}", dto.getName());

        if (roleRepository.existsRoleByName(dto.getName())) {
            throw new AlreadyExistsException("Role already exists with name: " + dto.getName());
        }
        String username = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();

        Role role = Role.builder()
                .name(dto.getName())
                .status(Status.ACTIVE)
                .createdUsername(username)
                .build();

        Role savedRole = roleRepository.save(role);

        return mapRoleToRoleResponse(savedRole);
    }

    @Override
    @Cacheable(value = CacheNames.ROLES, key = "'all'")
    @Auditable(
            action = AuditAction.READ,
            entity = "Role"
    )
    public List<RoleResponse> fetchAllRoles() {
        log.info("Fetch all roles");
        return roleRepository.findAll().stream()
                .map(this::mapRoleToRoleResponse)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    @Cacheable(value = CacheNames.ROLES, key = "#id")
    @Auditable(
            action = AuditAction.READ,
            entity = "Role"
    )
    public RoleResponse findById(Long id) {
        log.info("Find role by id {}", id);
        return mapRoleToRoleResponse(roleRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Role not found with id " + id)));
    }

    @Override
    @CacheEvict(value = {CacheNames.ROLES, CacheNames.USERS, CacheNames.PERMISSIONS}, allEntries = true)
    @Auditable(
            action = AuditAction.DELETE,
            entity = "Role"
    )
    public void delete(Long id) {
        log.info("Delete role by id {} ", id);
        Role role = roleRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Role not found with id " + id));
        role.setStatus(Status.DELETED);
    }

    @Override
    @CacheEvict(value = {CacheNames.ROLES, CacheNames.USERS}, allEntries = true)
    @Auditable(
            action = AuditAction.UPDATE,
            entity = "Role"
    )
    public RoleResponse update(Long id, RoleDTO dto) {
        log.info("Find role by id {}, for update", id);
        Role role = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role with id not found"));
        log.info("Change role name {} to => {}", role.getName(), dto.getName());
        role.setName(dto.getName());
        return mapRoleToRoleResponse(role);
    }

    @Override
    @CacheEvict(value = CacheNames.PERMISSIONS, allEntries = true)
    @Auditable(
            action = AuditAction.UPDATE,
            entity = "Role"
    )
    public void setPermissionToRole(Long roleId, Long permissionId) {
        log.info("Set permission {} to role {}", permissionId, roleId);
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        Permissions permission = permissionsRepository.findById(permissionId)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found"));

        if (role.getPermissions().contains(permission)) {
            throw new IllegalArgumentException("Permission already assigned to role");
        }

        role.getPermissions().add(permission);

        roleRepository.save(role);
    }

    @Override
    @CacheEvict(value = CacheNames.PERMISSIONS, allEntries = true)
    @Auditable(
            action = AuditAction.UPDATE,
            entity = "Role"
    )
    public void removePermissionFromRole(Long roleId, Long permissionId) {
        log.info("Remove permission {} from role {}", permissionId, roleId);
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        Permissions permission = permissionsRepository.findById(permissionId)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found"));

        if (!role.getPermissions().contains(permission)) {
            throw new IllegalArgumentException("Permission is not assigned to role");
        }

        role.getPermissions().remove(permission);

        roleRepository.save(role);
    }

    @Override
    @Cacheable(value = CacheNames.PERMISSIONS, key = "#roleId")
    public List<PermissionsResponse> fetchPermissionsByRoleId(Long roleId) {
        log.info("Fetch all permissions by role id {}", roleId);
        return permissionsMapper.toResponseList(permissionsRepository.findAllByRoleId(roleId));
    }

    private RoleResponse mapRoleToRoleResponse(Role role){
        return new RoleResponse(
                role.getId(),
                role.getName(),
                role.getStatus(),
                role.getCreatedAt(),
                role.getUpdatedAt(),
                role.getCreatedUsername());
    }
}
