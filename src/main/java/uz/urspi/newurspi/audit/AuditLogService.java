package  uz.urspi.newurspi.audit;

import java.util.List;

public interface AuditLogService {

    void save(AuditLog auditLog);
    List<AuditResponse> getAll();
}
