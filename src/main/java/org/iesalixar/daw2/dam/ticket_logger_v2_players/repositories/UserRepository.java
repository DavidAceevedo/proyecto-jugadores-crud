package org.iesalixar.daw2.dam.ticket_logger_v2_players.repositories;

import org.iesalixar.daw2.dam.ticket_logger_v2_players.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}