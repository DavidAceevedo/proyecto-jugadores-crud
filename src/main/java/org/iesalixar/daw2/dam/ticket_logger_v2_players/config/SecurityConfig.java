package org.iesalixar.daw2.dam.ticket_logger_v2_players.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // OBLIGATORIO PARA APIS
                .authorizeHttpRequests(auth -> auth
                        // Permitimos acceso a la web de Swagger y a los docs JSON
                        .requestMatchers("/v3/api-elements/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated()
                )
                // Esto permite que Swagger mande las credenciales en la cabecera (Basic Auth)
                .httpBasic(Customizer.withDefaults())
                // Puedes mantener esto para entrar por web, pero Swagger priorizará lo de arriba
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Usamos BCrypt puro (fuerza por defecto) igual que en tu otro proyecto
        return new BCryptPasswordEncoder();
    }
}