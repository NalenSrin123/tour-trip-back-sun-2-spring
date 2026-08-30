package com.etec.tourtripapi.payment.service;

import com.etec.tourtripapi.payment.dto.request.ParticipantRequest;
import com.etec.tourtripapi.payment.dto.response.ParticipantResponse;

import java.util.List;

public interface ParticipantService {
    ParticipantResponse createParticipant(ParticipantRequest request, Long bookingId);
    ParticipantResponse getParticipantById(Long id);
    List<ParticipantResponse> getAllParticipants();
    void deleteParticipant(Long id);
}
