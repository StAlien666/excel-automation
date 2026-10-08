package ru.alexandr.excel_automation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexandr.excel_automation.entity.ReportEntity;
import ru.alexandr.excel_automation.entity.ReportStatus;
import ru.alexandr.excel_automation.entity.ReportType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReportRepository extends JpaRepository<ReportEntity, UUID> {

    // version в сигнатуре обязателен: без него исправленная сдача дала бы две строки.
    Optional<ReportEntity> findByDistrictNameAndPeriodYearAndPeriodMonthAndReportTypeAndVersion(
            String districtName, Integer year, Integer month, ReportType type, Integer version);

    // ВСе сдачи типа за месяц — строка сводной таблицы отдела.
    List<ReportEntity> findByPeriodYearAndPeriodMonthAndReportType(
            Integer year, Integer month, ReportType type);

    // год района: 12 сдач для формы и предпросмотра с н.г.
    List<ReportEntity> findByDistrictNameAndPeriodYearAndReportType(
            String districtName, Integer year, ReportType type);

    List<ReportEntity> findByStatus(ReportStatus status);
}