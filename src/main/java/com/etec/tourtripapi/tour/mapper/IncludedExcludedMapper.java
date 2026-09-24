package com.etec.tourtripapi.tour.mapper;

import com.etec.tourtripapi.common.enums.IncludedExcludedStatus;
import com.etec.tourtripapi.tour.dto.request.IncludedExcludedRequestDTO;
import com.etec.tourtripapi.tour.dto.response.IncludedExcludedResponseDTO;
import com.etec.tourtripapi.tour.entity.IncludedExcluded;
import com.etec.tourtripapi.tour.entity.Tour;
import com.etec.tourtripapi.tour.repository.TourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IncludedExcludedMapper {

    private final TourRepository tourRepository;

    public IncludedExcluded toEntity(IncludedExcludedRequestDTO dto) {
        if (dto == null) return null;

        Tour tour = null;
        if (dto.getTourId() != null) {
            tour = tourRepository.findById(dto.getTourId())
                    .orElseThrow(() -> new RuntimeException("Tour not found with ID: " + dto.getTourId()));
        }

        IncludedExcluded entity = new IncludedExcluded();
        // Do not set ID here for creation, let the database generate it via IDENTITY
        entity.setType(dto.getType());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : IncludedExcludedStatus.active);
        entity.setTour(tour);

        return entity;
    }

    public IncludedExcludedResponseDTO toResponseDTO(IncludedExcluded entity) {
        if (entity == null) return null;

        IncludedExcludedResponseDTO dto = new IncludedExcludedResponseDTO();
        dto.setId(entity.getId());
        dto.setType(entity.getType());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setTourId(entity.getTour() != null ? entity.getTour().getId() : null);
        return dto;
    }
}