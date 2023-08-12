--liquibase formatted sql

--changeset pro100bat9:create_index_for_link_table

CREATE INDEX index_link_url ON link (url);