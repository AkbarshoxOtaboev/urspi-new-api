package uz.urspi.newurspi.range.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.urspi.newurspi.audit.AuditAction;
import uz.urspi.newurspi.audit.Auditable;
import uz.urspi.newurspi.exceptions.BadRequestException;
import uz.urspi.newurspi.exceptions.ResourceNotFoundException;
import uz.urspi.newurspi.range.Range;
import uz.urspi.newurspi.range.dto.RangeDTO;
import uz.urspi.newurspi.range.mapper.RangeMapper;
import uz.urspi.newurspi.range.repository.RangeRepository;
import uz.urspi.newurspi.range.response.RangeResponse;
import uz.urspi.newurspi.range.service.RangeService;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RangeServiceImpl implements RangeService {
    private final RangeRepository repository;
    private final RangeMapper mapper;

    @Override
    @CacheEvict(value = "ranges", allEntries = true)
    @Auditable(
            action = AuditAction.CREATE,
            entity = "Range"
    )
    public RangeResponse create(RangeDTO dto) {
        if (repository.existsByName(dto.getName())) {
            throw new BadRequestException("Range with this name already exists");
        }

        String username = currentUsername();

        Range range = Range.builder()
                .name(dto.getName())
                .status(Status.ACTIVE)
                .createdUsername(username)
                .build();

        Range saved = repository.save(range);
        log.info("Range created with id {}", saved.getId());
        return mapper.toResponse(saved);
    }

    @Override
    @Cacheable(value = "ranges", key = "#id")
    @Auditable(
            action = AuditAction.READ,
            entity = "Range"
    )
    public RangeResponse findById(Long id) {
        log.info("Find range by id {}", id);
        return mapper.toResponse(getRangeOrThrow(id));
    }

    @Override
    @Cacheable(value = "ranges")
    @Auditable(
            action = AuditAction.READ,
            entity = "Range"
    )
    public List<RangeResponse> fetchAllRanges() {
        log.info("Fetch all ranges");
        return mapper.toResponseList(repository.findAll());
    }

    @Override
    @CacheEvict(value = "ranges", key = "#id")
    @Auditable(
            action = AuditAction.UPDATE,
            entity = "Range"
    )
    public RangeResponse update(Long id, RangeDTO dto) {
        log.info("Update range with id {}", id);

        Range range = getRangeOrThrow(id);

        boolean nameChanged = !range.getName().equalsIgnoreCase(dto.getName());

        if (nameChanged && repository.existsByName(dto.getName())) {
            throw new BadRequestException("Range with this name already exists");
        }

        range.setName(dto.getName());

        Range updated = repository.save(range);
        return mapper.toResponse(updated);
    }

    @Override
    @CacheEvict(value = "ranges", key = "#id")
    @Auditable(
            action = AuditAction.DELETE,
            entity = "Range"
    )
    public void delete(Long id) {
        log.info("Delete range by id {}", id);
        Range range = getRangeOrThrow(id);
        range.setStatus(Status.DELETED);
    }

    @Override
    @CacheEvict(value = "ranges", key = "#id")
    @Auditable(
            action = AuditAction.UPDATE,
            entity = "Range"
    )
    public void activeOrDisabledRange(Long id) {
        log.info("Disable or active range with id {}", id);
        Range range = getRangeOrThrow(id);
        if (range.getStatus() == Status.ACTIVE) {
            log.info("Disabled range {}", range.getName());
            range.setStatus(Status.DISABLED);
        } else if (range.getStatus() == Status.DISABLED) {
            log.info("Activate range {}", range.getName());
            range.setStatus(Status.ACTIVE);
        }
    }

    private Range getRangeOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Range not found with id = " + id));
    }

    private String currentUsername() {
        return Objects.requireNonNull(SecurityContextHolder
                        .getContext()
                        .getAuthentication())
                .getName();
    }
}
