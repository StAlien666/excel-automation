package ru.alexandr.excel_automation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexandr.excel_automation.entity.ReportFileEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReportFileRepository extends JpaRepository<ReportFileEntity, UUID> {

    List<ReportFileEntity> findAllByOrderByUploadedAtDesc();

    List<ReportFileEntity> findByDistrictNameOrderByUploadedAtDesc(String districtName);
}