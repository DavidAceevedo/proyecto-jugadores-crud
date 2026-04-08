package org.iesalixar.daw2.dam.ticket_logger_v2_players.controllers;

import org.iesalixar.daw2.dam.ticket_logger_v2_players.entities.Team;
import org.iesalixar.daw2.dam.ticket_logger_v2_players.repositories.PlayerRepository;
import org.iesalixar.daw2.dam.ticket_logger_v2_players.repositories.TeamRepository;
import org.iesalixar.daw2.dam.ticket_logger_v2_players.mappers.PlayerMapper;
import org.iesalixar.daw2.dam.ticket_logger_v2_players.dto.PlayerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/players")
public class PlayerWebController {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    @GetMapping
    public String listPlayers(Model model) {
        model.addAttribute("players", playerRepository.findAll().stream()
                .map(PlayerMapper::toDTO)
                .collect(Collectors.toList()));

        // CORRECCIÓN: "players/list" (en plural, como tu carpeta)
        return "players/list";
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("playerDTO", new PlayerDTO());
        model.addAttribute("teams", teamRepository.findAll());

        // CORRECCIÓN: "players/form"
        return "players/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        var player = playerRepository.findById(id).orElseThrow();
        model.addAttribute("playerDTO", PlayerMapper.toDTO(player));
        model.addAttribute("teams", teamRepository.findAll());

        // CORRECCIÓN: "players/form"
        return "players/form";
    }

    @GetMapping("/delete/{id}")
    public String deletePlayer(@PathVariable Long id) {
        playerRepository.deleteById(id);
        return "redirect:/players";
    }

    // Este método es el que recibe los datos del formulario (POST)
    @PostMapping("/save")
    public String savePlayer(@ModelAttribute("playerDTO") PlayerDTO playerDTO) {

        // 1. Buscamos el equipo en la base de datos usando el ID del DTO
        Team team = teamRepository.findById(playerDTO.getTeamId())
                .orElseThrow(() -> new IllegalArgumentException("ID de equipo no válido"));

        // 2. Convertimos el DTO a Entidad y guardamos
        playerRepository.save(PlayerMapper.toEntity(playerDTO, team));

        // 3. Redirigimos a la lista para ver el cambio
        return "redirect:/players";
    }
}