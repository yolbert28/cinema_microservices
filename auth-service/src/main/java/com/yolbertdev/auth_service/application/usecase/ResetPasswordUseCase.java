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
        Otp otp = otpRepository
                .findActiveByUserIdAndPurpose(command.getUserId(), OtpPurpose.PASSWORD_RESET)
                .orElseThrow(InvalidOtpException::new);

        if (otp.isExpired() || !Objects.equals(otp.getCode(), command.getOtpCode())) {
            throw new InvalidOtpException();
        }

        otp.markAsUsed();
        otpRepository.save(otp);

        User user = userRepository.findById(command.getUserId())
                .orElseThrow(UserNotFoundException::new);

        user.changePassword(passwordEncoder.encode(command.getNewPassword()));
        userRepository.save(user);

        sessionRepository.revokeAllByUserId(user.getId());
    }
}
