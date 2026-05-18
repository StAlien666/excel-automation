-- для авторизации
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('ADMIN', 'DISTRICT_USER', 'VIEWER')),
    district_id VARCHAR(50), -- Привязка к району для DISTRICT_USER
    is_active BOOLEAN DEFAULT TRUE
);

--мета-информация
CREATE TABLE reports (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    district_id VARCHAR(50) NOT NULL,
    period VARCHAR(20) NOT NULL,
    version INT NOT NULL DEFAULT 1,
    status VARCHAR(20) NOT NULL DEFAULT 'UPLOADED', -- UPLOADED, DRAFT, COMMITTED, READY, ERROR
    created_by UUID REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT unique_report_key UNIQUE (district_id, period, version)
);

-- сами цифры из Excel

CREATE TABLE report_data (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    report_id UUID NOT NULL REFERENCES reports(id) ON DELETE CASCADE,
    row_index INT NOT NULL, -- № строки
    col_1 VARCHAR(255),
    col_2 VARCHAR(255),
    col_3 VARCHAR(255),
    col_4 VARCHAR(255),
    col_5 VARCHAR(255)
);

CREATE INDEX idx_report_data_report_id ON report_data(report_id);

INSERT INTO users (username, password_hash, role)
VALUES ('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'ADMIN');

-- пароль user
INSERT INTO users (username, password_hash, role, district_id)
VALUES ('district_omsk', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'DISTRICT_USER', 'OMSK');