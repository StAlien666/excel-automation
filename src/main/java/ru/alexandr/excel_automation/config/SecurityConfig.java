package ru.alexandr.excel_automation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // разрешает @PreAuthorize на методах контроллеров
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(reg -> reg
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/login", "/css/**", "/js/**", "/favicon.ico", "/error").permitAll()
                        // публично- матрица, справочники, экспорт, скачивание файлов
                        .requestMatchers(HttpMethod.GET, "/**").permitAll()
                        // все остальное только после входа
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")          // своя страница вместо стандартной
                        .defaultSuccessUrl("/", false) //не перебивать целевой URL после редиректа
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/"));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
