DROP TABLE IF EXISTS tasks_statuses;
CREATE TABLE tasks_statuses
(
    task_id   BIGINT NOT NULL REFERENCES tasks (id),
    status_id BIGINT NOT NULL REFERENCES statuses (id),
    PRIMARY KEY (task_id, status_id)
);
