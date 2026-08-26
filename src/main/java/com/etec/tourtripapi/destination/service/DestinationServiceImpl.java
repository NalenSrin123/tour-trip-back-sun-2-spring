package com.etec.tourtripapi.destination.service;

import com.etec.tourtripapi.common.exception.DuplicateResourceException;
import com.etec.tourtripapi.common.exception.ResourceNotFoundException;
import com.etec.tourtripapi.destination.dto.request.DestinationRequest;
import com.etec.tourtripapi.destination.dto.response.DestinationResponse;
import com.etec.tourtripapi.destination.entity.Destination;
import com.etec.tourtripapi.destination.mapper.DestinationMapper;
import com.etec.tourtripapi.destination.repository.DestinationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DestinationServiceImpl implements DestinationService {

    private final DestinationRepository destinationRepository;
    private final DestinationMapper destinationMapper;

    @Override
    public DestinationResponse create(DestinationRequest request) {
        if (destinationRepository.existsBySlug(request.getSlug())) {
            throw new DuplicateResourceException(
                    "Destination slug '" + request.getSlug() + "' already exists");
        }
        Destination destination = destinationMapper.toEntity(request);
        if (destination.getStatus() == null) {
            destination.setStatus(true);
        }
        return destinationMapper.toResponse(destinationRepository.save(destination));
    }

    @Override
    public DestinationResponse update(Integer id, DestinationRequest request) {
        Destination destination = findOrThrow(id);
        if (destinationRepository.existsBySlugAndIdNot(request.getSlug(), id)) {
            throw new DuplicateResourceException(
                    "Destination slug '" + request.getSlug() + "' already exists");
        }
        destinationMapper.updateEntityFromRequest(request, destination);
        return destinationMapper.toResponse(destinationRepository.save(destination));
    }

    @Override
    @Transactional(readOnly = true)
    public DestinationResponse getById(Integer id) {
        return destinationMapper.toResponse(findOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DestinationResponse> getAll() {
        return destinationMapper.toResponseList(destinationRepository.findAll());
    }

    @Override
    public void delete(Integer id) {
        destinationRepository.delete(findOrThrow(id));
    }

    private Destination findOrThrow(Integer id) {
        return destinationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Destination " + id + " not found"));
    }
}