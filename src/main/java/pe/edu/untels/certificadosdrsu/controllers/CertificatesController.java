package pe.edu.untels.certificadosdrsu.controllers;

import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.certificadosdrsu.dtos.CertificadoDetalleDTO;
import pe.edu.untels.certificadosdrsu.dtos.SugerenciaCertificadoDTO;
import pe.edu.untels.certificadosdrsu.servicesinterface.ICertificadoService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/certificates")
public class CertificatesController {

    private final ICertificadoService service;

    public CertificatesController(ICertificadoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<CertificadoDetalleDTO>> listar(
            @RequestParam(required = false) String participant,
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false) Long typeId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
            @RequestParam(defaultValue = "fechaEmision") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort));
        Page<CertificadoDetalleDTO> resultado = service.buscarFiltrado(
                participant, projectId, typeId, status, code, dateFrom, dateTo, pageable);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/suggestions")
    public ResponseEntity<List<SugerenciaCertificadoDTO>> sugerencias(@RequestParam String q) {
        return ResponseEntity.ok(service.sugerencias(q));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.obtenerDetalle(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> descargar(@PathVariable Long id) {
        try {
            Path ruta = service.resolverArchivoPdf(id);
            Resource resource = new PathResource(ruta);
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }
            String contentType = detectarContentType(ruta);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"certificado-" + id + ".pdf\"")
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/preview")
    public ResponseEntity<Resource> preview(@PathVariable Long id) {
        try {
            Path ruta = service.resolverArchivoPdf(id);
            Resource resource = new PathResource(ruta);
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }
            String contentType = detectarContentType(ruta);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"certificado-" + id + ".pdf\"")
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private String detectarContentType(Path ruta) {
        try {
            String tipo = Files.probeContentType(ruta);
            return tipo != null ? tipo : "application/octet-stream";
        } catch (IOException e) {
            return "application/octet-stream";
        }
    }
}
