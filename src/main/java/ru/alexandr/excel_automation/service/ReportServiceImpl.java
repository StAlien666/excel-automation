package ru.alexandr.excel_automation.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alexandr.excel_automation.entity.ReportEntity;
import ru.alexandr.excel_automation.repository.ReportRepository;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor // 1. Эта штука сама создаст конструктор для внедрения
public class ReportServiceImpl implements ReportService {

    // 2. Spring сам найдет ReportRepository и положит его сюда
    private final ReportRepository reportRepository;

    @Override
    public ReportEntity saveReport(ReportEntity report) {
        // Просто делегируем работу в репозиторий
        return reportRepository.save(report);
    }

    @Override
    public List<ReportEntity> getAllReports() {
        return reportRepository.findAll();
    }

    @Override
    public ReportEntity getReportById(UUID id) {
        // orElseThrow бросит ошибку, если отчет не найден
        return reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found with id: " + id));
    }

    @Override
    public void deleteReport(UUID id) {
        reportRepository.deleteById(id);
    }
}
