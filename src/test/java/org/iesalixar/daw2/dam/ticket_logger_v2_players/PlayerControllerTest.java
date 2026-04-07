package org.iesalixar.daw2.dam.ticket_logger_v2_players;

import org.iesalixar.daw2.dam.ticket_logger_v2_players.controllers.PlayerController;
import org.iesalixar.daw2.dam.ticket_logger_v2_players.repositories.PlayerRepository;
import org.iesalixar.daw2.dam.ticket_logger_v2_players.repositories.TeamRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlayerController.class) // Solo carga el controlador de jugadores
public class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // IMPORTANTE: En Spring Boot 3.4+, @MockBean se ha cambiado por @MockitoBean
    // Estos "simulan" los repositorios que el controlador necesita para arrancar
    @MockitoBean
    private PlayerRepository playerRepository;

    @MockitoBean
    private TeamRepository teamRepository;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"}) // Simula que estás logueado
    public void testShowPlayersPage() throws Exception {
        mockMvc.perform(get("/players"))
                .andExpect(status().isOk())
                .andExpect(view().name("players/list"));    }
}