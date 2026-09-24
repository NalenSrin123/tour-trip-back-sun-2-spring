package com.etec.tourtripapi.tour.service;

import com.etec.tourtripapi.tour.dto.request.TourRequestDTO;
import com.etec.tourtripapi.tour.dto.response.TourResponseDTO;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

public interface TourService {

    TourResponseDTO createTour(@Valid TourRequestDTO requestDTO);

    List<TourResponseDTO> findAll();

    Optional<TourResponseDTO> findById(Long id);

    void deleteById(Long id);

    void updateTourStatus(Long id, String status);
}