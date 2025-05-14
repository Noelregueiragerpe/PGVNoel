
package com.pgv.restaurante.controller;

import com.pgv.restaurante.model.Cuerpo;
import com.pgv.restaurante.repository.CuerposRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/cuerpos")
public class CuerposController {

    @Autowired
    private CuerposRepository cuerposRepository;

    @GetMapping
    public ResponseEntity<List<Cuerpo>> obtenerTodasLasCuerpos() {
        List<Cuerpo> cuerpos = cuerposRepository.findAll();
        return ResponseEntity.ok(cuerpos);
    }

    @PostMapping
    public ResponseEntity<Cuerpo> crearCuerpos(@RequestBody Cuerpo cuerpos) {
        Cuerpo nuevoCuerpos = cuerposRepository.save(cuerpos);
        return ResponseEntity.ok(nuevoCuerpos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuerpo> actualizarCuerpos(@PathVariable Long id, @RequestBody Cuerpo detallesCuerpos) {
        Cuerpo cuerpos = cuerposRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuerpo no encontrados con id: " + id));
        cuerpos.setNombre(detallesCuerpos.getNombre());
        cuerpos.setCodigo(detallesCuerpos.getCodigo());
        
        Cuerpo cuerposActualizada = cuerposRepository.save(cuerpos);
        return ResponseEntity.ok(cuerposActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCuerpos(@PathVariable Long id) {
        Cuerpo cuerpos = cuerposRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuerpo no encontrado con id: " + id));
        cuerposRepository.delete(cuerpos);
        return ResponseEntity.ok().build();
    }
}
