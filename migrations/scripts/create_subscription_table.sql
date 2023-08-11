--liquibase formatted sql

--changeset pro100bat9:create_subscription_table

CREATE TABLE IF NOT EXISTS subscription(
    chat_id BIGINT NOT NULL
    link_id BIGINT NOT NULL
    PRIMARY KEY (chat_id, link_id)
    FOREIGN KEY (chat_id) REFERENCES tg_chat (id) ON DELETE CASCADE
    FOREIGN KEY (link_id) REFERENCES link (id) ON DELETE CASCADE
);