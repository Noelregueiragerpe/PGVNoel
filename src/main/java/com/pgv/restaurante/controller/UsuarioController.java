package com.pgv.restaurante.controller;

import com.pgv.restaurante.model.Avatar;
import com.pgv.restaurante.repository.AvatarRepository;
import com.pgv.restaurante.repository.CabezasRepository;
import com.pgv.restaurante.repository.CuerposRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pgv.restaurante.ResourceNotFoundException;
import com.pgv.restaurante.model.Usuario;
import com.pgv.restaurante.repository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private static final String SECRET_KEY = "mySecretKeymySecretKeymySecretKeymySecretKeymySecretKeymySecretKeymySecretKeymySecretKeymySecretKeymySecretKeymySecretKey";

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$");
    @Autowired
    private CabezasRepository cabezasRepository;
    @Autowired
    private CuerposRepository cuerposRepository;
    @Autowired
    private AvatarRepository avatarRepository;

    @GetMapping
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        try {

            if (!EMAIL_PATTERN.matcher(usuario.getCorreo()).matches()) {
                return ResponseEntity.badRequest().body("❌ El correo electrónico no es válido");
            }

            if (usuarioRepository.findByCorreo(usuario.getCorreo()) != null) {
                return ResponseEntity.badRequest().body("❌ El correo ya está registrado");
            }

            if (!PASSWORD_PATTERN.matcher(usuario.getContrasena()).matches()) {
                return ResponseEntity.badRequest().body("❌ La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula y un número");
            }
            Avatar avatar = new Avatar();
            avatar.setCabeza(cabezasRepository.findById((long)1).orElseThrow(() -> new ResourceNotFoundException("⚠️ Cabeza no encontrada")));
            avatar.setCuerpo(cuerposRepository.findById((long)1).orElseThrow(() -> new ResourceNotFoundException("⚠️ Cuerpo no encontrado")));
            avatar.setUsuario(usuario);
            usuarioRepository.save(usuario);
            avatarRepository.save(avatar);
            return ResponseEntity.ok("✅ Usuario registrado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("❌ Error al registrar usuario: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable("id") Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("⚠️ Usuario no encontrado"));
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable("id") Long id, @RequestBody Usuario detallesUsuario) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("⚠️ Usuario no encontrado"));
        usuario.setNombre(detallesUsuario.getNombre());
        usuario.setCorreo(detallesUsuario.getCorreo());
        usuario.setContrasena(detallesUsuario.getContrasena());

        return ResponseEntity.ok(usuarioRepository.save(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable("id") Long id) {
        usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("⚠️ Usuario no encontrado"));

        usuarioRepository.deleteById(id);
        return ResponseEntity.ok("✅ Usuario eliminado correctamente");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String correo, @RequestParam String contrasena) {
        if (!EMAIL_PATTERN.matcher(correo).matches()) {
            return ResponseEntity.badRequest().body("❌ El correo electrónico no es válido");
        }

        Usuario usuarioExistente = usuarioRepository.findByCorreo(correo);

        if (usuarioExistente == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ Correo no registrado");
        }

        if (!PASSWORD_PATTERN.matcher(contrasena).matches()) {
            return ResponseEntity.badRequest().body("❌ La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula y un número");
        }

        if (!contrasena.equals(usuarioExistente.getContrasena())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ Contraseña incorrecta");
        }

        String token = getJWTToken(usuarioExistente.getNombre());
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("id", usuarioExistente.getId());
        response.put("nombre", usuarioExistente.getNombre());
        return ResponseEntity.ok(response);
    }


    private String getJWTToken(String nombre) {
        List<String> roles = List.of("ROLE_USER"); 
        String token = Jwts.builder()
                .setId("softtekJWT")
                .setSubject(nombre)
                .claim("authorities", roles)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS512)
                .compact();

        return "Bearer " + token;
    }

    @PutMapping("/logout/{id}")
    public ResponseEntity<Usuario> logoutUsuario(@PathVariable("id") Long id) {
        System.out.println(id);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        System.out.println(usuario.getNombre());
        usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuario);
    }

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
