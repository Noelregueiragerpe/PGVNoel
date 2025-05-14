
package com.pgv.restaurante.controller;

import com.pgv.restaurante.model.Cabeza;
import com.pgv.restaurante.repository.CabezasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/cabezas")
public class CabezasController {

    @Autowired
    private CabezasRepository cabezasRepository;

    @GetMapping
    public ResponseEntity<List<Cabeza>> obtenerTodasLasCabezas() {
        List<Cabeza> cabezas = cabezasRepository.findAll();
        return ResponseEntity.ok(cabezas);
    }

    @PostMapping
    public ResponseEntity<Cabeza> crearCabezas(@RequestBody Cabeza cabezas) {
        Cabeza nuevaCabezas = cabezasRepository.save(cabezas);
        return ResponseEntity.ok(nuevaCabezas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cabeza> actualizarCabezas(@PathVariable Long id, @RequestBody Cabeza detallesCabezas) {
        Cabeza cabezas = cabezasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cabeza no encontrados con id: " + id));
        cabezas.setNombre(detallesCabezas.getNombre());
        cabezas.setCodigo(detallesCabezas.getCodigo());
        Cabeza cabezasActualizada = cabezasRepository.save(cabezas);
        return ResponseEntity.ok(cabezasActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCabezas(@PathVariable Long id) {
        Cabeza cabezas = cabezasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cabeza no encontrado con id: " + id));
        cabezasRepository.delete(cabezas);
        return ResponseEntity.ok().build();
    }
}
