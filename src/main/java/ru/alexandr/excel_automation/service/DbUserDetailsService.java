package ru.alexandr.excel_automation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.alexandr.excel_automation.entity.UserEntity;
import ru.alexandr.excel_automation.repository.UserRepository;

// мост между таблицей users и спринг секьюрити
@Service
@RequiredArgsConstructor
public class DbUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity u = userRepository.findByUsernameAndIsActiveTrue(username)
                .orElseThrow(() -> new UsernameNotFoundException("Нет такого пользователя: " + username));

        // ROLE_ префикс
        return User.withUsername(u.getUsername())
                .password(u.getPasswordHash())
                .authorities("ROLE_" + u.getRole().name())
                .build();
    }
}