package ru.alexandr.excel_automation.entity;

public enum UserRole {
    ADMIN,          // полный доступ
    DISTRICT_USER,  // загрузка и редактирование в районе
    VIEWER          // просмотр и скачивание
}