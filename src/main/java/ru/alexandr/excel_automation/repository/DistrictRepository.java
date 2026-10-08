package ru.alexandr.excel_automation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexandr.excel_automation.entity.DistrictEntity;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<DistrictEntity, String> {

    // Порядок в отчётах не алфавитный
    List<DistrictEntity> findAllByIsActiveTrueOrderBySortOrderAsc();
}
