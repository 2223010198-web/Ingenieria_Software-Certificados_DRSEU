package pe.edu.untels.certificadosdrsu.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.certificadosdrsu.dtos.VerificacionCertificadoDTO;
import pe.edu.untels.certificadosdrsu.servicesinterface.ICertificadoService;

@RestController
@RequestMapping("/public/certificates")
public class PublicCertificatesController {

    private final ICertificadoService service;

    public PublicCertificatesController(ICertificadoService service) {
        this.service = service;
    }

    @GetMapping("/verify/{code}")
    public ResponseEntity<VerificacionCertificadoDTO> verificar(@PathVariable String code) {
        VerificacionCertificadoDTO resultado = service.verificar(code);
        return ResponseEntity.ok(resultado);
    }
}
