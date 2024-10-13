CREATE TABLE system_setting (
    id VARCHAR(20) NOT NULL,
    value VARCHAR(20) NOT NULL,
    PRIMARY KEY(id)
);

INSERT INTO system_setting (id, value) VALUES (
    'language',
    'en'
);