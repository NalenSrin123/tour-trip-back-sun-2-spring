package com.etec.tourtripapi.payment.repository;

import com.etec.tourtripapi.payment.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant,Long> {
    List<Participant> findByBookingId(Long bookingId);
}
