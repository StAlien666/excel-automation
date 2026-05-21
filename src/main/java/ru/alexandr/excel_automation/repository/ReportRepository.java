package ru.alexandr.excel_automation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexandr.excel_automation.entity.ReportEntity;
import ru.alexandr.excel_automation.entity.ReportStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReportRepository extends JpaRepository<ReportEntity, UUID> {

    List<ReportEntity> findByStatus(ReportStatus status);

    Optional<ReportEntity> findByDistrictCodeAndPeriodYearAndPeriodMonth(
            String districtCode, Integer year, Integer month
    );
}