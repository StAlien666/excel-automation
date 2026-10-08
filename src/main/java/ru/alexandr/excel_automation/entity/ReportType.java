package ru.alexandr.excel_automation.entity;

public enum ReportType {
    SALMONELLA,           // сальмонеллез: по видам + ручные колонки районы
    BAK_BOL,              // бак. болезни: журнал + сводная
    PROIZVODSTVENNAYA,    // производственная деятельность
    TBC_BRUCELLOSIS,      // туберкулез + бруцеллез КРС
    PADEZH                // падеж + поступило в лабораторию
}