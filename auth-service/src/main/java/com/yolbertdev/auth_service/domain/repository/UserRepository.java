package com.yolbertdev.auth_service.domain.repository;

import com.yolbertdev.auth_service.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    Optional<User> findById(UUID id);

    User save(User user);
}
