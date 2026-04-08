package org.iesalixar.daw2.dam.ticket_logger_v2_players.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.iesalixar.daw2.dam.ticket_logger_v2_players.entities.Team;
import org.iesalixar.daw2.dam.ticket_logger_v2_players.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teams")
@Tag(name = "Team Controller", description = "Endpoints para la gestión de equipos")
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @Operation(summary = "Obtener todos los equipos")
    @GetMapping
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    @Operation(summary = "Obtener un equipo por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        return teamRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
