package com.pgv.restaurante.controller;

import com.pgv.restaurante.model.Cabeza;
import com.pgv.restaurante.model.Cuerpo;
import com.pgv.restaurante.repository.CabezasRepository;
import com.pgv.restaurante.repository.CuerposRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.pgv.restaurante.ResourceNotFoundException;
import com.pgv.restaurante.model.Avatar;
import com.pgv.restaurante.repository.AvatarRepository;

import java.util.List;

@CrossOrigin(origins = "*") 
@RestController
@RequestMapping("/api/avatar")
public class AvatarController {

    @Autowired
    private AvatarRepository avatarRepository;
    @Autowired
    private CabezasRepository cabezasRepository;
    @Autowired
    private CuerposRepository cuerposRepository;

    @GetMapping
    public List<Avatar> obtenerTodosLosAvatares() {
        return avatarRepository.findAll();
    }

    @PostMapping
    public Avatar crearAvatar(@RequestBody Avatar avatar) {
        return avatarRepository.save(avatar);
    }

    @GetMapping("/{id}")
    public Avatar obtenerAvatarPorId(@PathVariable("id") Long id) {
        return avatarRepository.findByUsuarioId(id);
    }

    @PutMapping("/{id}")
    public Avatar actualizarAvatar(@PathVariable("id") Long id, @RequestParam long idCabeza, @RequestParam long idCuerpo) {
        Avatar avatar = avatarRepository.findByUsuarioId(id);
        Cabeza nuevaCabeza = cabezasRepository.findById(idCabeza).orElseThrow(() -> new ResourceNotFoundException("⚠️ Cabeza no encontrada"));
        Cuerpo nuevoCuerpo = cuerposRepository.findById(idCuerpo).orElseThrow(() -> new ResourceNotFoundException("⚠️ Cuerpo no encontrado"));
        avatar.setCabeza(nuevaCabeza);
        avatar.setCuerpo(nuevoCuerpo);
        return avatarRepository.save(avatar);
    }

    @DeleteMapping("/{id}")
    public Avatar eliminarAvatar(@PathVariable("id") Long id) {
        Avatar avatar = avatarRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avatar no encontrado"));
        avatarRepository.deleteById(id);
        return avatar;
    }
}
