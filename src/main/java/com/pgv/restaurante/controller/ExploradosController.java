package com.pgv.restaurante.controller;

import com.pgv.restaurante.model.*;
import com.pgv.restaurante.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pgv.restaurante.ResourceNotFoundException;
import com.pgv.restaurante.repository.ExploradoRepository;
import com.pgv.restaurante.repository.UsuarioRepository;
import com.pgv.restaurante.repository.LugarRepository;

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

    @Autowired NotificacionRepository notificacionRepository;;

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

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Explorado>> obtenerExploradosPorUsuario(@PathVariable Long idUsuario) {
        List<Explorado> explorados = exploradoRepository.findByIdIdUsuario(idUsuario);

        List<Explorado> exploradosConDatos = explorados.stream().map(explorado -> {
            Lugar lugar = lugarRepository.findById(explorado.getId().getIdLugar())
                    .orElseThrow(() -> new ResourceNotFoundException("Lugar no encontrado"));
            explorado.setLugarEntidad(lugar);
            return explorado;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(exploradosConDatos);
    }


    @PostMapping
    public ResponseEntity<?> crearExplorado(@RequestBody Explorado explorado) {
        Long idUsuario = explorado.getId().getIdUsuario();
        Long idLugar = explorado.getId().getIdLugar();

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Lugar lugar = lugarRepository.findById(idLugar)
                .orElseThrow(() -> new RuntimeException("Lugar no encontrado"));

        explorado.setUsuarioEntidad(usuario);
        explorado.setLugarEntidad(lugar);

        exploradoRepository.save(explorado);

        // Crear y guardar la notificación
        Notificacion notificacion = new Notificacion();
        notificacion.setUsuario(usuario);
        notificacion.setMensaje("Has visitado " + lugar.getNombre());
        notificacionRepository.save(notificacion); // 👈 Faltaba esto

        return ResponseEntity.ok("Explorado creado correctamente");
    }

    @PutMapping("/{idUsuario}/{idLugar}")
    public Explorado editarExplorado(@PathVariable("idUsuario") Long idUsuario, 
                                     @PathVariable("idLugar") Long idLugar, 
                                     @RequestBody Explorado exploradoDetails) {
        ExploradoId exploradoId = new ExploradoId(idUsuario, idLugar);
        Explorado explorado = exploradoRepository.findById(exploradoId)
                .orElseThrow(() -> new ResourceNotFoundException("Explorado no encontrado"));

        explorado.setFavorito(exploradoDetails.isFavorito()); 
        return exploradoRepository.save(explorado);
    }

    @DeleteMapping("/{idUsuario}/{idLugar}")
    public void eliminarExplorado(@PathVariable("idUsuario") Long idUsuario, 
                                  @PathVariable("idLugar") Long idLugar) {
        ExploradoId exploradoId = new ExploradoId(idUsuario, idLugar);
        Explorado explorado = exploradoRepository.findById(exploradoId)
                .orElseThrow(() -> new ResourceNotFoundException("Explorado no encontrado"));

        exploradoRepository.delete(explorado);
    }
}
