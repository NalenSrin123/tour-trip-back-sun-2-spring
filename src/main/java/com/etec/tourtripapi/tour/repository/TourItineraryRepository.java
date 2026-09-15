package com.etec.tourtripapi.tour.repository;

import com.etec.tourtripapi.tour.entity.TourItinerary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourItineraryRepository extends JpaRepository<TourItinerary, Long> {

}
