package com.etec.tourtripapi.tour.repository;

import com.etec.tourtripapi.tour.entity.TourImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourImageRepository extends JpaRepository<TourImage, Long> {

    // សម្រាប់ស្វែងរកជម្រើសរូបភាពតាម Tour ID
    List<TourImage> findByTourId(Long tourId);

}