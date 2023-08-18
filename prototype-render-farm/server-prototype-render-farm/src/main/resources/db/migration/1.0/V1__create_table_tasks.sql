DROP TABLE IF EXISTS tasks;
CREATE TABLE tasks
(
    id       BIGSERIAL PRIMARY KEY,
    title    VARCHAR(255) UNIQUE,
    type     VARCHAR(255),
    username VARCHAR(255) NOT NULL
);
