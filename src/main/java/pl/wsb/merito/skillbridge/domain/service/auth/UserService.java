package pl.wsb.merito.skillbridge.domain.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.wsb.merito.skillbridge.adapter.database.user.UserRepository;
import pl.wsb.merito.skillbridge.domain.exception.EmailAlreadyExistsException;
import pl.wsb.merito.skillbridge.domain.model.Role;
import pl.wsb.merito.skillbridge.domain.model.User;
import pl.wsb.merito.skillbridge.rest.request.RegisterRequest;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest registerRequest) {
        log.debug("Registering user with username: {}", registerRequest.getEmail());
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            log.error("Email already exists: {}", registerRequest.getEmail());
            throw new EmailAlreadyExistsException("Email already exists");
        }
        User user = User.builder()
                .id(UUID.randomUUID())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .name(registerRequest.getName())
                .bio(registerRequest.getBio())
                .image_url(registerRequest.getImage_url())
                .role(Role.getRole(registerRequest.getRole()))
                .created_at(Instant.now().getEpochSecond())
                .build();

        userRepository.save(user.toEntity());
        log.debug("User registered successfully!");
    }
}
