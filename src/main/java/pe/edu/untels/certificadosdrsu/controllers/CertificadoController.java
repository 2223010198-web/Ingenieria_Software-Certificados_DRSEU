package pe.edu.untels.certificadosdrsu.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.certificadosdrsu.dtos.CertificadoDTO;
import pe.edu.untels.certificadosdrsu.dtos.CertificadoInsertDTO;
import pe.edu.untels.certificadosdrsu.entities.Certificado;
import pe.edu.untels.certificadosdrsu.servicesinterface.ICertificadoService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/certificados")
public class CertificadoController {

    @Autowired
    private ICertificadoService certificadoService;

    private final ModelMapper m = new ModelMapper();

    @GetMapping("/lista")
    public ResponseEntity<List<CertificadoDTO>> listar() {
        List<CertificadoDTO> lista = certificadoService.list().stream()
                .map(c -> m.map(c, CertificadoDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Certificado> certificado = certificadoService.listId(id);
        if (certificado.isPresent()) {
            CertificadoDTO dto = m.map(certificado.get(), CertificadoDTO.class);
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Certificado no encontrado");
    }

    @PostMapping("/nuevo")
    public ResponseEntity<CertificadoInsertDTO> registrar(@RequestBody CertificadoInsertDTO dto) {
        Certificado c = m.map(dto, Certificado.class);
        Certificado guardado = certificadoService.insert(c);
        CertificadoInsertDTO responseDTO = m.map(guardado, CertificadoInsertDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody CertificadoInsertDTO dto) {
        Optional<Certificado> existente = certificadoService.listId(id);
        if (existente.isPresent()) {
            Certificado c = m.map(dto, Certificado.class);
            c.setId(id);
            Certificado actualizado = certificadoService.update(c);
            CertificadoInsertDTO responseDTO = m.map(actualizado, CertificadoInsertDTO.class);
            return ResponseEntity.ok(responseDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Certificado no encontrado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Certificado> existente = certificadoService.listId(id);
        if (existente.isPresent()) {
            certificadoService.delete(id);
            return ResponseEntity.ok("Certificado eliminado correctamente");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Certificado no encontrado");
    }
}
