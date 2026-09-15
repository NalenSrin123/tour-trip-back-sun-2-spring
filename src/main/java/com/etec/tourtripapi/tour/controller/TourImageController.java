package com.etec.tourtripapi.tour.controller;

import com.etec.tourtripapi.common.enums.EntityStatus;
import com.etec.tourtripapi.tour.dto.request.ImageRequestDTO;
import com.etec.tourtripapi.tour.dto.response.ImageResponseDTO;
import com.etec.tourtripapi.tour.service.TourImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tour-images")
@RequiredArgsConstructor
public class TourImageController {

    private final TourImageService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<ImageResponseDTO>> create(@Valid @ModelAttribute ImageRequestDTO requestDTO) {
        List<ImageResponseDTO> response = service.create(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ImageResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageResponseDTO> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tour/{tourId}")
    public ResponseEntity<List<ImageResponseDTO>> findByTourId(@PathVariable Long tourId) {
        return ResponseEntity.ok(service.findByTourId(tourId));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageResponseDTO> update(
            @PathVariable Long id,
            @Valid @ModelAttribute ImageRequestDTO requestDTO) {
        return ResponseEntity.ok(service.update(id, requestDTO));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long id,
            @RequestParam EntityStatus status) {
        service.updateStatus(id, status);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}