package pe.edu.untels.certificadosdrsu.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pe.edu.untels.certificadosdrsu.dtos.UserRequest;
import pe.edu.untels.certificadosdrsu.dtos.UsuarioDTO;
import pe.edu.untels.certificadosdrsu.entities.Usuario;
import pe.edu.untels.certificadosdrsu.servicesinterface.IUsuarioService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private IUsuarioService uS;

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(uS.create(request));
    }

    @GetMapping
    public List<UsuarioDTO> listar() {
        return uS.list().stream().map(y -> {
            ModelMapper m = new ModelMapper();
            return m.map(y, UsuarioDTO.class);
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        ModelMapper m = new ModelMapper();
        Optional<Usuario> usuario = uS.listId(id);

        if (usuario.isPresent()) {
            UsuarioDTO dto = m.map(usuario.get(), UsuarioDTO.class);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody UsuarioDTO dto) {
        ModelMapper m = new ModelMapper();
        Optional<Usuario> existente = uS.listId(id);
        if (existente.isPresent()) {
            Usuario u = m.map(dto, Usuario.class);
            u.setId(id);
            Usuario actualizado = uS.update(u);
            UsuarioDTO responseDTO = m.map(actualizado, UsuarioDTO.class);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Usuario> existente = uS.listId(id);
        if (existente.isPresent()) {
            uS.delete(id);
            return ResponseEntity.ok("Usuario eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }
    }
}
