package com.yolbertdev.auth_service.infrastructure.persistence.jpa;

import com.yolbertdev.auth_service.infrastructure.persistence.entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaSessionRepository extends JpaRepository<SessionEntity, UUID> {

    Optional<SessionEntity> findByJtiAndRevokedFalse(String jti);

    List<SessionEntity> findByUserIdAndRevokedFalse(UUID userId);

    @Modifying
    @Query("""
            UPDATE SessionEntity s
            SET s.revoked = true, s.revokedAt = :now, s.updatedAt = :now
            WHERE s.userId = :userId AND s.revoked = false
            """)
    void revokeAllByUserId(@Param("userId") UUID userId, @Param("now") OffsetDateTime now);
}
