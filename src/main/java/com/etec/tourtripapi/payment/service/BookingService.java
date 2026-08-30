package com.etec.tourtripapi.payment.service;

import com.etec.tourtripapi.payment.dto.request.BookingRequest;
import com.etec.tourtripapi.payment.dto.response.BookingResponse;

import java.util.List;

public interface BookingService {
    BookingResponse createBooking(BookingRequest request);
    BookingResponse getBookingById(Long id);
    List<BookingResponse> getAllBookings();
    void deleteBooking(Long id);
}
