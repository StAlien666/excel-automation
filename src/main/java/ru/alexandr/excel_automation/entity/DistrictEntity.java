package ru.alexandr.excel_automation.entity;

import jakarta.persistence.*;
import lombok.*;


// sort_order — порядок строк в отчётах (не алфавитный!), экспорт маппит по нему.

@Entity
@Table(name = "districts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistrictEntity {

    @Id
    @Column(name = "name", length = 150)
    private String name;

    /** Сокращение из БАК БОЛ (имена районных листов). Есть не у всех. */
    @Column(name = "abbr", length = 20)
    private String abbr;

    @Column(name = "sort_order", nullable = false)
    private Short sortOrder;

    /** Район гасим флагом, а не удалением: на него ссылаются сдачи. */
    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;
}
