package com.yolbertdev.auth_service.application.usecase;

import com.yolbertdev.auth_service.application.dto.UpdateRoleCommand;
import com.yolbertdev.auth_service.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateRoleUseCase {

    private final UserRepository userRepository;

    @Transactional
    public void execute(UUID id, UpdateRoleCommand command) {
        userRepository.updateRoleById(id,command.getRole());
    }
}
