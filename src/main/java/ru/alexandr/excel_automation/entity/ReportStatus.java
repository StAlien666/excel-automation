package ru.alexandr.excel_automation.entity;

public enum ReportStatus {
    UPLOADED,    // загружен, парсинг завершён
    DRAFT,       // редактируются
    COMMITTED,   // готов к экспорту
    EXPORTING,   // генерация Excel
    READY,       // готов к скачиванию
    ERROR
}
