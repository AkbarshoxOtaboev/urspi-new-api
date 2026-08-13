package uz.urspi.newurspi.position.service.impl;

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
import uz.urspi.newurspi.position.Position;
import uz.urspi.newurspi.position.dto.PositionDTO;
import uz.urspi.newurspi.position.mapper.PositionMapper;
import uz.urspi.newurspi.position.repository.PositionRepository;
import uz.urspi.newurspi.position.response.PositionResponse;
import uz.urspi.newurspi.position.service.PositionService;
import uz.urspi.newurspi.utils.CacheNames;
import uz.urspi.newurspi.utils.Status;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PositionServiceImpl implements PositionService {
    private final PositionRepository repository;
    private final PositionMapper mapper;

    @Override
    @CacheEvict(value = {CacheNames.POSITIONS, CacheNames.TEACHERS}, allEntries = true)
    @Auditable(action = AuditAction.CREATE, entity = "Position")
    public PositionResponse create(PositionDTO dto) {
        if (repository.existsByNameIgnoreCase(dto.getName())) {
            throw new BadRequestException("Position with this name already exists");
        }

        Position position = Position.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .status(Status.ACTIVE)
                .createdUsername(currentUsername())
                .build();

        Position saved = repository.save(position);
        log.info("Position created with id {}", saved.getId());
        return mapper.toResponse(saved);
    }

    @Override
    @Cacheable(value = CacheNames.POSITIONS, key = "#id")
    @Auditable(action = AuditAction.READ, entity = "Position")
    public PositionResponse findById(Long id) {
        log.info("Find position by id {}", id);
        return mapper.toResponse(getPositionOrThrow(id));
    }

    @Override
    @Cacheable(value = CacheNames.POSITIONS, key = "'all'")
    @Auditable(action = AuditAction.READ, entity = "Position")
    public List<PositionResponse> fetchAllPositions() {
        log.info("Fetch all positions");
        return mapper.toResponseList(repository.findAll());
    }

    @Override
    @CacheEvict(value = {CacheNames.POSITIONS, CacheNames.TEACHERS}, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "Position")
    public PositionResponse update(Long id, PositionDTO dto) {
        log.info("Update position with id {}", id);
        Position position = getPositionOrThrow(id);

        if (!position.getName().equalsIgnoreCase(dto.getName())
                && repository.existsByNameIgnoreCase(dto.getName())) {
            throw new BadRequestException("Position with this name already exists");
        }

        position.setName(dto.getName());
        position.setDescription(dto.getDescription());
        return mapper.toResponse(repository.save(position));
    }

    @Override
    @CacheEvict(value = {CacheNames.POSITIONS, CacheNames.TEACHERS}, allEntries = true)
    @Auditable(action = AuditAction.DELETE, entity = "Position")
    public void delete(Long id) {
        log.info("Delete position by id {}", id);
        Position position = getPositionOrThrow(id);
        position.setStatus(Status.DELETED);
    }

    @Override
    @CacheEvict(value = {CacheNames.POSITIONS, CacheNames.TEACHERS}, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "Position")
    public void activeOrDisabledPosition(Long id) {
        log.info("Disable or active position with id {}", id);
        Position position = getPositionOrThrow(id);
        if (position.getStatus() == Status.ACTIVE) {
            log.info("Disabled position {}", position.getName());
            position.setStatus(Status.DISABLED);
        } else if (position.getStatus() == Status.DISABLED) {
            log.info("Activate position {}", position.getName());
            position.setStatus(Status.ACTIVE);
        }
    }

    private Position getPositionOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Position not found with id = " + id));
    }

    private String currentUsername() {
        return Objects.requireNonNull(SecurityContextHolder
                        .getContext()
                        .getAuthentication())
                .getName();
    }
}
