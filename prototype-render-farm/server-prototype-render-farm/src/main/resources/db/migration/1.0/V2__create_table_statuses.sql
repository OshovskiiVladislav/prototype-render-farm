DROP TABLE IF EXISTS statuses;
CREATE TABLE statuses
(
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL
);
