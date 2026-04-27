package pe.edu.untels.certificadosdrsu.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.untels.certificadosdrsu.dtos.ProyectoDto;
import pe.edu.untels.certificadosdrsu.servicesinterface.IProyectoService;

import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {
  @Autowired
  private IProyectoService proyectoService;

  @GetMapping("/lista")
  public ResponseEntity<List<ProyectoDto>> listar() {
    ModelMapper m = new ModelMapper();
    List<ProyectoDto> listaProyectos = proyectoService.list()
        .stream().map(p -> m.map(p, ProyectoDto.class))
        .toList();

    return ResponseEntity.ok(listaProyectos);
  }
}
