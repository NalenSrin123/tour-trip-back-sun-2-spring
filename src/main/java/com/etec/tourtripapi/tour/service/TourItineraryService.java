package com.etec.tourtripapi.tour.service;

import com.etec.tourtripapi.common.enums.ItineraryStatus;
import com.etec.tourtripapi.tour.dto.request.TourItineraryRequestDTO;
import com.etec.tourtripapi.tour.dto.response.TourItineraryResponseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Validated
public interface TourItineraryService {

    TourItineraryResponseDTO createTourItinerary(@Valid @NotNull TourItineraryRequestDTO requestDTO);

    List<TourItineraryResponseDTO> findAll();

    Optional<TourItineraryResponseDTO> findById(@NotNull @Positive Long id);

    void deleteById(@NotNull @Positive Long id);

    void updateTourItineraryStatus(Long id, ItineraryStatus status);
}