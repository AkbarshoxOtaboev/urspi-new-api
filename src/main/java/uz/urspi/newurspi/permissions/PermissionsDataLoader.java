package uz.urspi.newurspi.permissions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uz.urspi.newurspi.utils.Action;
import uz.urspi.newurspi.utils.Resource;
import uz.urspi.newurspi.utils.Status;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(1)
public class PermissionsDataLoader implements CommandLineRunner {

    private static final String SYSTEM_USER = "SYSTEM";

    private final PermissionsRepository permissionsRepository;

    @Override
    @Transactional
    public void run(String... args) {
        List<Permissions> toCreate = new ArrayList<>();

        for (Resource resource : Resource.values()) {
            for (Action action : Action.values()) {
                if (permissionsRepository.existsByResourceAndAction(resource, action)) {
                    continue;
                }

                toCreate.add(Permissions.builder()
                        .resource(resource)
                        .action(action)
                        .name(resource.name() + "_" + action.name())
                        .status(Status.ACTIVE)
                        .createdUsername(SYSTEM_USER)
                        .build());
            }
        }

        if (toCreate.isEmpty()) {
            log.info("Permissions are already up to date");
            return;
        }

        permissionsRepository.saveAll(toCreate);
        log.info("Created {} permissions", toCreate.size());
    }
}
