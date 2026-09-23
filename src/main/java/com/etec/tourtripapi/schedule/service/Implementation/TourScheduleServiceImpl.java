package com.etec.tourtripapi.schedule.service.Implementation;

import com.etec.tourtripapi.common.exception.NotFoundException;
import com.etec.tourtripapi.schedule.dto.request.TourScheduleRequest;
import com.etec.tourtripapi.schedule.dto.response.TourScheduleResponse;
import com.etec.tourtripapi.schedule.entity.TourSchedule;
import com.etec.tourtripapi.schedule.mapper.TourScheduleMapper;
import com.etec.tourtripapi.schedule.repository.TourScheduleRepository;
import com.etec.tourtripapi.schedule.service.TourScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourScheduleServiceImpl implements TourScheduleService {
    private final TourScheduleRepository tourScheduleRepository;
    private final TourScheduleMapper tourScheduleMapper;

    @Override
    @Transactional
    public TourScheduleResponse createTourSchedule(TourScheduleRequest request) {
        TourSchedule schedule = tourScheduleMapper.toEntity(request);
        if (schedule.getCurrentBooked() == null) {
            schedule.setCurrentBooked(0);
        }
        if (schedule.getStatus() == null) {
            schedule.setStatus(com.etec.tourtripapi.common.enums.TourScheduleStatus.OPEN);
        }
        TourSchedule saved = tourScheduleRepository.save(schedule);
        return tourScheduleMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public TourScheduleResponse updateTourSchedule(Long id, TourScheduleRequest request) {
        TourSchedule schedule = tourScheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tour schedule not found with id: " + id));

        tourScheduleMapper.updateEntityFromRequest(request, schedule);
        TourSchedule saved = tourScheduleRepository.save(schedule);
        return tourScheduleMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public TourScheduleResponse getTourScheduleById(Long id) {
        TourSchedule schedule = tourScheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tour schedule not found with id: " + id));
        return tourScheduleMapper.toResponse(schedule);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TourScheduleResponse> getAllTourSchedules() {
        return tourScheduleRepository.findAll().stream()
                .map(tourScheduleMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteTourSchedule(Long id) {
        if (!tourScheduleRepository.existsById(id)) {
            throw new NotFoundException("Tour schedule not found with id: " + id);
        }
        tourScheduleRepository.deleteById(id);
    }
}
