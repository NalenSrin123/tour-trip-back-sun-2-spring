package com.etec.tourtripapi.tour.repository;

import com.etec.tourtripapi.tour.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourRepository extends JpaRepository<Tour, Long> {

}
