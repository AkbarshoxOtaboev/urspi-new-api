package uz.urspi.newurspi.rental.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.urspi.newurspi.audit.AuditAction;
import uz.urspi.newurspi.audit.Auditable;
import uz.urspi.newurspi.exceptions.BadRequestException;
import uz.urspi.newurspi.exceptions.ResourceNotFoundException;
import uz.urspi.newurspi.rental.Rental;
import uz.urspi.newurspi.rental.dto.RentalDTO;
import uz.urspi.newurspi.rental.mapper.RentalMapper;
import uz.urspi.newurspi.rental.repository.RentalRepository;
import uz.urspi.newurspi.rental.response.RentalLocalizedResponse;
import uz.urspi.newurspi.rental.response.RentalResponse;
import uz.urspi.newurspi.rental.service.RentalService;
import uz.urspi.newurspi.storage.StorageService;
import uz.urspi.newurspi.utils.CacheNames;
import uz.urspi.newurspi.utils.Language;
import uz.urspi.newurspi.utils.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RentalServiceImpl implements RentalService {
    private static final int MAX_IMAGES = 10;

    private final RentalRepository repository;
    private final RentalMapper mapper;
    private final StorageService storageService;

    @Override
    @CacheEvict(value = CacheNames.RENTALS, allEntries = true)
    @Auditable(action = AuditAction.CREATE, entity = "Rental")
    public RentalResponse create(RentalDTO dto) {
        return mapper.toResponse(repository.save(Rental.builder()
                .titleUz(dto.getTitleUz())
                .titleRu(dto.getTitleRu())
                .titleEn(dto.getTitleEn())
                .addressUz(dto.getAddressUz())
                .addressRu(dto.getAddressRu())
                .addressEn(dto.getAddressEn())
                .priceUz(dto.getPriceUz())
                .priceRu(dto.getPriceRu())
                .priceEn(dto.getPriceEn())
                .phoneNumber(dto.getPhoneNumber())
                .imageLinks(storeFiles(dto.getImages()))
                .status(Status.ACTIVE)
                .createdUsername(currentUsername())
                .build()));
    }

    @Override
    @Cacheable(value = CacheNames.RENTALS, key = "#id")
    @Auditable(action = AuditAction.READ, entity = "Rental")
    public RentalResponse findById(Long id) {
        return mapper.toResponse(getOrThrow(id));
    }

    @Override
    @Cacheable(value = CacheNames.RENTALS, key = "'all'")
    @Auditable(action = AuditAction.READ, entity = "Rental")
    public List<RentalResponse> fetchAll() {
        return mapper.toResponseList(repository.findAllByOrderByCreatedAtDesc());
    }

    @Override
    @Cacheable(value = CacheNames.RENTALS, key = "'lang_' + #lang.name()")
    @Auditable(action = AuditAction.READ, entity = "Rental")
    public List<RentalLocalizedResponse> fetchAllByLang(Language lang) {
        return mapper.toLocalizedResponseList(repository.findAllByOrderByCreatedAtDesc(), lang);
    }

    @Override
    @CacheEvict(value = CacheNames.RENTALS, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "Rental")
    public RentalResponse update(Long id, RentalDTO dto) {
        Rental entity = getOrThrow(id);
        entity.setTitleUz(dto.getTitleUz());
        entity.setTitleRu(dto.getTitleRu());
        entity.setTitleEn(dto.getTitleEn());
        entity.setAddressUz(dto.getAddressUz());
        entity.setAddressRu(dto.getAddressRu());
        entity.setAddressEn(dto.getAddressEn());
        entity.setPriceUz(dto.getPriceUz());
        entity.setPriceRu(dto.getPriceRu());
        entity.setPriceEn(dto.getPriceEn());
        entity.setPhoneNumber(dto.getPhoneNumber());
        List<String> newLinks = storeFiles(dto.getImages());
        if (!newLinks.isEmpty()) {
            entity.setImageLinks(newLinks);
        }
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @CacheEvict(value = CacheNames.RENTALS, allEntries = true)
    @Auditable(action = AuditAction.DELETE, entity = "Rental")
    public void delete(Long id) {
        getOrThrow(id).setStatus(Status.DELETED);
    }

    @Override
    @CacheEvict(value = CacheNames.RENTALS, allEntries = true)
    @Auditable(action = AuditAction.UPDATE, entity = "Rental")
    public void activeOrDisabled(Long id) {
        Rental entity = getOrThrow(id);
        if (entity.getStatus() == Status.ACTIVE) {
            entity.setStatus(Status.DISABLED);
        } else if (entity.getStatus() == Status.DISABLED) {
            entity.setStatus(Status.ACTIVE);
        }
    }

    private Rental getOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found with id = " + id));
    }

    private String currentUsername() {
        return Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
    }

    private String storeFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        return "/api/files/" + storageService.uploadFile(file);
    }

    private List<String> storeFiles(List<MultipartFile> files) {
        if (files == null || files.isEmpty()) {
            return new ArrayList<>();
        }
        if (files.size() > MAX_IMAGES) {
            throw new BadRequestException("Maximum " + MAX_IMAGES + " images allowed");
        }
        List<String> links = new ArrayList<>();
        for (MultipartFile file : files) {
            String link = storeFile(file);
            if (link != null) {
                links.add(link);
            }
        }
        return links;
    }
}
