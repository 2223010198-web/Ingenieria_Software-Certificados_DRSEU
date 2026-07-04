package pe.edu.untels.certificadosdrsu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.certificadosdrsu.dtos.ParticipanteDTO;
import pe.edu.untels.certificadosdrsu.dtos.ParticipanteInsertDTO;
import pe.edu.untels.certificadosdrsu.entities.Participante;
import pe.edu.untels.certificadosdrsu.entities.Usuario;
import pe.edu.untels.certificadosdrsu.servicesinterface.IUsuarioService;
import pe.edu.untels.certificadosdrsu.servicesinterface.ParticipanteService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/participantes")
public class ParticipanteController {

    @Autowired
    private ParticipanteService participanteService;

    @GetMapping
    public ResponseEntity<List<ParticipanteDTO>> buscar(
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "false") boolean includeInactive
    ) {
        List<ParticipanteDTO> participantes = participanteService.search(q, includeInactive)
                .stream()
                .map(this::toDto)
                .toList();

        return ResponseEntity.ok(participantes);
    }

    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody ParticipanteInsertDTO dto) {
        try {
            Participante guardado = participanteService.insert(toEntity(dto));
            return ResponseEntity.status(HttpStatus.CREATED).body(toInsertDto(guardado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<?> buscarPorDni(@PathVariable String dni) {
        Optional<Participante> participante = participanteService.findByDni(dni);

        if (participante.isPresent()) {
            return ResponseEntity.ok(toInsertDto(participante.get()));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Participante no encontrado");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Participante> participante = participanteService.listId(id);

        if (participante.isPresent()) {
            return ResponseEntity.ok(toInsertDto(participante.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Participante no encontrado");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody ParticipanteInsertDTO dto) {
        Optional<Participante> existente = participanteService.listId(id);
        if (existente.isPresent()) {
            Participante p = toEntity(dto);
            p.setIdParticipante(id);
            try {
                Participante actualizado = participanteService.update(p);
                return ResponseEntity.ok(toInsertDto(actualizado));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Participante no encontrado");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Participante> existente = participanteService.listId(id);
        if (existente.isPresent()) {
            participanteService.deactivate(id);
            return ResponseEntity.ok("Participante desactivado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Participante no encontrado");
        }
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<?> activar(@PathVariable Long id) {
        Optional<Participante> existente = participanteService.listId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Participante no encontrado");
        }

        Participante participante = existente.get();
        participante.setActivo(true);
        return ResponseEntity.ok(toInsertDto(participanteService.update(participante)));
    }

    private Participante toEntity(ParticipanteInsertDTO dto) {
        Participante participante = new Participante();
        participante.setIdParticipante(dto.getIdParticipante() != null ? dto.getIdParticipante() : dto.getId());
        participante.setDni(dto.getDni());
        participante.setNombres(dto.getNombres());
        participante.setApellidos(dto.getApellidos());
        participante.setEmail(dto.getEmail());
        participante.setCelular(dto.getCelular());
        participante.setCategoria(dto.getCategoria());
        participante.setActivo(dto.isActivo());
        participante.setCreatedAt(dto.getCreatedAt());
        participante.setUpdatedAt(dto.getUpdatedAt());
        return participante;
    }

    private ParticipanteDTO toDto(Participante participante) {
        ParticipanteDTO dto = new ParticipanteDTO();
        dto.setId(participante.getIdParticipante());
        dto.setIdParticipante(participante.getIdParticipante());
        dto.setDni(participante.getDni());
        dto.setNombres(participante.getNombres());
        dto.setApellidos(participante.getApellidos());
        dto.setNombreCompleto(buildFullName(participante));
        dto.setEmail(participante.getEmail());
        dto.setCelular(participante.getCelular());
        dto.setCategoria(participante.getCategoria());
        dto.setActivo(participante.isActivo());
        return dto;
    }

    private ParticipanteInsertDTO toInsertDto(Participante participante) {
        ParticipanteInsertDTO dto = new ParticipanteInsertDTO();
        dto.setId(participante.getIdParticipante());
        dto.setIdParticipante(participante.getIdParticipante());
        dto.setDni(participante.getDni());
        dto.setNombres(participante.getNombres());
        dto.setApellidos(participante.getApellidos());
        dto.setNombreCompleto(buildFullName(participante));
        dto.setEmail(participante.getEmail());
        dto.setCelular(participante.getCelular());
        dto.setCategoria(participante.getCategoria());
        dto.setActivo(participante.isActivo());
        dto.setCreatedAt(participante.getCreatedAt());
        dto.setUpdatedAt(participante.getUpdatedAt());
        return dto;
    }

    private String buildFullName(Participante participante) {
        String nombres = participante.getNombres() == null ? "" : participante.getNombres().trim();
        String apellidos = participante.getApellidos() == null ? "" : participante.getApellidos().trim();
        return (nombres + " " + apellidos).trim();
    }
}
