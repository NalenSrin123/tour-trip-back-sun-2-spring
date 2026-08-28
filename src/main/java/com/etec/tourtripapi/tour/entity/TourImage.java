package com.etec.tourtripapi.tour.entity;

import com.etec.tourtripapi.common.enums.EntityStatus;
import com.etec.tourtripapi.common.enums.InclusionType;
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
@Table(name = "included_excluded")
@SQLDelete(sql = "UPDATE included_excluded SET status = 'inactive' WHERE id=?")
@SQLRestriction("status = 'active'")
public class IncludedExcluded {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Using Long because the DB schema specifies BIGINT

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, columnDefinition = "ENUM('included', 'excluded')")
    private InclusionType type;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "ENUM('active', 'inactive') Default 'active'")
    private EntityStatus status = EntityStatus.active;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Foreign Key mapping to the Tour table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;
}