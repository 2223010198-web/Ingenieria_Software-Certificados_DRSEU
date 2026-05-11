package pe.edu.untels.certificadosdrsu.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.certificadosdrsu.dtos.ParticipacionDTO;
import pe.edu.untels.certificadosdrsu.dtos.ParticipacionInsertDTO;
import pe.edu.untels.certificadosdrsu.entities.Participacion;
import pe.edu.untels.certificadosdrsu.servicesinterface.ParticipacionService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/participaciones")
public class ParticipacionController {

    @Autowired
    private ParticipacionService participacionService;

    @GetMapping("/lista")
    public ResponseEntity<List<ParticipacionDTO>> listar() {
        ModelMapper m = new ModelMapper();
        List<ParticipacionDTO> lista = participacionService.list()
                .stream()
                .map(p -> m.map(p, ParticipacionDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PostMapping("/nuevo")
    public ResponseEntity<ParticipacionInsertDTO> registrar(@RequestBody ParticipacionInsertDTO dto) {
        ModelMapper m = new ModelMapper();
        Participacion p = m.map(dto, Participacion.class);
        Participacion guardado = participacionService.insert(p);
        ParticipacionInsertDTO responseDTO = m.map(guardado, ParticipacionInsertDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        ModelMapper m = new ModelMapper();
        Optional<Participacion> participacion = participacionService.listId(id);
        if (participacion.isPresent()) {
            ParticipacionInsertDTO dto = m.map(participacion.get(), ParticipacionInsertDTO.class);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Participacion no encontrada");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody ParticipacionInsertDTO dto) {
        ModelMapper m = new ModelMapper();
        Optional<Participacion> existente = participacionService.listId(id);
        if (existente.isPresent()) {
            Participacion p = m.map(dto, Participacion.class);
            p.setId(id);
            Participacion actualizado = participacionService.update(p);
            ParticipacionInsertDTO responseDTO = m.map(actualizado, ParticipacionInsertDTO.class);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Participacion no encontrada");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Participacion> existente = participacionService.listId(id);
        if (existente.isPresent()) {
            participacionService.delete(id);
            return ResponseEntity.ok("Participacion eliminada correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Participacion no encontrada");
        }
    }
}
