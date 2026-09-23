package com.etec.tourtripapi.schedule.controller;

import com.etec.tourtripapi.common.response.ApiResponse;
import com.etec.tourtripapi.schedule.dto.request.TourScheduleRequest;
import com.etec.tourtripapi.schedule.dto.response.TourScheduleResponse;
import com.etec.tourtripapi.schedule.service.TourScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tour-schedules")
@RequiredArgsConstructor
public class TourScheduleController {
    private final TourScheduleService tourScheduleService;

    @PostMapping
    public ResponseEntity<ApiResponse<TourScheduleResponse>> createTourSchedule(@Valid @RequestBody TourScheduleRequest request) {
        TourScheduleResponse response = tourScheduleService.createTourSchedule(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>("Tour schedule created successfully", 201, response)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TourScheduleResponse>> updateTourSchedule(@PathVariable Long id,
                                                                               @Valid @RequestBody TourScheduleRequest request) {
        TourScheduleResponse response = tourScheduleService.updateTourSchedule(id, request);
        return ResponseEntity.ok(
                new ApiResponse<>("Tour schedule updated successfully", 200, response)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TourScheduleResponse>> getTourScheduleById(@PathVariable Long id) {
        TourScheduleResponse response = tourScheduleService.getTourScheduleById(id);
        return ResponseEntity.ok(
                new ApiResponse<>("Tour schedule retrieved successfully", 200, response)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TourScheduleResponse>>> getAllTourSchedules() {
        List<TourScheduleResponse> response = tourScheduleService.getAllTourSchedules();
        return ResponseEntity.ok(
                new ApiResponse<>("Tour schedules retrieved successfully", 200, response)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTourSchedule(@PathVariable Long id) {
        tourScheduleService.deleteTourSchedule(id);
        return ResponseEntity.ok(
                new ApiResponse<>("Tour schedule deleted successfully", 200, null)
        );
    }
}
