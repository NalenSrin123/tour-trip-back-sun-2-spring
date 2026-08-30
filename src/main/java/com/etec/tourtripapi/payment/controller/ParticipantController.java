package com.etec.tourtripapi.payment.controller;

import com.etec.tourtripapi.common.response.ApiResponse;
import com.etec.tourtripapi.payment.dto.request.ParticipantRequest;
import com.etec.tourtripapi.payment.dto.response.ParticipantResponse;
import com.etec.tourtripapi.payment.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/participants")
@RequiredArgsConstructor
public class ParticipantController {
    private final ParticipantService participantService;

    @PostMapping
    public ResponseEntity<ApiResponse<ParticipantResponse>> createParticipant(
            @RequestBody ParticipantRequest request,
            @RequestParam Long bookingId) {
        ParticipantResponse response = participantService.createParticipant(request, bookingId);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>("Participant created successfully", 201, response)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ParticipantResponse>> getParticipantById(@PathVariable Long id) {
        ParticipantResponse response = participantService.getParticipantById(id);
        return ResponseEntity.ok(
                new ApiResponse<>("Participant retrieved successfully", 200, response)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ParticipantResponse>>> getAllParticipants() {
        List<ParticipantResponse> response = participantService.getAllParticipants();
        return ResponseEntity.ok(
                new ApiResponse<>("Participants retrieved successfully", 200, response)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteParticipant(@PathVariable Long id) {
        participantService.deleteParticipant(id);
        return ResponseEntity.ok(
                new ApiResponse<>("Participant deleted successfully", 200, null)
        );
    }
}
