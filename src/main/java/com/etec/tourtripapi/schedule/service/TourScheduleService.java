package com.etec.tourtripapi.schedule.service;

import com.etec.tourtripapi.schedule.dto.request.TourScheduleRequest;
import com.etec.tourtripapi.schedule.dto.response.TourScheduleResponse;

import java.util.List;

public interface TourScheduleService {
    TourScheduleResponse createTourSchedule(TourScheduleRequest request);
    TourScheduleResponse updateTourSchedule(Long id, TourScheduleRequest request);
    TourScheduleResponse getTourScheduleById(Long id);
    List<TourScheduleResponse> getAllTourSchedules();
    void deleteTourSchedule(Long id);
}
