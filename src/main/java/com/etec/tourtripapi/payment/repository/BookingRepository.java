package com.etec.tourtripapi.payment.repository;

import com.etec.tourtripapi.payment.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
}
