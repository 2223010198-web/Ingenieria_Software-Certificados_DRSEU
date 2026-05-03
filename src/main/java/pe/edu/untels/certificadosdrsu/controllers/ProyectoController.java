package pe.edu.untels.certificadosdrsu.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.certificadosdrsu.dtos.ProyectoDto;
import pe.edu.untels.certificadosdrsu.dtos.ProyectoInsertDto;
import pe.edu.untels.certificadosdrsu.entities.PlantillaPdf;
import pe.edu.untels.certificadosdrsu.entities.Proyecto;
import pe.edu.untels.certificadosdrsu.entities.TipoCertificado;
import pe.edu.untels.certificadosdrsu.entities.Usuario;
import pe.edu.untels.certificadosdrsu.repositories.IPlantillaPdfRepository;
import pe.edu.untels.certificadosdrsu.repositories.ITipoCertificadoRepository;
import pe.edu.untels.certificadosdrsu.servicesinterface.IProyectoService;
import pe.edu.untels.certificadosdrsu.servicesinterface.IUsuarioService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {
    @Autowired
    private IProyectoService proyectoService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private ITipoCertificadoRepository tipoCertificadoRepository;

    @Autowired
    private IPlantillaPdfRepository plantillaPdfRepository;

    @GetMapping("/lista")
    public ResponseEntity<List<ProyectoDto>> listar() {
        ModelMapper m = new ModelMapper();
        List<ProyectoDto> listaProyectos = proyectoService.list()
                .stream().map(p -> m.map(p, ProyectoDto.class))
                .toList();

        return ResponseEntity.ok(listaProyectos);
    }

    @PostMapping("/nuevo")
    public ResponseEntity<?> registrar(@RequestBody ProyectoInsertDto dto) {
        ModelMapper m = new ModelMapper();
        Proyecto p = m.map(dto, Proyecto.class);

        // Resolver FK: TipoCertificado
        Optional<TipoCertificado> tipo = tipoCertificadoRepository.findById(dto.getIdTipoCertificado());
        if (tipo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Tipo de certificado no encontrado");
        }
        p.setTipoCertificado(tipo.get());

        // Resolver FK: PlantillaPdf
        Optional<PlantillaPdf> plantilla = plantillaPdfRepository.findById(dto.getIdPlantillaPdf());
        if (plantilla.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Plantilla PDF no encontrada");
        }
        p.setPlantillaPdf(plantilla.get());

        // Resolver FK: CreadoPor (Usuario)
        Optional<Usuario> creador = usuarioService.listId(dto.getIdCreadoPor());
        if (creador.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario creador no encontrado");
        }
        p.setCreadoPor(creador.get());

        // Resolver FK: AprobadoPor (Usuario, opcional)
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
        responseDTO.setIdTipoCertificado(guardado.getTipoCertificado().getId());
        responseDTO.setIdPlantillaPdf(guardado.getPlantillaPdf().getId());
        responseDTO.setIdCreadoPor(guardado.getCreadoPor().getId());
        if (guardado.getAprobadoPor() != null) {
            responseDTO.setIdAprobadoPor(guardado.getAprobadoPor().getId());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        ModelMapper m = new ModelMapper();
        Optional<Proyecto> proyecto = proyectoService.listId(id);

        if (proyecto.isPresent()) {
            Proyecto p = proyecto.get();
            ProyectoInsertDto dto = m.map(p, ProyectoInsertDto.class);
            dto.setIdTipoCertificado(p.getTipoCertificado().getId());
            dto.setIdPlantillaPdf(p.getPlantillaPdf().getId());
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
    public ResponseEntity<?> modificar(@PathVariable int id, @RequestBody ProyectoInsertDto dto) {
        ModelMapper m = new ModelMapper();
        Optional<Proyecto> existente = proyectoService.listId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Proyecto no encontrado");
        }

        Proyecto p = m.map(dto, Proyecto.class);
        p.setIdProyecto(id);

        // Resolver FK: TipoCertificado
        Optional<TipoCertificado> tipo = tipoCertificadoRepository.findById(dto.getIdTipoCertificado());
        if (tipo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Tipo de certificado no encontrado");
        }
        p.setTipoCertificado(tipo.get());

        // Resolver FK: PlantillaPdf
        Optional<PlantillaPdf> plantilla = plantillaPdfRepository.findById(dto.getIdPlantillaPdf());
        if (plantilla.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Plantilla PDF no encontrada");
        }
        p.setPlantillaPdf(plantilla.get());

        // Resolver FK: CreadoPor
        Optional<Usuario> creador = usuarioService.listId(dto.getIdCreadoPor());
        if (creador.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario creador no encontrado");
        }
        p.setCreadoPor(creador.get());

        // Resolver FK: AprobadoPor (opcional)
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
        responseDTO.setIdTipoCertificado(actualizado.getTipoCertificado().getId());
        responseDTO.setIdPlantillaPdf(actualizado.getPlantillaPdf().getId());
        responseDTO.setIdCreadoPor(actualizado.getCreadoPor().getId());
        if (actualizado.getAprobadoPor() != null) {
            responseDTO.setIdAprobadoPor(actualizado.getAprobadoPor().getId());
        }
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable int id) {
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
