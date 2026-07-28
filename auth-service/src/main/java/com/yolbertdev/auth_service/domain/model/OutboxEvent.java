package com.yolbertdev.auth_service.domain.model;

import com.yolbertdev.auth_service.domain.enums.OutboxStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutboxEvent {

    private UUID id;
    private UUID aggregateId;
    private String aggregateType;
    private String eventType;
    private Map<String, Object> payload;
    private OutboxStatus status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public static OutboxEvent create(UUID aggregateId,
            String aggregateType,
            String eventType,
            Map<String, Object> payload) {
        OffsetDateTime now = OffsetDateTime.now();
        return OutboxEvent.builder()
                .id(UUID.randomUUID())
                .aggregateId(aggregateId)
                .aggregateType(aggregateType)
                .eventType(eventType)
                .payload(payload)
                .status(OutboxStatus.PENDING)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }
}
