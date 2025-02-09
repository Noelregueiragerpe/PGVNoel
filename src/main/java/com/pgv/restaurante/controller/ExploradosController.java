package com.pgv.restaurante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.pgv.restaurante.ResourceNotFoundException;
import com.pgv.restaurante.model.Explorado;
import com.pgv.restaurante.model.Usuario;
import com.pgv.restaurante.model.Lugar;
import com.pgv.restaurante.repository.ExploradoRepository;
import com.pgv.restaurante.repository.UsuarioRepository;
import com.pgv.restaurante.repository.LugarRepository;
import com.pgv.restaurante.model.ExploradoId;

import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/explorado")
public class ExploradosController {

    @Autowired
    private ExploradoRepository exploradoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LugarRepository lugarRepository;

    // Obtener todos los explorados
    @GetMapping
    public List<Explorado> obtenerTodosLosExplorados() {
        List<Explorado> explorados = exploradoRepository.findAll();
        return explorados.stream().map(explorado -> {
            Usuario usuario = usuarioRepository.findById(explorado.getId().getIdUsuario())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
            Lugar lugar = lugarRepository.findById(explorado.getId().getIdLugar())
                    .orElseThrow(() -> new ResourceNotFoundException("Lugar no encontrado"));

            explorado.setUsuario(usuario.getNombre());
            explorado.setLugar(lugar.getCiudad());
            return explorado;
        }).collect(Collectors.toList());
    }

    // Crear un nuevo explorado
    @PostMapping
    public Explorado crearExplorado(@RequestBody Explorado explorado) {
        return exploradoRepository.save(explorado);
    }

    // Editar un explorado
    @PutMapping("/{idUsuario}/{idLugar}")
    public Explorado editarExplorado(@PathVariable("idUsuario") Long idUsuario, 
                                     @PathVariable("idLugar") Long idLugar, 
                                     @RequestBody Explorado exploradoDetails) {
        ExploradoId exploradoId = new ExploradoId(idUsuario, idLugar);
        Explorado explorado = exploradoRepository.findById(exploradoId)
                .orElseThrow(() -> new ResourceNotFoundException("Explorado no encontrado"));

        explorado.setFavorito(exploradoDetails.isFavorito()); // Actualiza los campos necesarios
        return exploradoRepository.save(explorado);
    }

    // Eliminar un explorado
    @DeleteMapping("/{idUsuario}/{idLugar}")
    public void eliminarExplorado(@PathVariable("idUsuario") Long idUsuario, 
                                  @PathVariable("idLugar") Long idLugar) {
        ExploradoId exploradoId = new ExploradoId(idUsuario, idLugar);
        Explorado explorado = exploradoRepository.findById(exploradoId)
                .orElseThrow(() -> new ResourceNotFoundException("Explorado no encontrado"));

        exploradoRepository.delete(explorado);
    }
}
