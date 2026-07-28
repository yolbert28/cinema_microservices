package com.yolbertdev.auth_service.infrastructure.persistence.jpa;

import com.yolbertdev.auth_service.infrastructure.persistence.entity.OutboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOutboxRepository extends JpaRepository<OutboxEntity, UUID> {
}
