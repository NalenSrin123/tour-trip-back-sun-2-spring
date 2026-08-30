package com.etec.tourtripapi.destination.service;

import com.etec.tourtripapi.destination.dto.request.DestinationRequest;
import com.etec.tourtripapi.destination.dto.response.DestinationResponse;
import java.util.List;

public interface DestinationService {
    DestinationResponse create(DestinationRequest request);
    DestinationResponse update(Integer id, DestinationRequest request);
    DestinationResponse getById(Integer id);
    List<DestinationResponse> getAll();
    void delete(Integer id);
}