package com.etec.tourtripapi.tour.service;

import com.etec.tourtripapi.common.enums.ItineraryStatus;
import com.etec.tourtripapi.tour.dto.request.TourItineraryRequestDTO;
import com.etec.tourtripapi.tour.dto.response.TourItineraryResponseDTO;
import com.etec.tourtripapi.tour.entity.TourItinerary;
import com.etec.tourtripapi.tour.mapper.TourItineraryMapper;
import com.etec.tourtripapi.tour.repository.TourItineraryRepository;
import com.etec.tourtripapi.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional // Manages database transactions automatically for all methods
public class TourItineraryServiceImpl implements TourItineraryService {

    private final TourItineraryRepository tourItineraryRepository;
    private final TourItineraryMapper tourItineraryMapper;

    @Override
    public TourItineraryResponseDTO createTourItinerary(TourItineraryRequestDTO requestDTO) {
        // 1. Map Request DTO to Entity using the injected mapper
        TourItinerary tourItinerary = tourItineraryMapper.toEntity(requestDTO);

        // 2. Save to database
        TourItinerary savedTourItinerary = tourItineraryRepository.save(tourItinerary);

        // 3. Convert saved entity back to Response DTO and return
        return tourItineraryMapper.toResponseDTO(savedTourItinerary);
    }

    @Override
    @Transactional(readOnly = true) // Performance optimization for read queries
    public List<TourItineraryResponseDTO> findAll() {
        return tourItineraryRepository.findAll().stream()
                .map(tourItineraryMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TourItineraryResponseDTO> findById(Long id) {
        return tourItineraryRepository.findById(id)
                .map(tourItineraryMapper::toResponseDTO);
    }

    @Override
    public void deleteById(Long id) {
        // Ensure record exists before attempting deletion to avoid silent failures
        if (!tourItineraryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tour itinerary not found with id: " + id);
        }
        tourItineraryRepository.deleteById(id);
    }

    @Override
    public void updateTourItineraryStatus(Long id, ItineraryStatus status) {
        TourItinerary tourItinerary = tourItineraryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tour itinerary not found with id: " + id));

        tourItinerary.setStatus(status);
        tourItineraryRepository.save(tourItinerary);
    }
}