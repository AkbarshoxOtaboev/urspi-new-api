package uz.urspi.newurspi.configs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import uz.urspi.newurspi.utils.Resource;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(0)
public class EnumCheckConstraintMigrator implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) {
        dropIfExists("permissions_resource_check");
        dropIfExists("permissions_action_check");
        dropIfExists("permissions_status_check");
        deleteStalePermissions();
        dropLeftoverTables();
    }

    private void dropIfExists(String constraintName) {
        jdbcTemplate.execute("ALTER TABLE IF EXISTS permissions DROP CONSTRAINT IF EXISTS " + constraintName);
        log.info("Dropped stale check constraint if present: {}", constraintName);
    }

    private void deleteStalePermissions() {
        String placeholders = Arrays.stream(Resource.values())
                .map(resource -> "?")
                .collect(Collectors.joining(","));
        Object[] validResources = Arrays.stream(Resource.values())
                .map(Enum::name)
                .toArray();

        int unlinked = jdbcTemplate.update(
                "DELETE FROM role_permissions WHERE permission_id IN ("
                        + "SELECT id FROM permissions WHERE resource NOT IN (" + placeholders + "))",
                validResources
        );
        int deleted = jdbcTemplate.update(
                "DELETE FROM permissions WHERE resource NOT IN (" + placeholders + ")",
                validResources
        );

        if (unlinked > 0 || deleted > 0) {
            log.info("Removed {} stale permission links and {} stale permissions", unlinked, deleted);
        }
    }

    private void dropLeftoverTables() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS groups CASCADE");
        jdbcTemplate.execute("DROP TABLE IF EXISTS ranges CASCADE");
        jdbcTemplate.execute("DROP TABLE IF EXISTS semesters CASCADE");
        log.info("Dropped leftover groups, ranges and semesters tables if present");
    }
}

