package uz.urspi.newurspi.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uz.urspi.newurspi.configs.AppProperties;
import uz.urspi.newurspi.roles.Role;
import uz.urspi.newurspi.roles.RoleNames;
import uz.urspi.newurspi.roles.RoleRepository;
import uz.urspi.newurspi.utils.Status;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(3)
@ConditionalOnProperty(prefix = "app.seed", name = "enabled", havingValue = "true", matchIfMissing = true)
public class UserDataLoader implements CommandLineRunner {

    private static final String SYSTEM_USER = "SYSTEM";
    private static final String DEFAULT_PASSWORD = "admin";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppProperties appProperties;

    @Override
    @Transactional
    public void run(String... args) {
        AppProperties.Admin admin = appProperties.getSeed().getAdmin();
        String username = admin.getUsername();

        if (userRepository.existsByUsername(username)) {
            return;
        }

        Role superAdminRole = roleRepository.findByName(RoleNames.SUPER_ADMIN)
                .orElseThrow(() -> new IllegalStateException(RoleNames.SUPER_ADMIN + " role not found"));

        User user = User.builder()
                .fullName(admin.getFullName())
                .username(username)
                .password(passwordEncoder.encode(admin.getPassword()))
                .status(Status.ACTIVE)
                .phone(admin.getPhone())
                .roles(new HashSet<>(Set.of(superAdminRole)))
                .createdUsername(SYSTEM_USER)
                .build();

        userRepository.save(user);

        if (DEFAULT_PASSWORD.equals(admin.getPassword())) {
            log.warn("Default admin user '{}' created with the default password. Change APP_ADMIN_PASSWORD.", username);
        } else {
            log.info("Default admin user '{}' created", username);
        }
    }
}
