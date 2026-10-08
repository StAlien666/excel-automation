
CREATE TABLE districts (
    name       VARCHAR(150) PRIMARY KEY,
    abbr       VARCHAR(20),                 -- сокращения из БАК БОЛ (КАЛ, ПОЛ...) имена их листов
    sort_order SMALLINT  NOT NULL,          -- номер строки в отчётах
    is_active  BOOLEAN   NOT NULL DEFAULT TRUE
);

INSERT INTO districts (name, abbr, sort_order) VALUES
    ('Азовский',            NULL,  1),
    ('Большереченский',     'Б-Р', 2),
    ('Большеуковский',      NULL,  3),
    ('Горьковский',         NULL,  4),
    ('Знаменский',          NULL,  5),
    ('Исилькульский',       'ИС',  6),
    ('Калачинский',         'КАЛ', 7),
    ('Колосовский',         NULL,  8),
    ('Кормиловский',        'КОР', 9),
    ('Крутинский',          NULL, 10),
    ('Любинский',           NULL, 11),
    ('Марьяновский',        NULL, 12),
    ('Москаленский',        'МОС', 13),
    ('Муромцевский',        'МУР', 14),
    ('Называевский',        'НАЗ', 15),
    ('Нижнеомский',         NULL, 16),
    ('Нововаршавский',      'Н-В', 17),
    ('Одесский',            'ОД', 18),
    ('Оконешниковский',     'ОК', 19),
    ('Омский',              'ОМ', 20),
    ('Павлоградский',       'ПАВ', 21),
    ('Полтавский',          'ПОЛ', 22),
    ('Русско-Полянский',    'Р-П', 23),
    ('Саргатский',          NULL, 24),
    ('Седельниковский',     'СЕД', 25),
    ('Таврический',         'ТАВ', 26),
    ('Тарский',             'ТАР', 27),
    ('Тевризский',          NULL, 28),
    ('Тюкалинский',         'ТЮК', 29),
    ('Усть-Ишимский',       NULL, 30),
    ('Черлакский',          'ЧЕР', 31),
    ('Шербакульский',       'ШЕР', 32),
    ('Омск',                NULL, 33);

CREATE TABLE users (
    id            UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    username      VARCHAR(50)  NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role          VARCHAR(20)  NOT NULL,
    district_name VARCHAR(150) REFERENCES districts(name) ON UPDATE CASCADE,
    is_active     BOOLEAN      NOT NULL DEFAULT TRUE
);

CREATE TABLE reports (
    id            UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    district_name VARCHAR(150) NOT NULL REFERENCES districts(name) ON UPDATE CASCADE,
    period_year   INTEGER     NOT NULL,
    period_month  INTEGER     NOT NULL,
    report_type   VARCHAR(50) NOT NULL,               -- Java-enum ReportType
    version       INTEGER     NOT NULL DEFAULT 1,
    status        VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
    created_at    TIMESTAMP   NOT NULL DEFAULT now(),
    updated_at    TIMESTAMP,
    created_by    UUID        REFERENCES users(id),
    CONSTRAINT reports_version_unique UNIQUE
        (district_name, period_year, period_month, report_type, version),
    CONSTRAINT reports_month_check CHECK (period_month BETWEEN 1 AND 12)
);

CREATE TABLE report_data (
    id          UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    report_id   UUID        NOT NULL REFERENCES reports(id) ON DELETE CASCADE,
    metric_key  VARCHAR(80) NOT NULL,
    value_month INTEGER,
    CONSTRAINT report_data_unique UNIQUE (report_id, metric_key)
);

CREATE INDEX idx_report_data_report ON report_data(report_id);

CREATE TABLE report_files (
    id            UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    district_name VARCHAR(150) NOT NULL REFERENCES districts(name) ON UPDATE CASCADE,
    period_year   INTEGER      NOT NULL,
    period_month  INTEGER,
    report_type   VARCHAR(50),
    original_name VARCHAR(255) NOT NULL,
    stored_path   VARCHAR(500) NOT NULL,
    content_type  VARCHAR(100),
    size_bytes    BIGINT       NOT NULL,
    comment       VARCHAR(500),
    uploaded_at   TIMESTAMP    NOT NULL DEFAULT now(),
    uploaded_by   UUID         REFERENCES users(id)
);

CREATE INDEX idx_report_files_district ON report_files(district_name, period_year);