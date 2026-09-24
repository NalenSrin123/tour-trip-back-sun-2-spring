package com.etec.tourtripapi.tour.mapper;

import com.etec.tourtripapi.tour.dto.request.TourItineraryRequestDTO;
import com.etec.tourtripapi.tour.dto.response.TourItineraryResponseDTO;
import com.etec.tourtripapi.tour.entity.Tour;
import com.etec.tourtripapi.tour.entity.TourItinerary;
import com.etec.tourtripapi.tour.repository.TourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TourItineraryMapper {

    private final TourRepository tourRepository;

    public TourItinerary toEntity(TourItineraryRequestDTO dto) {
        if (dto == null) return null;

        Tour tour = null;
        if (dto.getTourId() != null) {
            tour = tourRepository.findById(dto.getTourId())
                    .orElseThrow(() -> new RuntimeException("Tour not found with ID: " + dto.getTourId()));
        }

        return TourItinerary.builder()
                .id(dto.getId())
                .dayNumber(dto.getDayNumber())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .mealsIncluded(dto.getMealsIncluded())
                .status(dto.getStatus())
                .tour(tour)
                .build();
    }

    public TourItineraryResponseDTO toResponseDTO(TourItinerary entity) {
        if (entity == null) return null;

        return TourItineraryResponseDTO.builder()
                .id(entity.getId())
                .dayNumber(entity.getDayNumber())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .mealsIncluded(entity.getMealsIncluded())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .tourId(entity.getTour() != null ? entity.getTour().getId() : null)
                .tourTitle(entity.getTour() != null ? entity.getTour().getTitle() : null)
                .build();
    }
}