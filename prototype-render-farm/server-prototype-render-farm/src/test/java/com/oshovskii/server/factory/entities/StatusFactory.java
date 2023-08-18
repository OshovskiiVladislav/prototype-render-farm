package com.oshovskii.server.factory.entities;

import com.oshovskii.server.models.Status;
import com.oshovskii.server.models.enums.StatusTitle;

public class StatusFactory {
    public static Status createStatus(int index) {
        return new Status(
                (long) index,
                StatusTitle.RENDERING
        );
    }
}
