package com.oshovskii.server.services.interfaces;

import com.oshovskii.server.models.Status;
import com.oshovskii.server.models.enums.StatusTitle;

public interface StatusService {
    Status save(Status status);
    Status findByTitle(StatusTitle statusTitle);
}
