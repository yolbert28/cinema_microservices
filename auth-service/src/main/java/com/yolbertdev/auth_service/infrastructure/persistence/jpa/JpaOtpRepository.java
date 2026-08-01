package com.yolbertdev.auth_service.infrastructure.persistence.jpa;

import com.yolbertdev.auth_service.domain.enums.OtpPurpose;
import com.yolbertdev.auth_service.domain.enums.OtpStatus;
import com.yolbertdev.auth_service.infrastructure.persistence.entity.OtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface JpaOtpRepository extends JpaRepository<OtpEntity, UUID> {

    @Query("""
            SELECT o FROM OtpEntity o
            WHERE o.userId = :userId
              AND o.purpose = :purpose
              AND o.status = :status
            ORDER BY o.createdAt DESC
            LIMIT 1
            """)
    Optional<OtpEntity> findFirstActiveByUserIdAndPurpose(
            @Param("userId") UUID userId,
            @Param("purpose") OtpPurpose purpose,
            @Param("status") OtpStatus status
    );

    @Modifying
    @Query("""
            UPDATE OtpEntity o
            SET o.status = :toStatus
            WHERE o.userId = :userId
              AND o.purpose = :purpose
              AND o.status = :fromStatus
            """)
    void updateStatusByUserIdAndPurpose(
            @Param("userId") UUID userId,
            @Param("purpose") OtpPurpose purpose,
            @Param("fromStatus") OtpStatus fromStatus,
            @Param("toStatus") OtpStatus toStatus
    );

    Optional<OtpEntity> findFirstByUserIdOrderByCreatedAtDesc(UUID userId);

    @Query("""
            SELECT o FROM OtpEntity o
            WHERE o.userId = :userId
              AND o.purpose = :purpose
            ORDER BY o.createdAt DESC
            LIMIT 1
            """)
    Optional<OtpEntity> findFirstByUserIdAndPurposeOrderByCreatedAtDesc(
            @Param("userId") UUID userId,
            @Param("purpose") OtpPurpose purpose
    );

    @Query("SELECT COUNT(o) FROM OtpEntity o WHERE o.userId = :userId AND o.createdAt >= :timestamp")
    long countByUserIdAndCreatedAtAfter(@Param("userId") UUID userId, @Param("timestamp") java.time.OffsetDateTime timestamp);
}
