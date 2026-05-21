-- пользователи
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('ADMIN', 'DISTRICT_USER', 'VIEWER')),
    district_id VARCHAR(50),
    is_active BOOLEAN DEFAULT TRUE
);

-- мета отчёта
CREATE TABLE reports (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    district_code VARCHAR(50) NOT NULL,
    period_month INTEGER NOT NULL,
    period_year INTEGER NOT NULL,
    version INTEGER NOT NULL DEFAULT 1,
    report_type VARCHAR(50) NOT NULL CHECK (report_type IN ('SALMONELLA_DISTRICT', 'SALMONELLA_SPECIES', 'PRODUCTION_ACTIVITY', 'BAK_BOL')), -- новое поле
    status VARCHAR(20) NOT NULL DEFAULT 'UPLOADED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT unique_report_key UNIQUE (district_code, period_year, period_month, version, report_type)
);

-- данные отчёта
CREATE TABLE report_data (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    report_id UUID NOT NULL REFERENCES reports(id) ON DELETE CASCADE,
    row_subject VARCHAR(150) NOT NULL,
    metric_name VARCHAR(150) NOT NULL, -- ключ метрики
    value_month INTEGER, -- значение за месяц
    value_year_start INTEGER, --значение с начала года
    CONSTRAINT unique_report_metric UNIQUE (report_id, row_subject, metric_name) --ограничение
);

-- индексы (добавлено для агрегации)
CREATE INDEX idx_report_data_report_id ON report_data(report_id);
CREATE INDEX idx_report_data_subject ON report_data(row_subject);
CREATE INDEX idx_report_data_metric ON report_data(metric_name);
CREATE INDEX idx_reports_district_period ON reports(district_code, period_year, period_month);

-- тестовые юзеры
INSERT INTO users (username, password_hash, role)
VALUES ('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'ADMIN');

INSERT INTO users (username, password_hash, role, district_id)
VALUES ('district_omsk', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'DISTRICT_USER', 'OMSK');