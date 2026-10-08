package ru.alexandr.excel_automation.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

// Архив присланных оригиналов: эксельки, сканы бумаг, скрины.

@Entity
@Table(name = "report_files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "district_name", referencedColumnName = "name", nullable = false)
    private DistrictEntity district;

    @Column(name = "period_year", nullable = false)
    private Integer periodYear;

    //NULL- файл за полугодие или год
    @Column(name = "period_month")
    private Integer periodMonth;

    //NULL — тип не распознан / просто документ района
    @Enumerated(EnumType.STRING)
    @Column(name = "report_type", length = 50)
    private ReportType reportType;

    // Имя у района
    @Column(name = "original_name", nullable = false, length = 255)
    private String originalName;

    @Column(name = "stored_path", nullable = false, length = 500)
    private String storedPath;

    @Column(name = "content_type", length = 100)
    private String contentType;

    @Column(name = "size_bytes", nullable = false)
    private Long sizeBytes;

    @Column(name = "comment", length = 500)
    private String comment;

    @Column(name = "uploaded_at", nullable = false, updatable = false)
    private LocalDateTime uploadedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_by")
    private UserEntity uploadedBy;

    @PrePersist
    protected void onCreate() {
        uploadedAt = LocalDateTime.now();
    }
}
