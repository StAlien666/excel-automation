package ru.alexandr.excel_automation.service;
//Оркестрация

import ru.alexandr.excel_automation.entity.ReportEntity;
import java.util.List;
import java.util.UUID;

public interface ReportService {

    ReportEntity saveReport(ReportEntity report);

    List<ReportEntity> getAllReports();

    ReportEntity getReportById(UUID id);

    void deleteReport(UUID id);
}
