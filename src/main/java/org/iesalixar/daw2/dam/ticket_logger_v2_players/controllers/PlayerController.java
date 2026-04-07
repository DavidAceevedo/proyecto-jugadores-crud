package org.iesalixar.daw2.dam.ticket_logger_v2_players.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.iesalixar.daw2.dam.ticket_logger_v2_players.dto.PlayerDTO;
import org.iesalixar.daw2.dam.ticket_logger_v2_players.entities.Player;
import org.iesalixar.daw2.dam.ticket_logger_v2_players.entities.Team;
import org.iesalixar.daw2.dam.ticket_logger_v2_players.mappers.PlayerMapper;
import org.iesalixar.daw2.dam.ticket_logger_v2_players.repositories.PlayerRepository;
import org.iesalixar.daw2.dam.ticket_logger_v2_players.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController // SUSTITUYE A @Controller
@RequestMapping("/api/v1/players")
@Tag(name = "PlayerController", description = "API REST de Jugadores") // Swagger
public class PlayerController {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    // LISTADO (GET)
    @Operation(summary = "Listar todos los jugadores")
    @GetMapping
    public List<PlayerDTO> listPlayers() {
        return playerRepository.findAll().stream()
                .map(PlayerMapper::toDTO)
                .collect(Collectors.toList());
    }

    // OBTENER UNO (GET)
    @Operation(summary = "Obtener un jugador por ID")
    @GetMapping("/{id}")
    public ResponseEntity<PlayerDTO> getPlayer(@PathVariable Long id) {
        return playerRepository.findById(id)
                .map(player -> ResponseEntity.ok(PlayerMapper.toDTO(player)))
                .orElse(ResponseEntity.notFound().build());
    }

    // GUARDAR (POST) - Combinamos Insert y Update en un solo endpoint REST
    @Operation(summary = "Insertar o actualizar un jugador")
    @PostMapping("/save")
    public ResponseEntity<?> savePlayer(@Valid @RequestBody PlayerDTO playerDTO) {
        try {
            Team team = teamRepository.findById(playerDTO.getTeamId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid team Id:" + playerDTO.getTeamId()));

            Player player = PlayerMapper.toEntity(playerDTO, team);
            // Si el DTO trae ID, Hibernate hará un Update; si no, un Insert.
            Player savedPlayer = playerRepository.save(player);

            return ResponseEntity.status(HttpStatus.CREATED).body(PlayerMapper.toDTO(savedPlayer));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error en el guardado: " + e.getMessage());
        }
    }

    // ELIMINAR (DELETE)
    @Operation(summary = "Eliminar un jugador")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        if (!playerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        playerRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}