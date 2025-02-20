package com.pgv.restaurante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.pgv.restaurante.ResourceNotFoundException;
import com.pgv.restaurante.model.Lugar;
import com.pgv.restaurante.repository.LugarRepository;

import java.util.List;

@CrossOrigin(origins = "*") 
@RestController
@RequestMapping("/api/lugar")
public class LugarController {

    @Autowired
    private LugarRepository lugarRepository;

    @GetMapping
    public List<Lugar> obtenerTodosLosLugares() {
        return lugarRepository.findAll();
    }

    @PostMapping
    public Lugar crearLugar(@RequestBody Lugar lugar) {
        return lugarRepository.save(lugar);
    }

    @GetMapping("/{id}")
    public Lugar obtenerLugarPorId(@PathVariable("id") Long id) {
        return lugarRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lugar no encontrado"));
    }

    @PutMapping("/{id}")
    public Lugar actualizarLugar(@PathVariable("id") Long id, @RequestBody Lugar detallesLugar) {
        Lugar lugar = lugarRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lugar no encontrado"));

        lugar.setNombre(detallesLugar.getNombre());
        lugar.setCiudad(detallesLugar.getCiudad());
        lugar.setCoordenadasx(detallesLugar.getCoordenadasx());
        lugar.setCoordenadasy(detallesLugar.getCoordenadasy());
        lugar.setCategoria(detallesLugar.getCategoria());
        return lugarRepository.save(lugar);
    }

    @DeleteMapping("/{id}")
    public Lugar eliminarLugar(@PathVariable("id") Long id) {
        Lugar lugar = lugarRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lugar no encontrado"));
        lugarRepository.deleteById(id);
        return lugar;
    }
}
