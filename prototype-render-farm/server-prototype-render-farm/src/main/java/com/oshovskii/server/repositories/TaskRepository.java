package com.oshovskii.server.repositories;

import com.oshovskii.server.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByTitle(String title);

    Optional<List<Task>> findByUsername(String username);
}
