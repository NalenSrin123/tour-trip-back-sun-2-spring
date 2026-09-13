package com.etec.tourtripapi.payment.entity;

import com.etec.tourtripapi.common.enums.Sex;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "participants")
@SQLDelete(sql = "UPDATE participants SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class Participant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "booking_id",nullable = false)
    private Booking booking;

    @Column(name = "age_group")
    private String ageGroup;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Column(name = "is_deleted",nullable = false)
    @Builder.Default
    private boolean isDeleted = false;

}
