package pe.edu.untels.certificadosdrsu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

import pe.edu.untels.certificadosdrsu.dtos.TipoProyectoDto;
import pe.edu.untels.certificadosdrsu.entities.TipoProyecto;
import pe.edu.untels.certificadosdrsu.servicesinterface.ITipoProyectoService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tipoproyectos")
public class TipoProyectoController {

    @Autowired
    private ITipoProyectoService tipoProyectoService;

    private final ModelMapper m = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<TipoProyectoDto>> listar() {
        List<TipoProyectoDto> list = tipoProyectoService.list().stream()
                .map(p -> m.map(p, TipoProyectoDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoProyectoDto> buscar(@PathVariable Long id) {
        Optional<TipoProyecto> tipo_proyecto = tipoProyectoService.listId(id);
        if (tipo_proyecto.isPresent()) {
            return ResponseEntity.ok(m.map(tipo_proyecto.get(), TipoProyectoDto.class));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TipoProyectoDto> insertar(@RequestBody TipoProyectoDto dto) {
        TipoProyecto tipo_proyecto = m.map(dto, TipoProyecto.class);
        TipoProyecto tipo_proyecto2 = tipoProyectoService.insert(tipo_proyecto);
        TipoProyectoDto responseDTO = m.map(tipo_proyecto2, TipoProyectoDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody TipoProyectoDto dto) {
        Optional<TipoProyecto> tipo_proyecto = tipoProyectoService.listId(id);

        if (tipo_proyecto.isPresent()) {
            TipoProyecto tipo_proyecto1 = m.map(dto, TipoProyecto.class);
            tipo_proyecto1.setIdTipoProyecto(id);

            TipoProyecto tipo_proyecto2 = tipoProyectoService.update(tipo_proyecto1);
            TipoProyectoDto responseDTO = m.map(tipo_proyecto2, TipoProyectoDto.class);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el tipo de proyecto con ID: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        Optional<TipoProyecto> existente = tipoProyectoService.listId(id);

        if (existente.isPresent()) {
            tipoProyectoService.delete(id);
            return ResponseEntity.ok("Tipo de proyecto eliminado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se pudo eliminar el tipo de proyecto. Posiblemente no exista.");
        }
    }
}
