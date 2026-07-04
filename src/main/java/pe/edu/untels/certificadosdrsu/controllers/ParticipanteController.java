package pe.edu.untels.certificadosdrsu.controllers;

import org.modelmapper.ModelMapper;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/participantes")
public class ParticipanteController {

    @Autowired
    private ParticipanteService participanteService;

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/lista")
    public ResponseEntity<List<ParticipanteDTO>> listar() {
        ModelMapper m = new ModelMapper();

        // Mapeo de la lista de entidades a la lista de DTOs
        List<ParticipanteDTO> listaParticipantes = participanteService.list()
                .stream()
                .map(participante -> m.map(participante, ParticipanteDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listaParticipantes);
    }

    @PostMapping("/nuevo")
    public ResponseEntity<?> registrar(@RequestBody ParticipanteInsertDTO dto) {
        ModelMapper m = new ModelMapper();
        Participante p = m.map(dto, Participante.class);
        var us = usuarioService.listId(dto.getUsuarioId());
        if (us.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Participante guardado = participanteService.insert(p);

        us.get().setIdParticipante(guardado.getIdParticipante());
        usuarioService.update(us.get());
        ParticipanteInsertDTO responseDTO = m.map(guardado, ParticipanteInsertDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        ModelMapper m = new ModelMapper();
        Optional<Participante> participante = participanteService.listId(id);

        if (participante.isPresent()) {
            ParticipanteInsertDTO dto = m.map(participante.get(), ParticipanteInsertDTO.class);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Participante no encontrado");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody ParticipanteInsertDTO dto) {
        ModelMapper m = new ModelMapper();
        Optional<Participante> existente = participanteService.listId(id);
        if (existente.isPresent()) {
            Participante p = m.map(dto, Participante.class);
            p.setIdParticipante(id);
            Participante actualizado = participanteService.update(p);
            ParticipanteInsertDTO responseDTO = m.map(actualizado, ParticipanteInsertDTO.class);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Participante no encontrado");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Participante> existente = participanteService.listId(id);
        if (existente.isPresent()) {
            participanteService.delete(id);
            return ResponseEntity.ok("Participante eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Participante no encontrado");
        }
    }
}
