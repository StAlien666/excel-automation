package ru.alexandr.excel_automation.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.alexandr.excel_automation.entity.UserEntity;
import ru.alexandr.excel_automation.entity.UserRole;
import ru.alexandr.excel_automation.repository.UserRepository;

// при первом старте создаёт админа

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.username:admin}")
    private String username;

    @Value("${app.admin.password:admin}")
    private String password;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.count() == 0) {
            userRepository.save(UserEntity.builder()
                    .username(username)
                    .passwordHash(passwordEncoder.encode(password))
                    .role(UserRole.ADMIN)
                    .isActive(true)
                    .build());
            log.warn("Создан администратор '{}' с паролем по умолчанию. Сменить в БД", username);
        }
    }
}
