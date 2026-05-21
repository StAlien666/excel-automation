package ru.alexandr.excel_automation.entity;
//Строки данных (строгие колонки)


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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id", nullable = false)
    private ReportEntity report;

    // субъект измерения
    @Column(name = "row_subject", nullable = false, length = 150)
    private String rowSubject;

    // ключ метрики
    @Column(name = "metric_name", nullable = false, length = 150)
    private String metricName;

    // за отчётный месяц
    @Column(name = "value_month")
    private Integer valueMonth;

    //с начала года
    @Column(name = "value_year_start")
    private Integer valueYearStart;

    //один отчёт + один субъект + одна метрика = уникальность
    @Column(name = "unique_key", insertable = false, updatable = false)
    private String uniqueKey;
}