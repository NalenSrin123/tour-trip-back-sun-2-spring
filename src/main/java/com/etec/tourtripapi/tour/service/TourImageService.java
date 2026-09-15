package com.etec.tourtripapi.tour.service;

import com.etec.tourtripapi.common.enums.EntityStatus;
import com.etec.tourtripapi.tour.dto.request.ImageRequestDTO;
import com.etec.tourtripapi.tour.dto.response.ImageResponseDTO;

import java.util.List;
import java.util.Optional;

public interface TourImageService {
    List<ImageResponseDTO> create(ImageRequestDTO requestDTO);
    List<ImageResponseDTO> findAll();
    Optional<ImageResponseDTO> findById(Long id);
    List<ImageResponseDTO> findByTourId(Long tourId);
    ImageResponseDTO update(Long id, ImageRequestDTO requestDTO);
    void updateStatus(Long id, EntityStatus status);
    void deleteById(Long id);
}