package com.pgv.restaurante.controller;

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
        return avatarRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avatar no encontrado"));
    }

    @PutMapping("/{id}")
    public Avatar actualizarAvatar(@PathVariable("id") Long id, @RequestBody Avatar detallesAvatar) {
        Avatar avatar = avatarRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avatar no encontrado"));
        
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
