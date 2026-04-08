package org.iesalixar.daw2.dam.ticket_logger_v2_players.services;

import org.iesalixar.daw2.dam.ticket_logger_v2_players.entities.User;
import org.iesalixar.daw2.dam.ticket_logger_v2_players.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscamos tu entidad y la devolvemos directamente.
        // Spring llamará al getAuthorities() que acabamos de arreglar arriba.
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }
}