package org.iesalixar.daw2.dam.ticket_logger_v2_players.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.iesalixar.daw2.dam.ticket_logger_v2_players.dto.AuthRequest;
import org.iesalixar.daw2.dam.ticket_logger_v2_players.services.JwtService; // Cambiado de JwtUtil a JwtService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "Endpoint para obtener el token JWT")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService; // Cambiado de JwtUtil a JwtService

    @Autowired
    private UserDetailsService userDetailsService;

    @Operation(summary = "Login para obtener el token")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        // 1. Validar usuario y contraseña en la BD
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        // 2. Cargar detalles del usuario
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());

        // 3. Generar el token usando el servicio de llaves RSA
        String token = jwtService.generateToken(userDetails);

        // 4. Devolverlo en un mapa para que salga como {"token": "xxxx"}
        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return ResponseEntity.ok(response);
    }
}