package com.etec.tourtripapi.payment.controller;

import com.etec.tourtripapi.common.response.ApiResponse;
import com.etec.tourtripapi.payment.dto.request.BookingRequest;
import com.etec.tourtripapi.payment.dto.response.BookingResponse;
import com.etec.tourtripapi.payment.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<ApiResponse<BookingResponse>> createBooking(@RequestBody BookingRequest request) {
        BookingResponse response = bookingService.createBooking(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>("Booking created successfully", 201, response)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookingResponse>> getBookingById(@PathVariable Long id) {
        BookingResponse response = bookingService.getBookingById(id);
        return ResponseEntity.ok(
                new ApiResponse<>("Booking retrieved successfully", 200, response)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getAllBookings() {
        List<BookingResponse> response = bookingService.getAllBookings();
        return ResponseEntity.ok(
                new ApiResponse<>("Bookings retrieved successfully", 200, response)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.ok(
                new ApiResponse<>("Booking deleted successfully", 200, null)
        );
    }
}
