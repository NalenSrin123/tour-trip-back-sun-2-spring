package com.etec.tourtripapi.destination.service;

import com.etec.tourtripapi.destination.dto.request.DestinationRequest;
import com.etec.tourtripapi.destination.dto.response.DestinationResponse;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

public interface DestinationService {
    DestinationResponse create(@Valid DestinationRequest.Create requestDTO);
    List<DestinationResponse> findAll();
    Optional<DestinationResponse> findById(Integer id);
    DestinationResponse update(Integer id, @Valid DestinationRequest.Update requestDTO);
    void deleteById(Integer id);


}