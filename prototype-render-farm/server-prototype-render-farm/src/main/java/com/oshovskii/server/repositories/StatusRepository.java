package com.oshovskii.server.repositories;

import com.oshovskii.server.models.Status;
import com.oshovskii.server.models.enums.StatusTitle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
    Status findByTitle(StatusTitle statusTitle);
}
