--liquibase formatted sql

--changeset pro100bat9:create_link_sequence

CREATE SEQUENCE link_id_sequence;

--changeset pro100bat9:create_link_table

CREATE TABLE IF NOT EXISTS link(
    id BIGINT PRIMARY KEY DEFAULT nextval('link_id_sequence'),
    url TEXT UNIQUE NOT NULL
    );




