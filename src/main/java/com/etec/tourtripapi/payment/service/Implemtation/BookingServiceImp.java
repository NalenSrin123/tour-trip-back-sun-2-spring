package com.etec.tourtripapi.payment.service.Implemtation;

import com.etec.tourtripapi.common.enums.BookingStatus;
import com.etec.tourtripapi.common.exception.NotFoundException;
import com.etec.tourtripapi.payment.dto.request.BookingRequest;
import com.etec.tourtripapi.payment.dto.response.BookingResponse;
import com.etec.tourtripapi.payment.entity.Booking;
import com.etec.tourtripapi.payment.mapper.BookingMapper;
import com.etec.tourtripapi.payment.repository.BookingRepository;
import com.etec.tourtripapi.payment.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImp implements BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    @Override
    @Transactional
    public BookingResponse createBooking(BookingRequest request) {
        Booking booking = bookingMapper.toEntity(request);
        
        if (booking.getParticipants() != null) {
            booking.getParticipants().forEach(p -> p.setBooking(booking));
        }
        
        booking.setBookingStatus(BookingStatus.Pending);
        
        Booking savedBooking = bookingRepository.save(booking);
        return bookingMapper.toResponse(savedBooking);
    }

    @Override
    public BookingResponse getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking not found with id: " + id));
        return bookingMapper.toResponse(booking);
    }

    @Override
    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(bookingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new NotFoundException("Booking not found with id: " + id);
        }
        bookingRepository.deleteById(id);
    }
}
