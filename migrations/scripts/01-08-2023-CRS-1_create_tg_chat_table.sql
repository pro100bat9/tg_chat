--liquibase formatted sql

-- --changeset pro100bat9:create_chat_sequence
-- CREATE Sequence chat_id_sequence;


--changeset pro100bat9:create_chat_table
CREATE TABLE IF NOT EXISTS tg_chat
(
    id BIGINT PRIMARY KEY
);