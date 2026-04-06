package org.iesalixar.daw2.dam.ticket_logger_v2_players;

import org.iesalixar.daw2.dam.ticket_logger_v2_players.controllers.PlayerController;
import org.iesalixar.daw2.dam.ticket_logger_v2_players.services.PlayerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlayerController.class) // Solo carga la capa web y controladores
public class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerService playerService; // Simulamos el servicio para no tocar la BD real

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testListPlayersPage() throws Exception {
        // Simulamos que el servicio devuelve una lista vacía
        Mockito.when(playerService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/players"))
                .andExpect(status().isOk()) // Esperamos un 200 OK
                .andExpect(view().name("players/list")) // Verificamos la vista
                .andExpect(model().attributeExists("players")); // Verificamos que se envía la lista
    }

    @Test
    public void testAccessDeniedForAnonymous() throws Exception {
        // Si no estamos logueados, Spring Security redirige por defecto (302 Found)
        // OJO: El código de redirección estándar es 302, no 322.
        mockMvc.perform(get("/players"))
                .andExpect(status().is3xxRedirection());
    }
}