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
        migrateLegacyTextColumns();
        backfillPublishedAt("news");
        backfillPublishedAt("announcements");
    }

    private void dropIfExists(String constraintName) {
        jdbcTemplate.execute("ALTER TABLE IF EXISTS permissions DROP CONSTRAINT IF EXISTS " + constraintName);
        log.info("Dropped stale check constraint if present: {}", constraintName);
    }

    private void deleteStalePermissions() {
        if (!columnExists("permissions", "resource") || !columnExists("permissions", "action")) {
            log.info("Skipping stale permission cleanup: permissions.resource/action columns not present yet");
            return;
        }
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

    /**
     * ddl-auto:update eski ustunni rename qilmaydi. fullName → fullNameUz
     * yangi full_name_uz yaratadi, eski full_name NOT NULL qoladi.
     * Uz fieldlar legacy ustunga map qilinadi; yangi duplicate ustundan NOT NULL olinadi.
     */
    private void migrateLegacyTextColumns() {
        backfill("teachers", "full_name", "full_name_uz");
        dropNotNullIfExists("teachers", "full_name_uz");

        backfill("faculties", "name", "name_uz");
        dropNotNullIfExists("faculties", "name_uz");
        backfill("faculties", "description", "description_uz");
        dropNotNullIfExists("faculties", "description_uz");

        backfill("departments", "name", "name_uz");
        dropNotNullIfExists("departments", "name_uz");
        backfill("departments", "description", "description_uz");
        dropNotNullIfExists("departments", "description_uz");
    }

    private void backfillPublishedAt(String table) {
        if (!columnExists(table, "published_at") || !columnExists(table, "created_at")) {
            return;
        }
        int updated = jdbcTemplate.update(
                "UPDATE " + table
                        + " SET published_at = CAST(created_at AS date)"
                        + " WHERE published_at IS NULL AND created_at IS NOT NULL"
        );
        if (updated > 0) {
            log.info("Backfilled {}.published_at for {} rows", table, updated);
        }
    }

    private void backfill(String table, String legacyColumn, String newColumn) {
        if (!columnExists(table, legacyColumn) || !columnExists(table, newColumn)) {
            return;
        }
        jdbcTemplate.execute(
                "UPDATE " + table
                        + " SET " + legacyColumn + " = " + newColumn
                        + " WHERE (" + legacyColumn + " IS NULL OR " + legacyColumn + " = '')"
                        + " AND " + newColumn + " IS NOT NULL AND " + newColumn + " <> ''"
        );
        log.info("Backfilled {}.{} from {}", table, legacyColumn, newColumn);
    }

    private void dropNotNullIfExists(String table, String column) {
        if (!columnExists(table, column)) {
            return;
        }
        jdbcTemplate.execute("ALTER TABLE " + table + " ALTER COLUMN " + column + " DROP NOT NULL");
        log.info("Dropped NOT NULL on {}.{} if present", table, column);
    }

    private boolean columnExists(String table, String column) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM information_schema.columns "
                        + "WHERE table_schema = current_schema() AND table_name = ? AND column_name = ?",
                Integer.class,
                table,
                column
        );
        return count != null && count > 0;
    }
}

