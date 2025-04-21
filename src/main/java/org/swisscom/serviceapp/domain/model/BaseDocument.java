package org.swisscom.serviceapp.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class BaseDocument {
    protected UUID id;
    protected LocalDateTime createdAt;

    // field used for optimistic-locking to achieve thread safety
    protected Integer version;
}
