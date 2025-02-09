package com.pgv.restaurante.controller;

import com.pgv.restaurante.model.Notificacion;
import com.pgv.restaurante.model.Usuario;
import com.pgv.restaurante.repository.NotificacionRepository;
import com.pgv.restaurante.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionRepository notificacionRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @GetMapping
    public ResponseEntity<List<Notificacion>> obtenerTodasLasNotificaciones() {
        List<Notificacion> notificaciones = notificacionRepository.findAll();
        return ResponseEntity.ok(notificaciones);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Notificacion>> obtenerNotificacionesPorUsuario(@PathVariable Long usuarioId) {
        List<Notificacion> notificaciones = notificacionRepository.findByUsuarioId(usuarioId);
        return ResponseEntity.ok(notificaciones);
    }

    @PostMapping
    public ResponseEntity<Notificacion> crearNotificacion(@RequestBody Map<String, Object> datos) {
        try {
            // Extraer los valores del JSON
            String mensaje = (String) datos.get("mensaje");
            String tipo = (String) datos.get("tipo");
            Long usuarioId = datos.get("usuario_id") != null ? Long.valueOf(datos.get("usuario_id").toString()) : null;
    
            // Crear la notificación
            Notificacion notificacion = new Notificacion();
            notificacion.setMensaje(mensaje);
            notificacion.setTipo(tipo);
    
            // Asociar el usuario si el ID está presente
            if (usuarioId != null) {
                Usuario usuario = usuarioRepository.findById(usuarioId)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + usuarioId));
                notificacion.setUsuario(usuario);
            }
    
            // Guardar la notificación
            Notificacion nuevaNotificacion = notificacionRepository.save(notificacion);
            return ResponseEntity.ok(nuevaNotificacion);
    
        } catch (Exception e) {
            throw new RuntimeException("Error al crear la notificación: " + e.getMessage());
        }
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<Notificacion> actualizarNotificacion(@PathVariable Long id, 
                                                             @RequestBody Notificacion detallesNotificacion) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada con id: " + id));

        try {
            notificacion.setMensaje(detallesNotificacion.getMensaje());
            notificacion.setTipo(detallesNotificacion.getTipo());
            notificacion.setUsuario(detallesNotificacion.getUsuario());
            
            Notificacion notificacionActualizada = notificacionRepository.save(notificacion);
            return ResponseEntity.ok(notificacionActualizada);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la notificación: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarNotificacion(@PathVariable Long id) {
        try {
            Notificacion notificacion = notificacionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Notificación no encontrada con id: " + id));

            notificacionRepository.delete(notificacion);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la notificación: " + e.getMessage());
        }
    }
}