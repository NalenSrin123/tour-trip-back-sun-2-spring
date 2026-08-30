package com.etec.tourtripapi.destination.controller;

import com.etec.tourtripapi.common.response.ApiResponse;
import com.etec.tourtripapi.destination.dto.request.DestinationRequest;
import com.etec.tourtripapi.destination.dto.response.DestinationResponse;
import com.etec.tourtripapi.destination.service.DestinationService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/destinations")
@RequiredArgsConstructor
public class DestinationController {

    private final DestinationService destinationService;

    @PostMapping
    public ResponseEntity<ApiResponse<DestinationResponse>> create(
            @Valid @RequestBody DestinationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(destinationService.create(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DestinationResponse>> update(
            @PathVariable Integer id, @Valid @RequestBody DestinationRequest request) {
        return ResponseEntity.ok(ApiResponse.success(destinationService.update(id, request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DestinationResponse>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(destinationService.getById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DestinationResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(destinationService.getAll()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        destinationService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Destination deleted", null));
    }
}