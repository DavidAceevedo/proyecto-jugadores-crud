package org.iesalixar.daw2.dam.ticket_logger_v2_players.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDTO {

    private Long id;

    @NotBlank(message = "{msg.player.validation.name}")
    @Size(max = 100)
    private String name;

    @Min(1)
    @Max(99)
    private Integer number;

    // Aquí guardamos solo el ID del equipo para el <select> del formulario
    @NotNull(message = "{msg.player.validation.team}")
    private Long teamId;

    // Para mostrar el nombre del equipo en el listado
    private String teamName;
}