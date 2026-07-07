package pe.edu.untels.certificadosdrsu.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.certificadosdrsu.dtos.IntegranteDTO;
import pe.edu.untels.certificadosdrsu.dtos.IntegranteInsertDTO;
import pe.edu.untels.certificadosdrsu.dtos.IntegranteUpdateDTO;
import pe.edu.untels.certificadosdrsu.dtos.ParticipanteSugerenciaDTO;
import pe.edu.untels.certificadosdrsu.dtos.ProyectoDto;
import pe.edu.untels.certificadosdrsu.dtos.ProyectoInsertDto;
import pe.edu.untels.certificadosdrsu.entities.Participacion;
import pe.edu.untels.certificadosdrsu.entities.Participante;
import pe.edu.untels.certificadosdrsu.entities.Proyecto;
import pe.edu.untels.certificadosdrsu.entities.Usuario;
import pe.edu.untels.certificadosdrsu.enums.CategoriaParticipante;
import pe.edu.untels.certificadosdrsu.servicesinterface.IProyectoService;
import pe.edu.untels.certificadosdrsu.servicesinterface.IUsuarioService;
import pe.edu.untels.certificadosdrsu.servicesinterface.ParticipacionService;
import pe.edu.untels.certificadosdrsu.servicesinterface.ParticipanteService;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

    @Autowired
    private IProyectoService proyectoService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private ParticipacionService participacionService;

    @Autowired
    private ParticipanteService participanteService;

    private final ModelMapper m = new ModelMapper();

    @GetMapping("/lista")
    public ResponseEntity<List<ProyectoDto>> listar() {
        List<ProyectoDto> listaProyectos = proyectoService.list()
                .stream().map(p -> {
                    ProyectoDto dto = m.map(p, ProyectoDto.class);
                    dto.setIdProyecto(p.getIdProyecto());
                    dto.setCantidadIntegrantes(participacionService.contarPorProyecto(p.getIdProyecto()));
                    return dto;
                })
                .toList();

        return ResponseEntity.ok(listaProyectos);
    }

    @PostMapping("/nuevo")
    public ResponseEntity<?> registrar(@RequestBody ProyectoInsertDto dto) {
        Proyecto p = m.map(dto, Proyecto.class);

        Optional<Usuario> creador = usuarioService.listId(dto.getIdCreadoPor());
        if (creador.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario creador no encontrado");
        }
        p.setCreadoPor(creador.get());

        if (dto.getIdAprobadoPor() != null) {
            Optional<Usuario> aprobador = usuarioService.listId(dto.getIdAprobadoPor());
            if (aprobador.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Usuario aprobador no encontrado");
            }
            p.setAprobadoPor(aprobador.get());
        }

        Proyecto guardado = proyectoService.insert(p);
        ProyectoInsertDto responseDTO = m.map(guardado, ProyectoInsertDto.class);
        responseDTO.setIdCreadoPor(guardado.getCreadoPor().getId());
        if (guardado.getAprobadoPor() != null) {
            responseDTO.setIdAprobadoPor(guardado.getAprobadoPor().getId());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Proyecto> proyecto = proyectoService.listId(id);

        if (proyecto.isPresent()) {
            Proyecto p = proyecto.get();
            ProyectoInsertDto dto = m.map(p, ProyectoInsertDto.class);
            dto.setIdCreadoPor(p.getCreadoPor().getId());
            if (p.getAprobadoPor() != null) {
                dto.setIdAprobadoPor(p.getAprobadoPor().getId());
            }
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Proyecto no encontrado");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody ProyectoInsertDto dto) {
        Optional<Proyecto> existente = proyectoService.listId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Proyecto no encontrado");
        }

        Proyecto p = m.map(dto, Proyecto.class);
        p.setIdProyecto(id);

        // Resolver FK: CreadoPor
        Optional<Usuario> creador = usuarioService.listId(dto.getIdCreadoPor());
        if (creador.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario creador no encontrado");
        }
        p.setCreadoPor(creador.get());

        if (dto.getIdAprobadoPor() != null) {
            Optional<Usuario> aprobador = usuarioService.listId(dto.getIdAprobadoPor());
            if (aprobador.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Usuario aprobador no encontrado");
            }
            p.setAprobadoPor(aprobador.get());
        }

        Proyecto actualizado = proyectoService.update(p);
        ProyectoInsertDto responseDTO = m.map(actualizado, ProyectoInsertDto.class);
        responseDTO.setIdCreadoPor(actualizado.getCreadoPor().getId());
        if (actualizado.getAprobadoPor() != null) {
            responseDTO.setIdAprobadoPor(actualizado.getAprobadoPor().getId());
        }
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Proyecto> existente = proyectoService.listId(id);
        if (existente.isPresent()) {
            proyectoService.delete(id);
            return ResponseEntity.ok("Proyecto eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Proyecto no encontrado");
        }
    }

    // ---- Módulo 2: integrantes de un proyecto ----

    @GetMapping("/{id}/integrantes")
    public ResponseEntity<?> listarIntegrantes(@PathVariable Long id) {
        if (proyectoService.listId(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proyecto no encontrado");
        }

        List<Participacion> participaciones = participacionService.listByProyecto(id);
        Map<Long, Participante> participantesPorId = participanteService.list().stream()
                .collect(java.util.stream.Collectors.toMap(Participante::getIdParticipante, Function.identity()));

        List<IntegranteDTO> integrantes = participaciones.stream()
                .map(p -> {
                    Participante participante = participantesPorId.get(p.getIdParticipante());
                    IntegranteDTO dto = new IntegranteDTO();
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
                })
                .sorted(Comparator.comparing(IntegranteDTO::getApellidos, Comparator.nullsLast(String::compareTo))
                        .thenComparing(IntegranteDTO::getNombres, Comparator.nullsLast(String::compareTo)))
                .toList();

        return ResponseEntity.ok(integrantes);
    }

    @PostMapping("/{id}/integrantes")
    public ResponseEntity<?> agregarIntegrante(@PathVariable Long id, @RequestBody IntegranteInsertDTO dto) {
        if (proyectoService.listId(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proyecto no encontrado");
        }

        Optional<Participante> participante = participanteService.listId(dto.getIdParticipante());
        if (participante.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Participante no encontrado");
        }

        if (participacionService.existeParticipacion(id, dto.getIdParticipante())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El participante ya forma parte de este proyecto");
        }

        if (!esTipoParticipacionValido(dto.getTipoParticipacion())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Tipo de participación inválido");
        }

        Participacion p = new Participacion();
        p.setIdProyecto(id);
        p.setIdParticipante(dto.getIdParticipante());
        p.setTipoParticipacion(dto.getTipoParticipacion());
        p.setDescripcionParticipante(dto.getDescripcionParticipante());
        Participacion guardado = participacionService.insert(p);

        IntegranteDTO responseDTO = new IntegranteDTO();
        responseDTO.setIdParticipacion(guardado.getId());
        responseDTO.setIdParticipante(participante.get().getIdParticipante());
        responseDTO.setNombres(participante.get().getNombres());
        responseDTO.setApellidos(participante.get().getApellidos());
        responseDTO.setEmail(participante.get().getEmail());
        responseDTO.setTipoParticipacion(guardado.getTipoParticipacion());
        responseDTO.setDescripcionParticipante(guardado.getDescripcionParticipante());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{id}/integrantes/{idParticipacion}")
    public ResponseEntity<?> actualizarIntegrante(
            @PathVariable Long id, @PathVariable Long idParticipacion, @RequestBody IntegranteUpdateDTO dto) {
        Optional<Participacion> existente = participacionService.listId(idParticipacion);
        if (existente.isEmpty() || !existente.get().getIdProyecto().equals(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Integrante no encontrado en este proyecto");
        }

        if (!esTipoParticipacionValido(dto.getTipoParticipacion())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Tipo de participación inválido");
        }

        Participacion p = existente.get();
        p.setTipoParticipacion(dto.getTipoParticipacion());
        p.setDescripcionParticipante(dto.getDescripcionParticipante());
        Participacion actualizado = participacionService.update(p);

        Participante participante = participanteService.listId(actualizado.getIdParticipante()).orElse(null);
        IntegranteDTO responseDTO = new IntegranteDTO();
        responseDTO.setIdParticipacion(actualizado.getId());
        responseDTO.setIdParticipante(actualizado.getIdParticipante());
        if (participante != null) {
            responseDTO.setNombres(participante.getNombres());
            responseDTO.setApellidos(participante.getApellidos());
            responseDTO.setEmail(participante.getEmail());
        }
        responseDTO.setTipoParticipacion(actualizado.getTipoParticipacion());
        responseDTO.setDescripcionParticipante(actualizado.getDescripcionParticipante());
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}/integrantes/{idParticipacion}")
    public ResponseEntity<?> eliminarIntegrante(@PathVariable Long id, @PathVariable Long idParticipacion) {
        Optional<Participacion> existente = participacionService.listId(idParticipacion);
        if (existente.isEmpty() || !existente.get().getIdProyecto().equals(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Integrante no encontrado en este proyecto");
        }

        participacionService.delete(idParticipacion);
        return ResponseEntity.ok("Integrante eliminado correctamente");
    }

    @GetMapping("/integrantes/sugerencias")
    public ResponseEntity<List<ParticipanteSugerenciaDTO>> sugerenciasIntegrantes(@RequestParam String q) {
        if (q == null || q.trim().length() < 2) {
            return ResponseEntity.ok(List.of());
        }

        List<ParticipanteSugerenciaDTO> sugerencias = participanteService.buscarSugerencias(q.trim())
                .stream()
                .map(p -> m.map(p, ParticipanteSugerenciaDTO.class))
                .toList();

        return ResponseEntity.ok(sugerencias);
    }

    @GetMapping("/integrantes/tipos")
    public ResponseEntity<List<String>> tiposIntegrante() {
        return ResponseEntity.ok(Arrays.stream(CategoriaParticipante.values()).map(Enum::name).toList());
    }

    private boolean esTipoParticipacionValido(String tipo) {
        if (tipo == null) {
            return false;
        }
        return Arrays.stream(CategoriaParticipante.values()).anyMatch(c -> c.name().equals(tipo));
    }
}
