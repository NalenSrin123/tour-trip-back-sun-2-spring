package com.etec.tourtripapi.tour.repository;

import com.etec.tourtripapi.tour.entity.IncludedExcluded;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncludedExcludedRepository extends JpaRepository<IncludedExcluded, Long> {

    // Corrected method name: traverses from IncludedExcluded -> tour -> id
    List<IncludedExcluded> findByTourId(Long tourId);

}