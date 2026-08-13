package uz.urspi.newurspi.roles;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uz.urspi.newurspi.permissions.Permissions;
import uz.urspi.newurspi.permissions.PermissionsRepository;
import uz.urspi.newurspi.utils.Status;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(2)
public class RoleDataLoader implements CommandLineRunner {

    private static final String SYSTEM_USER = "SYSTEM";

    private final RoleRepository repository;
    private final PermissionsRepository permissionsRepository;

    @Override
    @Transactional
    public void run(String... args) {
        Set<Permissions> allPermissions = new HashSet<>(permissionsRepository.findAll());

        Role superAdmin = repository.findByName(RoleNames.SUPER_ADMIN)
                .orElseGet(() -> Role.builder()
                        .name(RoleNames.SUPER_ADMIN)
                        .status(Status.ACTIVE)
                        .permissions(new HashSet<>())
                        .createdUsername(SYSTEM_USER)
                        .build());

        if (superAdmin.getPermissions() == null) {
            superAdmin.setPermissions(new HashSet<>());
        }

        boolean created = superAdmin.getId() == null;
        int before = superAdmin.getPermissions().size();
        superAdmin.getPermissions().addAll(allPermissions);
        repository.save(superAdmin);

        if (created) {
            log.info("{} role created with {} permissions", RoleNames.SUPER_ADMIN, allPermissions.size());
        } else if (superAdmin.getPermissions().size() > before) {
            log.info("{} role synced, now has {} permissions", RoleNames.SUPER_ADMIN, superAdmin.getPermissions().size());
        }

        if (repository.findByName(RoleNames.ADMIN).isEmpty()) {
            Role admin = Role.builder()
                    .name(RoleNames.ADMIN)
                    .status(Status.ACTIVE)
                    .permissions(new HashSet<>(allPermissions))
                    .createdUsername(SYSTEM_USER)
                    .build();
            repository.save(admin);
            log.info("{} role created", RoleNames.ADMIN);
        }
    }
}
