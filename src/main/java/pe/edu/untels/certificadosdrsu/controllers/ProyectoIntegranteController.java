package pe.edu.untels.certificadosdrsu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.certificadosdrsu.dtos.IntegranteDto;
import pe.edu.untels.certificadosdrsu.dtos.IntegranteInsertDto;
import pe.edu.untels.certificadosdrsu.dtos.IntegranteUpdateDto;
import pe.edu.untels.certificadosdrsu.dtos.ParticipanteSugerenciaDto;
import pe.edu.untels.certificadosdrsu.entities.Participacion;
import pe.edu.untels.certificadosdrsu.entities.Participante;
import pe.edu.untels.certificadosdrsu.enums.CategoriaParticipante;
import pe.edu.untels.certificadosdrsu.servicesinterface.IProyectoIntegranteService;
import pe.edu.untels.certificadosdrsu.servicesinterface.IProyectoService;
import pe.edu.untels.certificadosdrsu.servicesinterface.ParticipanteService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Módulo 2 — Gestión de proyectos: integrantes de un proyecto.
 * HU-13 (orden alfabético), HU-19 (agregar con sugerencias), HU-20 (tipo de participación).
 */
@RestController
@RequestMapping("/api/proyectos")
public class ProyectoIntegranteController {

    private static final int LIMITE_SUGERENCIAS = 10;

    @Autowired
    private IProyectoIntegranteService integranteService;

    @Autowired
    private IProyectoService proyectoService;

    @Autowired
    private ParticipanteService participanteService;

    // HU-13: lista de integrantes ordenada por apellidos, nombres
    @GetMapping("/{id}/integrantes")
    public ResponseEntity<?> listar(@PathVariable Long id) {
        if (proyectoService.listId(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proyecto no encontrado");
        }
        List<IntegranteDto> integrantes = integranteService.listarPorProyecto(id)
                .stream()
                .map(p -> toDto(p, p.getParticipante()))
                .toList();
        return ResponseEntity.ok(integrantes);
    }

    // HU-19 / HU-20: agregar integrante al proyecto con su tipo de participación
    @PostMapping("/{id}/integrantes")
    public ResponseEntity<?> agregar(@PathVariable Long id, @RequestBody IntegranteInsertDto dto) {
        if (proyectoService.listId(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proyecto no encontrado");
        }
        if (dto.getIdParticipante() == null) {
            return ResponseEntity.badRequest().body("idParticipante es obligatorio");
        }
        Optional<Participante> participante = participanteService.listId(dto.getIdParticipante());
        if (participante.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Participante no encontrado");
        }
        if (integranteService.existeEnProyecto(dto.getIdParticipante(), id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El participante ya es integrante de este proyecto");
        }
        if (!esTipoValido(dto.getTipoParticipacion())) {
            return ResponseEntity.badRequest().body(mensajeTipoInvalido());
        }

        Participacion p = new Participacion();
        p.setIdProyecto(id);
        p.setIdParticipante(dto.getIdParticipante());
        p.setTipoParticipacion(dto.getTipoParticipacion().toUpperCase());
        p.setDescripcionParticipante(dto.getDescripcionParticipante());

        Participacion guardado = integranteService.insert(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(guardado, participante.get()));
    }

    // HU-20: cambiar el tipo de participación (y descripción) de un integrante
    @PutMapping("/{id}/integrantes/{idParticipacion}")
    public ResponseEntity<?> modificar(@PathVariable Long id,
                                       @PathVariable Long idParticipacion,
                                       @RequestBody IntegranteUpdateDto dto) {
        Optional<Participacion> existente = integranteService.listId(idParticipacion);
        if (existente.isEmpty() || !existente.get().getIdProyecto().equals(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Integrante no encontrado en este proyecto");
        }
        if (!esTipoValido(dto.getTipoParticipacion())) {
            return ResponseEntity.badRequest().body(mensajeTipoInvalido());
        }

        Participacion p = existente.get();
        p.setTipoParticipacion(dto.getTipoParticipacion().toUpperCase());
        p.setDescripcionParticipante(dto.getDescripcionParticipante());

        Participacion actualizado = integranteService.update(p);
        Participante participante = participanteService.listId(actualizado.getIdParticipante()).orElse(null);
        return ResponseEntity.ok(toDto(actualizado, participante));
    }

    @DeleteMapping("/{id}/integrantes/{idParticipacion}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @PathVariable Long idParticipacion) {
        Optional<Participacion> existente = integranteService.listId(idParticipacion);
        if (existente.isEmpty() || !existente.get().getIdProyecto().equals(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Integrante no encontrado en este proyecto");
        }
        integranteService.delete(idParticipacion);
        return ResponseEntity.ok("Integrante eliminado correctamente");
    }

    // HU-19: sugerencias para el autocompletado al agregar integrantes
    @GetMapping("/integrantes/sugerencias")
    public ResponseEntity<List<ParticipanteSugerenciaDto>> sugerencias(@RequestParam("q") String q) {
        if (q == null || q.trim().length() < 2) {
            return ResponseEntity.ok(List.of());
        }
        List<ParticipanteSugerenciaDto> sugerencias = integranteService
                .sugerencias(q.trim(), LIMITE_SUGERENCIAS)
                .stream()
                .map(this::toSugerenciaDto)
                .toList();
        return ResponseEntity.ok(sugerencias);
    }

    // HU-20: catálogo de tipos de participación válidos
    @GetMapping("/integrantes/tipos")
    public ResponseEntity<List<String>> tiposParticipacion() {
        return ResponseEntity.ok(
                Arrays.stream(CategoriaParticipante.values()).map(Enum::name).toList());
    }

    private boolean esTipoValido(String tipo) {
        if (tipo == null || tipo.isBlank()) {
            return false;
        }
        return Arrays.stream(CategoriaParticipante.values())
                .anyMatch(c -> c.name().equalsIgnoreCase(tipo.trim()));
    }

    private String mensajeTipoInvalido() {
        return "Tipo de participación inválido. Valores permitidos: "
                + Arrays.toString(CategoriaParticipante.values());
    }

    private IntegranteDto toDto(Participacion p, Participante participante) {
        IntegranteDto dto = new IntegranteDto();
        dto.setIdParticipacion(p.getId());
        dto.setIdParticipante(p.getIdParticipante());
        dto.setTipoParticipacion(p.getTipoParticipacion());
        dto.setDescripcionParticipante(p.getDescripcionParticipante());
        if (participante != null) {
            dto.setNombres(participante.getNombres());
            dto.setApellidos(participante.getApellidos());
            dto.setEmail(participante.getEmail());
        }
        return dto;
    }

    private ParticipanteSugerenciaDto toSugerenciaDto(Participante p) {
        ParticipanteSugerenciaDto dto = new ParticipanteSugerenciaDto();
        dto.setIdParticipante(p.getIdParticipante());
        dto.setNombres(p.getNombres());
        dto.setApellidos(p.getApellidos());
        dto.setEmail(p.getEmail());
        return dto;
    }
}
