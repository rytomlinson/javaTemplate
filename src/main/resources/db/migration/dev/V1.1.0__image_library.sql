SET SCHEMA 'insight';

CREATE TABLE image_library (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name TEXT,
    owner uuid,
    size INTEGER,
    width INTEGER,
    height INTEGER,
    extension TEXT
) WITH (OIDS = false);
