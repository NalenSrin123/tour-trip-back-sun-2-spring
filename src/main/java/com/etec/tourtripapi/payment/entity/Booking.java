package com.etec.tourtripapi.payment.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import com.etec.tourtripapi.common.enums.BookingStatus;
import com.etec.tourtripapi.common.enums.BookingType;

@Data
@Entity
@Table(name = "bookings")
@SQLDelete(sql = "UPDATE bookings SET is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted = false")
public class Booking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "tour_schedule_id")
    private Long tourScheduleId;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "special_requests")
    private String specialRequests;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    @Enumerated(EnumType.STRING)
    private BookingType bookingType;

    @Column(name = "member_count")
    private Integer memberCount;

    @Column(name = "is_deleted")
    private boolean isDeleted = false; // for soft delete

    @OneToMany(mappedBy = "booking" , cascade = CascadeType.ALL)
    private List<Participant> participants;
}
