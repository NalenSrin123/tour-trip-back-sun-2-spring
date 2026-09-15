package com.etec.tourtripapi.tour.controller;

import com.etec.tourtripapi.tour.dto.request.TourRequestDTO;
import com.etec.tourtripapi.tour.dto.response.TourResponseDTO;
import com.etec.tourtripapi.tour.service.TourService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tours")
@RequiredArgsConstructor
public class TourController {

    private final TourService tourService;

    // 1. Create a Tour (POST /api/tours)
    @PostMapping
    public ResponseEntity<TourResponseDTO> createTour(@Valid @RequestBody TourRequestDTO requestDTO) {
        TourResponseDTO createdTour = tourService.createTour(requestDTO);
        return new ResponseEntity<>(createdTour, HttpStatus.CREATED);
    }

    // 2. Get All Tours (GET /api/tours)
    @GetMapping
    public ResponseEntity<List<TourResponseDTO>> getAllTours() {
        List<TourResponseDTO> tours = tourService.findAll();
        return ResponseEntity.ok(tours);
    }

    // 3. Get Tour by ID (GET /api/tours/{id})
    @GetMapping("/{id}")
    public ResponseEntity<TourResponseDTO> getTourById(@PathVariable Long id) {
        return tourService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. Delete Tour (DELETE /api/tours/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable Long id) {
        tourService.deleteById(id);
        return ResponseEntity.noContent().build(); // Returns HTTP 204 No Content
    }

    // 5. Update Tour Status (PATCH /api/tours/{id}/status?status=active)
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateTourStatus(@PathVariable Long id, @RequestParam String status) {
        tourService.updateTourStatus(id, status);
        return ResponseEntity.noContent().build();
    }
}