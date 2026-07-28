package com.yolbertdev.auth_service.domain.repository;

import com.yolbertdev.auth_service.domain.model.OutboxEvent;

public interface OutboxRepository {

    OutboxEvent save(OutboxEvent event);
}
