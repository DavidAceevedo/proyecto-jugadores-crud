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

@RestController // Cambiado: Ahora devuelve datos JSON directamente
@RequestMapping("/api/v1/players") // Ruta estándar de API
@Tag(name = "Player Controller", description = "Endpoints para la gestión de jugadores mediante API REST")
public class PlayerController {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    // LISTADO
    @Operation(summary = "Obtener todos los jugadores", description = "Retorna una lista de PlayerDTO")
    @GetMapping
    public List<PlayerDTO> listPlayers() {
        return playerRepository.findAll().stream()
                .map(PlayerMapper::toDTO)
                .collect(Collectors.toList());
    }

    // OBTENER UNO
    @Operation(summary = "Obtener un jugador por ID")
    @GetMapping("/{id}")
    public ResponseEntity<PlayerDTO> getPlayerById(@PathVariable Long id) {
        return playerRepository.findById(id)
                .map(player -> ResponseEntity.ok(PlayerMapper.toDTO(player)))
                .orElse(ResponseEntity.notFound().build());
    }

    // GUARDAR (INSERT / UPDATE)
    @Operation(summary = "Guardar o actualizar un jugador")
    @PostMapping
    public ResponseEntity<?> savePlayer(@Valid @RequestBody PlayerDTO playerDTO) {
        try {
            Team team = teamRepository.findById(playerDTO.getTeamId())
                    .orElseThrow(() -> new IllegalArgumentException("ID de equipo no válido: " + playerDTO.getTeamId()));

            Player player = PlayerMapper.toEntity(playerDTO, team);
            Player savedPlayer = playerRepository.save(player);

            return ResponseEntity.status(HttpStatus.CREATED).body(PlayerMapper.toDTO(savedPlayer));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al procesar la solicitud: " + e.getMessage());
        }
    }

    // ELIMINAR
    @Operation(summary = "Eliminar un jugador por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        if (!playerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        playerRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // Devuelve un 204 No Content (éxito sin cuerpo)
    }
}