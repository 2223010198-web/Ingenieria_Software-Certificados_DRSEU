package pe.edu.untels.certificadosdrsu.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.certificadosdrsu.dtos.ProyectoDto;
import pe.edu.untels.certificadosdrsu.dtos.ProyectoInsertDto;
import pe.edu.untels.certificadosdrsu.dtos.ProyectoSearchResponseDTO;
import pe.edu.untels.certificadosdrsu.entities.Proyecto;
import pe.edu.untels.certificadosdrsu.entities.Usuario;
import pe.edu.untels.certificadosdrsu.enums.EstadoProyecto;
import pe.edu.untels.certificadosdrsu.servicesinterface.IProyectoIntegranteService;
import pe.edu.untels.certificadosdrsu.servicesinterface.IProyectoService;
import pe.edu.untels.certificadosdrsu.servicesinterface.IUsuarioService;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

    @Autowired
    private IProyectoService proyectoService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IProyectoIntegranteService integranteService;

    private final ModelMapper m = new ModelMapper();

    @GetMapping("/lista")
    public ResponseEntity<List<ProyectoDto>> listar() {
        Map<Long, Long> conteoIntegrantes = integranteService.contarIntegrantesPorProyecto();

        List<ProyectoDto> listaProyectos = proyectoService.list()
                .stream().map(p -> {
                    ProyectoDto dto = m.map(p, ProyectoDto.class);
                    dto.setCantidadIntegrantes(conteoIntegrantes.getOrDefault(p.getIdProyecto(), 0L));
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

    @GetMapping("/search")
    public ResponseEntity<List<ProyectoSearchResponseDTO>> buscar(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta,
            @RequestParam(required = false) EstadoProyecto estado) {

        List<ProyectoSearchResponseDTO> resultado = proyectoService.search(titulo, fechaDesde, fechaHasta, estado)
                .stream().map(this::toSearchDto).toList();

        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/sugerencias")
    public ResponseEntity<List<String>> sugerencias(@RequestParam String q) {
        List<String> titulos = proyectoService.sugerencias(q)
                .stream().map(Proyecto::getTitulo).toList();

        return ResponseEntity.ok(titulos);
    }

    @GetMapping("/reporte")
    public ResponseEntity<byte[]> reporte(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta,
            @RequestParam(required = false) EstadoProyecto estado) {

        List<Proyecto> proyectos = proyectoService.search(titulo, fechaDesde, fechaHasta, estado);
        byte[] csv = generarReporteCsv(proyectos);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_proyectos.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csv);
    }

    private ProyectoSearchResponseDTO toSearchDto(Proyecto p) {
        ProyectoSearchResponseDTO dto = new ProyectoSearchResponseDTO();
        dto.setIdProyecto(p.getIdProyecto());
        dto.setTitulo(p.getTitulo());
        dto.setDescripcion(p.getDescripcion());
        dto.setEstado(p.getEstado() != null ? p.getEstado().name() : null);
        dto.setFechaAprobacion(p.getFechaAprobacion());
        dto.setTipoProyectoNombre(p.getTipoProyecto() != null ? p.getTipoProyecto().getTipoProyectoNombre() : null);
        dto.setCreadoPorUsername(p.getCreadoPor() != null ? p.getCreadoPor().getUsername() : null);
        return dto;
    }

    private byte[] generarReporteCsv(List<Proyecto> proyectos) {
        StringBuilder sb = new StringBuilder();
        sb.append("ID,Titulo,Descripcion,Estado,Fecha Aprobacion,Tipo Proyecto,Creado Por\n");

        for (Proyecto p : proyectos) {
            sb.append(p.getIdProyecto()).append(",");
            sb.append(csvEscape(p.getTitulo())).append(",");
            sb.append(csvEscape(p.getDescripcion())).append(",");
            sb.append(csvEscape(p.getEstado() != null ? p.getEstado().name() : "")).append(",");
            sb.append(p.getFechaAprobacion() != null ? p.getFechaAprobacion().toString() : "").append(",");
            sb.append(csvEscape(p.getTipoProyecto() != null ? p.getTipoProyecto().getTipoProyectoNombre() : "")).append(",");
            sb.append(csvEscape(p.getCreadoPor() != null ? p.getCreadoPor().getUsername() : "")).append("\n");
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.writeBytes(sb.toString().getBytes(StandardCharsets.UTF_8));
        return out.toByteArray();
    }

    private String csvEscape(String value) {
        if (value == null) {
            return "";
        }
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
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
}
