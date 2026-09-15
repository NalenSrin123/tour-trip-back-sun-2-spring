package com.etec.tourtripapi.tour.service;

import com.etec.tourtripapi.common.enums.IncludedExcludedStatus;
import com.etec.tourtripapi.common.exception.ResourceNotFoundException;
import com.etec.tourtripapi.tour.dto.request.IncludedExcludedRequestDTO;
import com.etec.tourtripapi.tour.dto.response.IncludedExcludedResponseDTO;
import com.etec.tourtripapi.tour.entity.IncludedExcluded;
import com.etec.tourtripapi.tour.entity.Tour;
import com.etec.tourtripapi.tour.mapper.IncludedExcludedMapper;
import com.etec.tourtripapi.tour.repository.IncludedExcludedRepository;
import com.etec.tourtripapi.tour.repository.TourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class IncludedExcludedServiceImpl implements IncludedExcludedService {

    private final IncludedExcludedRepository repository;
    private final TourRepository tourRepository; // ត្រូវប្រាកដថាបាន Inject repository ນີ້ដើម្បីឆែករក Tour ពេល Update
    private final IncludedExcludedMapper mapper;

    @Override
    public IncludedExcludedResponseDTO create(IncludedExcludedRequestDTO requestDTO) {
        IncludedExcluded entity = mapper.toEntity(requestDTO);
        IncludedExcluded saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IncludedExcludedResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IncludedExcludedResponseDTO> findById(Long id) {
        return repository.findById(id).map(mapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IncludedExcludedResponseDTO> findByTourId(Long tourId) {
        return repository.findByTourId(tourId).stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // 🔥 បន្ថែម Method Update នេះចូល
    @Override
    public IncludedExcludedResponseDTO update(Long id, IncludedExcludedRequestDTO requestDTO) {
        IncludedExcluded existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Included/Excluded not found with id: " + id));

        Tour tour = tourRepository.findById(requestDTO.getTourId())
                .orElseThrow(() -> new ResourceNotFoundException("Tour not found with ID: " + requestDTO.getTourId()));

        existing.setType(requestDTO.getType());
        existing.setDescription(requestDTO.getDescription());
        if (requestDTO.getStatus() != null) {
            existing.setStatus(requestDTO.getStatus());
        }
        existing.setTour(tour);

        IncludedExcluded updated = repository.save(existing);
        return mapper.toResponseDTO(updated);
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Included/Excluded not found with id: " + id);
        }
        repository.deleteById(id); // Triggers @SQLDelete defined in entity
    }

    @Override
    public void updateStatus(Long id, IncludedExcludedStatus status) {
        IncludedExcluded entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Included/Excluded not found with id: " + id));
        entity.setStatus(status);
        repository.save(entity);
    }
}