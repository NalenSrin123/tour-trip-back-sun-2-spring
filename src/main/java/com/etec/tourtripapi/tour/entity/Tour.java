package com.etec.tourtripapi.tour.entity;

import com.etec.tourtripapi.common.enums.EntityStatus;
import com.etec.tourtripapi.category.entity.Category;
import com.etec.tourtripapi.destination.entity.Destination;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tours")
@SQLDelete(sql = "UPDATE tours SET tour_status = 'inactive' WHERE tour_id=?")
@SQLRestriction("tour_status = 'active'")
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tour_id")
    private Long id; // Changed to lowercase

    @Column(name = "title", nullable = false)
    private String title; // Changed to lowercase

    @Column(name = "slug", nullable = false, unique = true)
    private String slug; // Changed to lowercase

    @Column(name = "duration_day")
    private Integer durationDay; // Changed to lowercase

    @Column(name = "duration_night")
    private Integer durationNight; // Changed to lowercase

    @Column(name = "base_price")
    private BigDecimal basePrice; // Changed to lowercase

    @Column(name = "price_override")
    private BigDecimal priceOverride; // Changed to lowercase

    @Enumerated(EnumType.STRING)
    @Column(name = "tour_status", columnDefinition = "ENUM('active', 'inactive') Default 'active'")
    private EntityStatus status = EntityStatus.active;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // relationship connection
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id")
    private Destination destination;
}