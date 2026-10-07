
CREATE TABLE districts (
    code      VARCHAR(50)  PRIMARY KEY,
    name      VARCHAR(150) NOT NULL,
    is_active BOOLEAN      NOT NULL DEFAULT TRUE
);
COMMENT ON TABLE districts IS 'Справочник районов области';

CREATE TABLE users (
    id            UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    username      VARCHAR(50)  NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role          VARCHAR(20)  NOT NULL,
    district_code VARCHAR(50),
    is_active     BOOLEAN      NOT NULL DEFAULT TRUE,
    CONSTRAINT users_username_unique UNIQUE (username),
    CONSTRAINT users_role_check CHECK (role IN ('ADMIN', 'DISTRICT_USER')),
    CONSTRAINT users_district_user_needs_district
        CHECK (role <> 'DISTRICT_USER' OR district_code IS NOT NULL)
);
COMMENT ON TABLE users IS 'Пользователи и их роли';

CREATE TABLE reports (
    id            UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    district_code VARCHAR(50)  NOT NULL,
    period_month  INTEGER      NOT NULL,
    period_year   INTEGER      NOT NULL,
    version       INTEGER      NOT NULL DEFAULT 1,
    report_type   VARCHAR(50)  NOT NULL,
    status        VARCHAR(20)  NOT NULL DEFAULT 'UPLOADED',
    created_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP,
    CONSTRAINT unique_report_key
        UNIQUE (district_code, period_year, period_month, version, report_type),
    CONSTRAINT reports_report_type_check CHECK (report_type IN
        ('SALMONELLA_DISTRICT','SALMONELLA_SPECIES','PRODUCTION_ACTIVITY','BAK_BOL')),
    CONSTRAINT reports_status_check CHECK (status IN
        ('UPLOADED','DRAFT','COMMITTED','EXPORTING','READY','ERROR')),
    CONSTRAINT reports_period_month_check CHECK (period_month BETWEEN 1 AND 12),
    CONSTRAINT reports_period_year_check  CHECK (period_year  BETWEEN 2000 AND 2100),
    CONSTRAINT reports_version_check      CHECK (version > 0)
);
COMMENT ON TABLE reports IS 'Метаданные загруженного отчёта';

CREATE TABLE report_data (
    id               UUID          PRIMARY KEY DEFAULT gen_random_uuid(),
    report_id        UUID          NOT NULL,
    row_subject      VARCHAR(150)  NOT NULL,
    metric_name      VARCHAR(150)  NOT NULL,
    value_month      NUMERIC(15,3),
    value_year_start NUMERIC(15,3),
    CONSTRAINT report_data_report_fk
        FOREIGN KEY (report_id) REFERENCES reports(id) ON DELETE CASCADE,
    CONSTRAINT unique_report_metric
        UNIQUE (report_id, row_subject, metric_name)
);
COMMENT ON TABLE report_data IS 'Строки данных отчёта: субъект / метрика / значения';

CREATE INDEX idx_report_data_subject ON report_data(row_subject);
CREATE INDEX idx_report_data_metric  ON report_data(metric_name);