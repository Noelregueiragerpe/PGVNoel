package com.pgv.restaurante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pgv.restaurante.ResourceNotFoundException;
import com.pgv.restaurante.model.Usuario;
import com.pgv.restaurante.repository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000") // ✅ Permitir solicitudes desde el frontend
@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // ✅ OBTENER TODOS LOS USUARIOS
    @GetMapping
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    // ✅ REGISTRO DE USUARIO SIN PASSWORD ENCRYPT
    @PostMapping
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            // 🔑 Generamos el token
            String token = getJWTToken(usuario.getNombre());
            usuario.setToken(token);

            // 💾 Guardamos el usuario en la base de datos
            usuarioRepository.save(usuario);

            return ResponseEntity.ok("✅ Usuario registrado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("❌ Error al registrar usuario: " + e.getMessage());
        }
    }

    // ✅ OBTENER USUARIO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable("id") Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("⚠️ Usuario no encontrado"));
        return ResponseEntity.ok(usuario);
    }

    // ✅ ACTUALIZAR USUARIO SIN PASSWORD ENCRYPT
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable("id") Long id, @RequestBody Usuario detallesUsuario) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("⚠️ Usuario no encontrado"));

        usuario.setNombre(detallesUsuario.getNombre());
        usuario.setCorreo(detallesUsuario.getCorreo());
        usuario.setContrasena(detallesUsuario.getContrasena()); // ⚠️ Ahora la contraseña NO está encriptada

        return ResponseEntity.ok(usuarioRepository.save(usuario));
    }

    // ✅ ELIMINAR USUARIO
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable("id") Long id) {
        usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("⚠️ Usuario no encontrado"));

        usuarioRepository.deleteById(id);
        return ResponseEntity.ok("✅ Usuario eliminado correctamente");
    }

    // ✅ INICIO DE SESIÓN (LOGIN) SIN PASSWORD ENCRYPT
    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(
            @RequestParam String correo,
            @RequestParam String contrasena) {

        // Buscar usuario por correo
        Usuario usuario = usuarioRepository.findByCorreo(correo);

        if (usuario == null || !usuario.getContrasena().equals(contrasena)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }

        // Generar nuevo token
        String token = getJWTToken(usuario.getNombre());
        usuario.setToken(token);
        usuario.setFechaRegistro(new Date());
        usuario.setActivo(true);

        // Guardar usuario con el nuevo token
        usuarioRepository.save(usuario);

        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/logout/{id}")
    public ResponseEntity<Usuario> logoutUsuario(@PathVariable("id") Long id) {
        System.out.println(id);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        System.out.println(usuario.getNombre());
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuario);
    }

    // ✅ GENERACIÓN DE TOKEN JWT
    private String getJWTToken(String nombre) {
        String secretKey = "mySecretKeymySecretKeymySecretKeymySecretKeymySecretKeymySecretKeymySecretKey";
        
        String token = Jwts.builder()
                .setId("softtekJWT")
                .setSubject(nombre)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS512)
                .compact();

        return "Bearer " + token;
    }

    // ✅ CLASE AUXILIAR PARA DEVOLVER TOKEN EN JSON
    class AuthResponse {
        private String token;

        public AuthResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }
}
