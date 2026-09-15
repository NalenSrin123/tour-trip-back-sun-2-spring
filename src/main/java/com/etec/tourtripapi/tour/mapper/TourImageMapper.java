package com.etec.tourtripapi.tour.mapper;

import com.etec.tourtripapi.common.enums.EntityStatus;
import com.etec.tourtripapi.tour.dto.request.ImageRequestDTO;
import com.etec.tourtripapi.tour.dto.response.ImageResponseDTO;
import com.etec.tourtripapi.tour.entity.Tour;
import com.etec.tourtripapi.tour.entity.TourImage;
import com.etec.tourtripapi.tour.repository.TourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TourImageMapper {

    private final TourRepository tourRepository;

    public TourImage toEntity(ImageRequestDTO dto) {
        if (dto == null) return null;

        Tour tour = tourRepository.findById(dto.getTourId())
                .orElseThrow(() -> new RuntimeException("Tour not found with ID: " + dto.getTourId()));

        TourImage entity = new TourImage();
        entity.setTour(tour);
        // ក្នុងករណីខ្លះបើមានផ្ញើ imageUrl មកជា Text ផ្ទាល់ អាចដាក់បញ្ចូលបាន (ប៉ុន្តែ Service យើងបានចាត់ចែងរឿង File upload រួចស្រេច)
        entity.setImageUrl(dto.getImageUrl());
        entity.setIsPrimary(dto.getIsPrimary() != null ? dto.getIsPrimary() : false);
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : EntityStatus.active);

        return entity;
    }

    public ImageResponseDTO toResponseDTO(TourImage entity) {
        if (entity == null) return null;

        return ImageResponseDTO.builder()
                .id(entity.getId())
                .tourId(entity.getTour() != null ? entity.getTour().getId() : null)
                .imageUrl(entity.getImageUrl())
                .isPrimary(entity.getIsPrimary())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}