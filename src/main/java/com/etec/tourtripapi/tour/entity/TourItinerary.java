package com.etec.tourtripapi.tour.entity;

import com.etec.tourtripapi.common.enums.EntityStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tour_itineraries")
@SQLDelete(sql = "UPDATE tour_itineraries SET status = 'inactive' WHERE id=?")
@SQLRestriction("status = 'active'")
public class TourItinerary { // 1. Changed to Singular

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day_number", nullable = false)
    private Integer dayNumber;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT") // 2. Expanded storage for descriptions
    private String description;

    @Column(name = "meals_included")
    private String mealsIncluded;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "ENUM('active', 'inactive') Default 'active'")
    private EntityStatus status = EntityStatus.active;

    @CreationTimestamp // 3. Auto-manages creation time
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp // 3. Auto-manages update time
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 4. Added missing relationship to Tour
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;
}