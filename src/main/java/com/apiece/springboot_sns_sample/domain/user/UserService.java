package com.apiece.springboot_sns_sample.domain.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apiece.springboot_sns_sample.controller.dto.UserCreateRequest;
import com.apiece.springboot_sns_sample.controller.dto.UserResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse signup(UserCreateRequest request) {
        validateDuplicateUsername(request.username());

        String encodedPassword = passwordEncoder.encode(request.password());
        User user = new User(request.username(), encodedPassword);
        User savedUser = userRepository.save(user);

        return UserResponse.from(savedUser);
    }

    private void validateDuplicateUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw UserException.duplicateUsername(username);
        }
    }
}
