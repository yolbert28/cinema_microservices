package com.yolbertdev.auth_service.application.usecase;

import com.yolbertdev.auth_service.application.dto.ResetPasswordCommand;
import com.yolbertdev.auth_service.application.exception.InvalidOtpException;
import com.yolbertdev.auth_service.application.exception.UserNotFoundException;
import com.yolbertdev.auth_service.application.port.PasswordEncoderPort;
import com.yolbertdev.auth_service.domain.enums.OtpPurpose;
import com.yolbertdev.auth_service.domain.model.Otp;
import com.yolbertdev.auth_service.domain.model.User;
import com.yolbertdev.auth_service.domain.repository.OtpRepository;
import com.yolbertdev.auth_service.domain.repository.SessionRepository;
import com.yolbertdev.auth_service.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResetPasswordUseCase {

    private final OtpRepository otpRepository;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final PasswordEncoderPort passwordEncoder;

    @Transactional
    public void execute(ResetPasswordCommand command) {

        User user = userRepository.findByEmail(command.getEmail()).orElseThrow(UserNotFoundException::new);

        Otp otp = otpRepository
                .findActiveByUserIdAndPurpose(user.getId(), OtpPurpose.PASSWORD_RESET)
                .orElseThrow(InvalidOtpException::new);

        if (otp.isExpired() || !Objects.equals(otp.getCode(), command.getOtpCode())) {
            throw new InvalidOtpException();
        }

        otp.markAsUsed();
        otpRepository.save(otp);

        user.changePassword(passwordEncoder.encode(command.getNewPassword()));
        userRepository.save(user);

        sessionRepository.revokeAllByUserId(user.getId());
    }
}
