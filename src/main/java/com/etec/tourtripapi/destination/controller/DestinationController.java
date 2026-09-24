package com.etec.tourtripapi.destination.controller;

import com.etec.tourtripapi.destination.dto.request.DestinationRequest;
import com.etec.tourtripapi.destination.dto.response.DestinationResponse;
import com.etec.tourtripapi.destination.service.DestinationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/destinations")
@RequiredArgsConstructor
public class DestinationController {

    private final DestinationService destinationService;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DestinationResponse> create(
            // ប្រើ @RequestParam ជំនួស @RequestPart សម្រាប់ data ដើម្បីការពារបញ្ហា Content-Type ខុសពី Postman
            @RequestParam("data") String dataJsonString,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        // ប្រើ ObjectMapper ដើម្បីแปลง String JSON ទៅជា Object វិញដោយខ្លួនឯង
        ObjectMapper objectMapper = new ObjectMapper();
        DestinationRequest.Create requestDTO;
        try {
            requestDTO = objectMapper.readValue(dataJsonString, DestinationRequest.Create.class);
        } catch (Exception e) {
            throw new RuntimeException("Invalid JSON format for data: " + e.getMessage());
        }

        if (file != null && !file.isEmpty()) {
            requestDTO.setFile(file);
        }

        DestinationResponse response = destinationService.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 2. Get all destinations
    @GetMapping
    public ResponseEntity<List<DestinationResponse>> findAll() {
        List<DestinationResponse> responses = destinationService.findAll();
        return ResponseEntity.ok(responses);
    }

    // 3. Get destination by ID
    @GetMapping("/{id}")
    public ResponseEntity<DestinationResponse> findById(@PathVariable Integer id) {
        DestinationResponse response = destinationService.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination not found with id: " + id));
        return ResponseEntity.ok(response);
    }

    // 4. Update destination
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DestinationResponse> update(
            @PathVariable Integer id,
            @RequestBody @Valid DestinationRequest.Update requestDTO
    ) {
        DestinationResponse response = destinationService.update(id, requestDTO);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        destinationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}