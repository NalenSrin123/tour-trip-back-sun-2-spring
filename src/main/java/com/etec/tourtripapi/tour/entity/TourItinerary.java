package com.etec.tourtripapi.tour.entity;

import com.etec.tourtripapi.common.enums.ItineraryStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder; // បន្ថែម Builder annotation នៅទីនេះផង
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Builder // <--- ត្រូវមាន @Builder ទើបប្រើ .builder() បាន
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tour_itineraries")
@SQLDelete(sql = "UPDATE tour_itineraries SET status = 'inactive' WHERE id=?")
@SQLRestriction("status = 'active'")
public class TourItinerary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day_number", nullable = false)
    private Integer dayNumber;

    @Column(nullable = false)
    private String title; // កែពី Title មក title

    @Column(columnDefinition = "TEXT")
    private String description; // កែពី Description មក description

    @Column(name = "meals_included")
    private String mealsIncluded; // កែពី MealsIncluded មក mealsIncluded

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "ENUM('active', 'inactive') Default 'active'")
    private ItineraryStatus status = ItineraryStatus.active; // កែពី Status មក status

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;
}