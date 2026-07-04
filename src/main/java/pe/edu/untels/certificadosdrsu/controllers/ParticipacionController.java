package pe.edu.untels.certificadosdrsu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.certificadosdrsu.dtos.ParticipanteProyectoDTO;
import pe.edu.untels.certificadosdrsu.dtos.ParticipanteProyectoRequestDTO;
import pe.edu.untels.certificadosdrsu.servicesinterface.ParticipacionService;

import java.util.List;

@RestController
@RequestMapping("/api/participaciones")
public class ParticipacionController {

    @Autowired
    private ParticipacionService participacionService;

    @GetMapping("/proyecto/{idProyecto}")
    public ResponseEntity<?> listarPorProyecto(@PathVariable Long idProyecto) {
        try {
            List<ParticipanteProyectoDTO> participantes = participacionService.listByProject(idProyecto);
            return ResponseEntity.ok(participantes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/proyecto/{idProyecto}/participantes/{idParticipante}")
    public ResponseEntity<?> buscarPorProyectoYParticipante(
            @PathVariable Long idProyecto,
            @PathVariable Long idParticipante
    ) {
        try {
            return participacionService.findByProjectAndParticipant(idProyecto, idParticipante)
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Participación no encontrada"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/proyecto/{idProyecto}")
    public ResponseEntity<?> asignarAProyecto(
            @PathVariable Long idProyecto,
            @RequestBody ParticipanteProyectoRequestDTO request
    ) {
        try {
            ParticipanteProyectoDTO response = participacionService.assignToProject(idProyecto, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/proyecto/{idProyecto}/participantes/{idParticipante}")
    public ResponseEntity<?> actualizarParticipanteDeProyecto(
            @PathVariable Long idProyecto,
            @PathVariable Long idParticipante,
            @RequestBody ParticipanteProyectoRequestDTO request
    ) {
        try {
            ParticipanteProyectoDTO response = participacionService.updateProjectParticipant(
                    idProyecto, idParticipante, request
            );
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/proyecto/{idProyecto}/participantes/{idParticipante}")
    public ResponseEntity<?> quitarDeProyecto(
            @PathVariable Long idProyecto,
            @PathVariable Long idParticipante
    ) {
        try {
            participacionService.removeFromProject(idProyecto, idParticipante);
            return ResponseEntity.ok("Participante retirado del proyecto correctamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
