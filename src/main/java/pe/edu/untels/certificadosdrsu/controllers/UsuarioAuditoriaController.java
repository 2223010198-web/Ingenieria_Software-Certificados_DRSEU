package pe.edu.untels.certificadosdrsu.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.certificadosdrsu.dtos.UsuarioAuditoriaDTO;
import pe.edu.untels.certificadosdrsu.entities.UsuarioAuditoria;
import pe.edu.untels.certificadosdrsu.servicesinterface.IUsuarioAuditoriaService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuario-auditoria")
public class UsuarioAuditoriaController {

    @Autowired
    private IUsuarioAuditoriaService service;

    @GetMapping
    public ResponseEntity<List<UsuarioAuditoriaDTO>> listar() {
        ModelMapper m = new ModelMapper();
        List<UsuarioAuditoriaDTO> lista = service.list().stream()
                .map(u -> m.map(u, UsuarioAuditoriaDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<UsuarioAuditoriaDTO> registrar(@RequestBody UsuarioAuditoriaDTO dto) {
        ModelMapper m = new ModelMapper();
        UsuarioAuditoria u = m.map(dto, UsuarioAuditoria.class);
        UsuarioAuditoria guardado = service.insert(u);
        UsuarioAuditoriaDTO responseDTO = m.map(guardado, UsuarioAuditoriaDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        ModelMapper m = new ModelMapper();
        Optional<UsuarioAuditoria> entidad = service.listId(id);
        
        if (entidad.isPresent()) {
            UsuarioAuditoriaDTO dto = m.map(entidad.get(), UsuarioAuditoriaDTO.class);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Auditoria de usuario no encontrada");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody UsuarioAuditoriaDTO dto) {
        ModelMapper m = new ModelMapper();
        Optional<UsuarioAuditoria> existente = service.listId(id);
        
        if (existente.isPresent()) {
            UsuarioAuditoria u = m.map(dto, UsuarioAuditoria.class);
            u.setId(id);
            UsuarioAuditoria actualizado = service.update(u);
            UsuarioAuditoriaDTO responseDTO = m.map(actualizado, UsuarioAuditoriaDTO.class);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Auditoria de usuario no encontrada");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<UsuarioAuditoria> existente = service.listId(id);
        
        if (existente.isPresent()) {
            service.delete(id);
            return ResponseEntity.ok("Auditoria de usuario eliminada correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Auditoria de usuario no encontrada");
        }
    }
}