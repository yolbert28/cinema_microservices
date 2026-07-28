package com.yolbertdev.auth_service.infrastructure.persistence.adapter;

import com.yolbertdev.auth_service.domain.model.OutboxEvent;
import com.yolbertdev.auth_service.domain.repository.OutboxRepository;
import com.yolbertdev.auth_service.infrastructure.persistence.entity.OutboxEntity;
import com.yolbertdev.auth_service.infrastructure.persistence.jpa.JpaOutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OutboxRepositoryAdapter implements OutboxRepository {

    private final JpaOutboxRepository jpa;

    @Override
    public OutboxEvent save(OutboxEvent event) {
        OutboxEntity entity = OutboxEntity.builder()
                .id(event.getId())
                .aggregateId(event.getAggregateId())
                .aggregateType(event.getAggregateType())
                .eventType(event.getEventType())
                .payload(event.getPayload())
                .status(event.getStatus())
                .createdAt(event.getCreatedAt())
                .updatedAt(event.getUpdatedAt())
                .build();

        jpa.save(entity);
        return event;
    }
}
