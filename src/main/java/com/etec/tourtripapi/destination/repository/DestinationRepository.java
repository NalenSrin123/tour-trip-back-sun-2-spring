package com.etec.tourtripapi.destination.repository;

import com.etec.tourtripapi.destination.entity.Destination;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinationRepository extends JpaRepository<Destination, Integer> {
    Optional<Destination> findBySlug(String slug);
    boolean existsBySlug(String slug);
    boolean existsBySlugAndIdNot(String slug, Integer id);
}