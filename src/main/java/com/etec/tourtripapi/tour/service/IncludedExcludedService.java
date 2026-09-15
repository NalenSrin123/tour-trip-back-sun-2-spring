package com.etec.tourtripapi.tour.service;

import com.etec.tourtripapi.common.enums.IncludedExcludedStatus;
import com.etec.tourtripapi.tour.dto.request.IncludedExcludedRequestDTO;
import com.etec.tourtripapi.tour.dto.response.IncludedExcludedResponseDTO;

import java.util.List;
import java.util.Optional;

public interface IncludedExcludedService {

    IncludedExcludedResponseDTO create(IncludedExcludedRequestDTO requestDTO);

    List<IncludedExcludedResponseDTO> findAll();

    Optional<IncludedExcludedResponseDTO> findById(Long id);

    List<IncludedExcludedResponseDTO> findByTourId(Long tourId);

    // 🔥 បន្ថែម Method នេះដើម្បីទ្រទ្រង់ PUT Request សម្រាប់ Update
    IncludedExcludedResponseDTO update(Long id, IncludedExcludedRequestDTO requestDTO);

    void deleteById(Long id);

    void updateStatus(Long id, IncludedExcludedStatus status);
}