package pe.edu.untels.certificadosdrsu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.untels.certificadosdrsu.entities.Usuario;
import pe.edu.untels.certificadosdrsu.repositories.UsuarioRepository;

@RestController
@RequestMapping("/seed")
public class SeedController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/admin")
    public ResponseEntity<String> crearAdmin() {
        Usuario admin = new Usuario();
        admin.setUsername("admin");
        admin.setPasswordHash(passwordEncoder.encode("admin123"));
        admin.setRol("ADMIN");
        admin.setActivo(true);

        usuarioRepository.save(admin);
        return ResponseEntity.ok("Usuario admin creado");
    }
}