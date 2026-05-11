package pe.edu.untels.certificadosdrsu.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.certificadosdrsu.dtos.PlantillaPdfDTO;
import pe.edu.untels.certificadosdrsu.entities.PlantillaPdf;
import pe.edu.untels.certificadosdrsu.servicesinterface.IPlantillaPdfService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/plantillas-pdf")
public class PlantillaPdfController {

    @Autowired
    private IPlantillaPdfService service;

    @GetMapping
    public ResponseEntity<List<PlantillaPdfDTO>> listar() {
        ModelMapper m = new ModelMapper();
        List<PlantillaPdfDTO> lista = service.list().stream()
                .map(p -> m.map(p, PlantillaPdfDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<PlantillaPdfDTO> registrar(@RequestBody PlantillaPdfDTO dto) {
        ModelMapper m = new ModelMapper();
        PlantillaPdf p = m.map(dto, PlantillaPdf.class);
        PlantillaPdf guardado = service.insert(p);
        PlantillaPdfDTO responseDTO = m.map(guardado, PlantillaPdfDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        ModelMapper m = new ModelMapper();
        Optional<PlantillaPdf> entidad = service.listId(id);
        
        if (entidad.isPresent()) {
            PlantillaPdfDTO dto = m.map(entidad.get(), PlantillaPdfDTO.class);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plantilla PDF no encontrada");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable int id, @RequestBody PlantillaPdfDTO dto) {
        ModelMapper m = new ModelMapper();
        Optional<PlantillaPdf> existente = service.listId(id);
        
        if (existente.isPresent()) {
            PlantillaPdf p = m.map(dto, PlantillaPdf.class);
            p.setId(id);
            PlantillaPdf actualizado = service.update(p);
            PlantillaPdfDTO responseDTO = m.map(actualizado, PlantillaPdfDTO.class);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plantilla PDF no encontrada");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable int id) {
        Optional<PlantillaPdf> existente = service.listId(id);
        
        if (existente.isPresent()) {
            service.delete(id);
            return ResponseEntity.ok("Plantilla PDF eliminada correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plantilla PDF no encontrada");
        }
    }
}