package org.iesalixar.daw2.dam.ticket_logger_v2_players.mappers;

import org.iesalixar.daw2.dam.ticket_logger_v2_players.dto.PlayerDTO;
import org.iesalixar.daw2.dam.ticket_logger_v2_players.entities.Player;
import org.iesalixar.daw2.dam.ticket_logger_v2_players.entities.Team;

public class PlayerMapper {

    public static PlayerDTO toDTO(Player player) {
        PlayerDTO dto = new PlayerDTO();
        dto.setId(player.getId());
        dto.setName(player.getName());
        dto.setNumber(player.getNumber());
        if (player.getTeam() != null) {
            dto.setTeamId(player.getTeam().getId());
            dto.setTeamName(player.getTeam().getName());
        }
        return dto;
    }

    public static Player toEntity(PlayerDTO dto, Team team) {
        Player player = new Player();
        player.setId(dto.getId());
        player.setName(dto.getName());
        player.setNumber(dto.getNumber());
        player.setTeam(team);
        return player;
    }
}