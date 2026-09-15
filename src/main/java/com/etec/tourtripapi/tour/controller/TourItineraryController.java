package com.etec.tourtripapi.tour.controller;

import com.etec.tourtripapi.common.enums.ItineraryStatus; // Matches your service import
import com.etec.tourtripapi.tour.dto.request.TourItineraryRequestDTO;
import com.etec.tourtripapi.tour.dto.response.TourItineraryResponseDTO;
import com.etec.tourtripapi.tour.service.TourItineraryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tour-itineraries")
@RequiredArgsConstructor
@Validated // Enables method-level validation for path variables and request parameters
public class TourItineraryController {

    private final TourItineraryService tourItineraryService;

    /**
     * Create a new tour itinerary
     * Endpoint: POST /api/tour-itineraries
     */
    @PostMapping
    public ResponseEntity<TourItineraryResponseDTO> createTourItinerary(
            @Valid @RequestBody TourItineraryRequestDTO requestDTO) {
        TourItineraryResponseDTO createdItinerary = tourItineraryService.createTourItinerary(requestDTO);
        return new ResponseEntity<>(createdItinerary, HttpStatus.CREATED);
    }

    /**
     * Retrieve all tour itineraries
     * Endpoint: GET /api/tour-itineraries
     */
    @GetMapping
    public ResponseEntity<List<TourItineraryResponseDTO>> getAllItineraries() {
        List<TourItineraryResponseDTO> itineraries = tourItineraryService.findAll();
        return ResponseEntity.ok(itineraries);
    }

    /**
     * Retrieve a specific tour itinerary by ID
     * Endpoint: GET /api/tour-itineraries/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TourItineraryResponseDTO> getItineraryById(
            @PathVariable @Positive @NotNull Long id) {
        return tourItineraryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete a tour itinerary by ID
     * Endpoint: DELETE /api/tour-itineraries/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItinerary(
            @PathVariable @Positive @NotNull Long id) {
        tourItineraryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Update the status of a tour itinerary
     * Endpoint: PATCH /api/tour-itineraries/{id}/status?status=ACTIVE
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateItineraryStatus(
            @PathVariable @Positive @NotNull Long id,
            @RequestParam @NotNull ItineraryStatus status) {
        tourItineraryService.updateTourItineraryStatus(id, status);
        return ResponseEntity.noContent().build();
    }
}