package com.etec.tourtripapi.tour.mapper;

import com.etec.tourtripapi.category.entity.Category;
import com.etec.tourtripapi.category.repository.CategoryRepository;
import com.etec.tourtripapi.destination.entity.Destination;
import com.etec.tourtripapi.destination.repository.DestinationRepository;
import com.etec.tourtripapi.common.enums.EntityStatus;
import com.etec.tourtripapi.tour.dto.request.TourRequestDTO;
import com.etec.tourtripapi.tour.dto.response.TourResponseDTO;
import com.etec.tourtripapi.tour.entity.Tour;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TourMapper {

    private final CategoryRepository categoryRepository;
    private final DestinationRepository destinationRepository;

    public Tour toEntity(TourRequestDTO dto) {
        if (dto == null) return null;

        Category category = null;
        if (dto.getCategoryId() != null) {
            category = categoryRepository.findById(Math.toIntExact(dto.getCategoryId()))
                    .orElseThrow(() -> new RuntimeException("Category not found with ID: " + dto.getCategoryId()));
        }

        Destination destination = null;
        if (dto.getDestinationId() != null) {
            destination = destinationRepository.findById(Math.toIntExact(dto.getDestinationId()))
                    .orElseThrow(() -> new RuntimeException("Destination not found with ID: " + dto.getDestinationId()));
        }

        return Tour.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .slug(dto.getSlug())
                .durationDay(dto.getDurationDay())
                .durationNight(dto.getDurationNight())
                .basePrice(dto.getBasePrice())
                .priceOverride(dto.getPriceOverride())
                .status(dto.getStatus() != null ? dto.getStatus() : EntityStatus.active)
                .category(category)
                .destination(destination)
                .build();
    }

    public TourResponseDTO toResponseDTO(Tour tour) {
        if (tour == null) return null;

        return TourResponseDTO.builder()
                .id(tour.getId())
                .title(tour.getTitle())
                .slug(tour.getSlug())
                .durationDay(tour.getDurationDay())
                .durationNight(tour.getDurationNight())
                .basePrice(tour.getBasePrice())
                .priceOverride(tour.getPriceOverride())
                .status(tour.getStatus())
                .createdAt(tour.getCreatedAt())
                .updatedAt(tour.getUpdatedAt())
                .categoryId(tour.getCategory() != null ? Long.valueOf(tour.getCategory().getId()) : null)
                .categoryName(tour.getCategory() != null ? tour.getCategory().getName() : null)
                .destinationId(tour.getDestination() != null ? Long.valueOf(tour.getDestination().getId()) : null)
                .destinationName(tour.getDestination() != null ? tour.getDestination().getName() : null)
                .build();
    }
}