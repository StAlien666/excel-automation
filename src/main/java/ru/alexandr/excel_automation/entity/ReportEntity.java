package ru.alexandr.excel_automation.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Одна сдача: район + год + месяц + тип + версия.
 * Годовая сводная = запрос 12 таких сдач по району (или 33×12 по отделу).
 */

@Entity
@Table(name = "reports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "district_name", referencedColumnName = "name", nullable = false)
    private DistrictEntity district;

    @Column(name = "period_year", nullable = false)
    private Integer periodYear;

    @Column(name = "period_month", nullable = false)
    private Integer periodMonth;

    @Enumerated(EnumType.STRING)
    @Column(name = "report_type", nullable = false, length = 50)
    private ReportType reportType;

    /** Исправленная сдача за тот же период = version + 1, старая не затирается. */
    @Column(name = "version", nullable = false)
    @Builder.Default
    private Integer version = 1;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private ReportStatus status = ReportStatus.DRAFT;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private UserEntity createdBy;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}