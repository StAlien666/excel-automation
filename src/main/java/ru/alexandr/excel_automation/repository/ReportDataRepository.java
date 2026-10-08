package ru.alexandr.excel_automation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexandr.excel_automation.entity.ReportDataEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReportDataRepository extends JpaRepository<ReportDataEntity, UUID> {
    List<ReportDataEntity> findByReportId(UUID reportId);
    void deleteByReportId(UUID reportId);
}