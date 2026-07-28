package com.yolbertdev.auth_service.infrastructure.persistence.adapter;

import com.yolbertdev.auth_service.domain.model.User;
import com.yolbertdev.auth_service.domain.repository.UserRepository;
import com.yolbertdev.auth_service.infrastructure.persistence.jpa.JpaUserRepository;
import com.yolbertdev.auth_service.infrastructure.persistence.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final JpaUserRepository jpa;

    @Override
    public Optional<User> findByEmail(String email) {
        return jpa.findByEmail(email).map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpa.findById(id).map(UserMapper::toDomain);
    }

    @Override
    public User save(User user) {
        return UserMapper.toDomain(jpa.save(UserMapper.toEntity(user)));
    }
}
