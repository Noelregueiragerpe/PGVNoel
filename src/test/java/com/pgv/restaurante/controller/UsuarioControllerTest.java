package com.pgv.restaurante.controller;

import com.pgv.restaurante.model.Usuario;
import com.pgv.restaurante.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class NotificacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @LocalServerPort
    private int port;

    private Usuario usuario1;
    private String token; 

    @BeforeEach
    void setUp() throws Exception {
        usuarioRepository.deleteAll();
        usuario1 = new Usuario();
        usuario1.setNombre("Usuario Prueba");
        usuario1.setCorreo("usuario.valido@example.com"); 
        usuario1.setContrasena("ContraseñaSegura123"); 
        usuarioRepository.save(usuario1);

        token = obtenerToken(usuario1.getCorreo(), usuario1.getContrasena());
    }

    private String obtenerToken(String correo, String contrasena) throws Exception {
        String loginRequestBody = "{\"correo\":\"" + correo + "\",\"contrasena\":\"" + contrasena + "\"}";

        String response = mockMvc.perform(post("/api/usuario/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginRequestBody))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

       return response.split(":\"")[1].split("\"}")[0];
    }

    @Test
    void obtenerUsuarioPorId_shouldReturnUsuario() throws Exception {
        mockMvc.perform(get("/api/usuario/" + usuario1.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token) 
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value(usuario1.getNombre()))
                .andExpect(jsonPath("$.correo").value(usuario1.getCorreo()));
    }
}
