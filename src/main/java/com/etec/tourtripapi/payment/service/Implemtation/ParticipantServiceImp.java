package com.etec.tourtripapi.payment.service.Implemtation;

import com.etec.tourtripapi.common.exception.NotFoundException;
import com.etec.tourtripapi.payment.dto.request.ParticipantRequest;
import com.etec.tourtripapi.payment.dto.response.ParticipantResponse;
import com.etec.tourtripapi.payment.entity.Booking;
import com.etec.tourtripapi.payment.entity.Participant;
import com.etec.tourtripapi.payment.mapper.ParticipantMapper;
import com.etec.tourtripapi.payment.repository.BookingRepository;
import com.etec.tourtripapi.payment.repository.ParticipantRepository;
import com.etec.tourtripapi.payment.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImp implements ParticipantService {
    private final ParticipantRepository participantRepository;
    private final BookingRepository bookingRepository;
    private final ParticipantMapper participantMapper;

    @Override
    @Transactional
    public ParticipantResponse createParticipant(ParticipantRequest request, Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Booking not found with id: " + bookingId));
        
        Participant participant = participantMapper.toEntity(request);
        participant.setBooking(booking);
        
        Participant savedParticipant = participantRepository.save(participant);
        return participantMapper.toResponse(savedParticipant);
    }

    @Override
    public ParticipantResponse getParticipantById(Long id) {
        Participant participant = participantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Participant not found with id: " + id));
        return participantMapper.toResponse(participant);
    }

    @Override
    public List<ParticipantResponse> getAllParticipants() {
        return participantRepository.findAll().stream()
                .map(participantMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteParticipant(Long id) {
        if (!participantRepository.existsById(id)) {
            throw new NotFoundException("Participant not found with id: " + id);
        }
        participantRepository.deleteById(id);
    }
}
