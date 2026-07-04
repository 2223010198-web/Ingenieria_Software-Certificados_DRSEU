package pe.edu.untels.certificadosdrsu.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.certificadosdrsu.dtos.TipoCertificadoDTO;
import pe.edu.untels.certificadosdrsu.dtos.TipoCertificadoInsertDTO;
import pe.edu.untels.certificadosdrsu.servicesinterface.ITipoCertificadoService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/certificate-types")
public class TipoCertificadoController {

    private final ITipoCertificadoService service;

    public TipoCertificadoController(ITipoCertificadoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TipoCertificadoDTO>> listar() {
        return ResponseEntity.ok(service.listarActivos());
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody TipoCertificadoInsertDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody TipoCertificadoInsertDTO dto) {
        try {
            return ResponseEntity.ok(service.actualizar(id, dto));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            service.desactivar(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
