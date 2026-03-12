package org.iesalixar.daw2.dam.ticket_logger_v2_players.controllers;

import jakarta.validation.Valid;
import org.iesalixar.daw2.dam.ticket_logger_v2_players.dto.PlayerDTO;
import org.iesalixar.daw2.dam.ticket_logger_v2_players.entities.Player;
import org.iesalixar.daw2.dam.ticket_logger_v2_players.entities.Team;
import org.iesalixar.daw2.dam.ticket_logger_v2_players.mappers.PlayerMapper;
import org.iesalixar.daw2.dam.ticket_logger_v2_players.repositories.PlayerRepository;
import org.iesalixar.daw2.dam.ticket_logger_v2_players.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    // LISTADO
    @GetMapping
    public String listPlayers(Model model) {
        List<PlayerDTO> players = playerRepository.findAll().stream()
                .map(PlayerMapper::toDTO)
                .collect(Collectors.toList());
        model.addAttribute("players", players);
        return "players/list";
    }

    // FORMULARIO NUEVO
    @GetMapping("/new")
    public String showNewForm(Model model) {
        PlayerDTO playerDTO = new PlayerDTO();
        model.addAttribute("playerDTO", playerDTO);
        model.addAttribute("teams", teamRepository.findAll());
        return "players/form";
    }

    // FORMULARIO EDITAR
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid player Id:" + id));
        model.addAttribute("playerDTO", PlayerMapper.toDTO(player));
        model.addAttribute("teams", teamRepository.findAll());
        return "players/form";
    }

    // GUARDAR (INSERT/UPDATE)
    @PostMapping("/save")
    public String savePlayer(@Valid @ModelAttribute("playerDTO") PlayerDTO playerDTO,
                             BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("teams", teamRepository.findAll());
            return "players/form";
        }

        Team team = teamRepository.findById(playerDTO.getTeamId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid team Id:" + playerDTO.getTeamId()));

        Player player = PlayerMapper.toEntity(playerDTO, team);
        playerRepository.save(player);

        redirectAttributes.addFlashAttribute("success", "Operación realizada con éxito");
        return "redirect:/players";
    }

    // ELIMINAR
    @GetMapping("/delete/{id}")
    public String deletePlayer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        playerRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Jugador eliminado con éxito");
        return "redirect:/players";
    }
}