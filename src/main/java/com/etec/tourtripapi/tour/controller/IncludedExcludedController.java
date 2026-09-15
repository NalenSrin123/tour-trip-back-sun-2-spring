package com.etec.tourtripapi.tour.controller;

import com.etec.tourtripapi.common.enums.IncludedExcludedStatus;
import com.etec.tourtripapi.tour.dto.request.IncludedExcludedRequestDTO;
import com.etec.tourtripapi.tour.dto.response.IncludedExcludedResponseDTO;
import com.etec.tourtripapi.tour.service.IncludedExcludedService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/included-excluded")
@RequiredArgsConstructor
public class IncludedExcludedController {

    private final IncludedExcludedService service;

    @PostMapping
    public ResponseEntity<IncludedExcludedResponseDTO> create(@Valid @RequestBody IncludedExcludedRequestDTO requestDTO) {
        IncludedExcludedResponseDTO response = service.create(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<IncludedExcludedResponseDTO>> findAll() {
        List<IncludedExcludedResponseDTO> responseList = service.findAll();
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncludedExcludedResponseDTO> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tour/{tourId}")
    public ResponseEntity<List<IncludedExcludedResponseDTO>> findByTourId(@PathVariable Long tourId) {
        List<IncludedExcludedResponseDTO> responseList = service.findByTourId(tourId);
        return ResponseEntity.ok(responseList);
    }

    // 🔥 បន្ថែម Endpoint នេះសម្រាប់ Update (PUT) ធានាថាត្រូវគ្នាជាមួយ Service ដែលបានកែប្រែ
    @PutMapping("/{id}")
    public ResponseEntity<IncludedExcludedResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody IncludedExcludedRequestDTO requestDTO) {
        IncludedExcludedResponseDTO response = service.update(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long id,
            @RequestParam IncludedExcludedStatus status) {
        service.updateStatus(id, status);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}