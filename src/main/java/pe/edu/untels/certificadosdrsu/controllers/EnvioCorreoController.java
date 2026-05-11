package pe.edu.untels.certificadosdrsu.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.certificadosdrsu.dtos.EnvioCorreoDTO;
import pe.edu.untels.certificadosdrsu.dtos.EnvioCorreoInsertDTO;
import pe.edu.untels.certificadosdrsu.entities.Certificado;
import pe.edu.untels.certificadosdrsu.entities.EnvioCorreo;
import pe.edu.untels.certificadosdrsu.entities.Participante;
import pe.edu.untels.certificadosdrsu.servicesinterface.ICertificadoService;
import pe.edu.untels.certificadosdrsu.servicesinterface.IEnvioCorreoService;
import pe.edu.untels.certificadosdrsu.servicesinterface.ParticipanteService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/envios-correo")
public class EnvioCorreoController {

    @Autowired
    private IEnvioCorreoService envioCorreoService;

    @Autowired
    private ParticipanteService participanteService;

    @Autowired
    private ICertificadoService certificadoService;

    @GetMapping("/lista")
    public ResponseEntity<List<EnvioCorreoDTO>> listar() {
        ModelMapper m = new ModelMapper();
        List<EnvioCorreoDTO> listaEnvios = envioCorreoService.list()
                .stream()
                .map(envio -> m.map(envio, EnvioCorreoDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listaEnvios);
    }

    @PostMapping("/nuevo")
    public ResponseEntity<?> registrar(@RequestBody EnvioCorreoInsertDTO dto) {
        ModelMapper m = new ModelMapper();
        EnvioCorreo e = m.map(dto, EnvioCorreo.class);

        // Resolver FK: idEnviadoPor (Participante)
        Optional<Participante> enviadoPor = participanteService.listId(dto.getIdEnviadoPor());
        if (enviadoPor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Participante remitente no encontrado");
        }
        e.setIdEnviadoPor(enviadoPor.get().getIdParticipante());

        // Resolver FK: idCertificado (Certificado)
        Optional<Certificado> certificado = certificadoService.listId(dto.getIdCertificado());
        if (certificado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Certificado no encontrado");
        }
        e.setIdCertificado(certificado.get().getId());

        // Resolver FK: idParticipante (Participante)
        Optional<Participante> participante = participanteService.listId(dto.getIdParticipante());
        if (participante.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Participante destinatario no encontrado");
        }
        e.setIdParticipante(participante.get().getIdParticipante());

        EnvioCorreo guardado = envioCorreoService.insert(e);
        EnvioCorreoInsertDTO responseDTO = m.map(guardado, EnvioCorreoInsertDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        ModelMapper m = new ModelMapper();
        Optional<EnvioCorreo> envioCorreo = envioCorreoService.listId(id);

        if (envioCorreo.isPresent()) {
            EnvioCorreoInsertDTO dto = m.map(envioCorreo.get(), EnvioCorreoInsertDTO.class);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Envío de correo no encontrado");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable int id, @RequestBody EnvioCorreoInsertDTO dto) {
        ModelMapper m = new ModelMapper();
        Optional<EnvioCorreo> existente = envioCorreoService.listId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Envío de correo no encontrado");
        }

        EnvioCorreo e = m.map(dto, EnvioCorreo.class);
        e.setId(id);

        // Resolver FK: idEnviadoPor (Participante)
        Optional<Participante> enviadoPor = participanteService.listId(dto.getIdEnviadoPor());
        if (enviadoPor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Participante remitente no encontrado");
        }
        e.setIdEnviadoPor(enviadoPor.get().getIdParticipante());

        // Resolver FK: idCertificado (Certificado)
        Optional<Certificado> certificado = certificadoService.listId(dto.getIdCertificado());
        if (certificado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Certificado no encontrado");
        }
        e.setIdCertificado(certificado.get().getId());

        // Resolver FK: idParticipante (Participante)
        Optional<Participante> participante = participanteService.listId(dto.getIdParticipante());
        if (participante.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Participante destinatario no encontrado");
        }
        e.setIdParticipante(participante.get().getIdParticipante());

        EnvioCorreo actualizado = envioCorreoService.update(e);
        EnvioCorreoInsertDTO responseDTO = m.map(actualizado, EnvioCorreoInsertDTO.class);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable int id) {
        Optional<EnvioCorreo> existente = envioCorreoService.listId(id);
        if (existente.isPresent()) {
            envioCorreoService.delete(id);
            return ResponseEntity.ok("Envío de correo eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Envío de correo no encontrado");
        }
    }
}
