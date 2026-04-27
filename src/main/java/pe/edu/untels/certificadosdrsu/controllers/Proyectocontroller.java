package pe.edu.untels.certificadosdrsu.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.untels.certificadosdrsu.dtos.Proyectodto;
import pe.edu.untels.certificadosdrsu.servicesinterface.Iproyectoservice;

import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
public class Proyectocontroller {
    @Autowired
    private Iproyectoservice proyectoService;

    @GetMapping("/lista")
    public ResponseEntity<List<Proyectodto>> listar() {
        ModelMapper m = new ModelMapper();
        List<Proyectodto> listaProyectos = proyectoService.list()
                .stream().map(p -> m.map(p, Proyectodto.class))
                .toList();

        return ResponseEntity.ok(listaProyectos);
    }
}
