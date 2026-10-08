package ru.alexandr.excel_automation.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "report_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "report_id", nullable = false)
    private ReportEntity report;

    @Column(name = "metric_key", nullable = false, length = 80)
    private String metricKey;

    @Column(name = "value_month")
    private Integer valueMonth;
}