package com.oshovskii.server.services;

import com.oshovskii.server.models.Status;
import com.oshovskii.server.models.enums.StatusTitle;
import com.oshovskii.server.repositories.StatusRepository;
import com.oshovskii.server.services.interfaces.StatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {
    private final StatusRepository statusRepository;

    @Override
    public Status save(Status status) {
        return statusRepository.save(status);
    }

    @Override
    public Status findByTitle(StatusTitle statusTitle) {
        log.info("[findByTitle] statusTitle: {}", statusTitle);
        return statusRepository.findByTitle(statusTitle);
    }
}
