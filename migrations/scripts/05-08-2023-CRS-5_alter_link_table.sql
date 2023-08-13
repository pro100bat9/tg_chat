--liquibase formatted sql

--changeset pro100bat9:add_column_last_check_time
ALTER TABLE link
    ADD COLUMN
        last_check_time TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now();

--liquibase formatted sql

--changeset pro100bat9:add_column_updated_at
ALTER TABLE link
    ADD COLUMN
        updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now();