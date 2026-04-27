package pe.edu.untels.certificadosdrsu.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.untels.certificadosdrsu.dtos.ParticipanteDTO;
import pe.edu.untels.certificadosdrsu.servicesinterface.ParticipanteService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/participantes")
public class ParticipanteController {

    @Autowired
    private ParticipanteService participanteService;

    @GetMapping("/lista")
    public ResponseEntity<List<ParticipanteDTO>> listar() {
        ModelMapper m = new ModelMapper();

        // Mapeo de la lista de entidades a la lista de DTOs
        List<ParticipanteDTO> listaParticipantes = participanteService.list()
                .stream()
                .map(participante -> m.map(participante, ParticipanteDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listaParticipantes);
    }
}