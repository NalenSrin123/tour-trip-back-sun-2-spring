package com.etec.tourtripapi.tour.service;

import com.etec.tourtripapi.common.enums.EntityStatus;
import com.etec.tourtripapi.common.exception.ResourceNotFoundException;
import com.etec.tourtripapi.tour.dto.request.TourRequestDTO;
import com.etec.tourtripapi.tour.dto.response.TourResponseDTO;
import com.etec.tourtripapi.tour.entity.Tour;
import com.etec.tourtripapi.tour.mapper.TourMapper;
import com.etec.tourtripapi.tour.repository.TourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;
    private final TourMapper tourMapper;

    @Override
    public TourResponseDTO createTour(TourRequestDTO requestDTO) {
        Tour tour = tourMapper.toEntity(requestDTO);
        Tour savedTour = tourRepository.save(tour);
        return tourMapper.toResponseDTO(savedTour);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TourResponseDTO> findAll() {
        return tourRepository.findAll().stream()
                .map(tourMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TourResponseDTO> findById(Long id) {
        return tourRepository.findById(id)
                .map(tourMapper::toResponseDTO);
    }

    // បន្ថែម Method Update សម្រាប់ PUT Request (បើចាំបាច់)
    public TourResponseDTO updateTour(Long id, TourRequestDTO requestDTO) {
        Tour existingTour = tourRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tour not found with id: " + id));

        // แปลงទិន្នន័យថ្មីពី DTO ចូល Entity ដោយរក្សាទុក ID ដើម
        Tour updatedTour = tourMapper.toEntity(requestDTO);
        updatedTour.setId(existingTour.getId());
        updatedTour.setCreatedAt(existingTour.getCreatedAt()); // រក្សាទុកកាលបរិច្ឆេទបង្កើតដើម

        Tour saved = tourRepository.save(updatedTour);
        return tourMapper.toResponseDTO(saved);
    }

    @Override
    public void deleteById(Long id) {
        if (!tourRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tour not found with id: " + id);
        }
        tourRepository.deleteById(id); // ដំណើរការ Soft Delete តាមរយៈ @SQLDelete ក្នុង Tour Entity
    }

    @Override
    public void updateTourStatus(Long id, String status) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tour not found with id: " + id));

        tour.setStatus(EntityStatus.valueOf(status.toLowerCase()));
        tourRepository.save(tour);
    }
}