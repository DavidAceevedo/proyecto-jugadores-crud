package org.iesalixar.daw2.dam.ticket_logger_v2_players.repositories;

import org.iesalixar.daw2.dam.ticket_logger_v2_players.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
}