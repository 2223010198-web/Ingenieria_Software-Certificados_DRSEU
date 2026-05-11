package pe.edu.untels.certificadosdrsu.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.certificadosdrsu.dtos.ParticipanteDTO;
import pe.edu.untels.certificadosdrsu.dtos.ParticipanteInsertDTO;
import pe.edu.untels.certificadosdrsu.entities.Participante;
import pe.edu.untels.certificadosdrsu.servicesinterfaces.IParticipanteService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/participantes")
public class ParticipanteController {
    @Autowired
    private IParticipanteService pS;

    @PostMapping
    public void registrar(@RequestBody ParticipanteInsertDTO dto) {
        ModelMapper m = new ModelMapper();
        Participante p = m.map(dto, Participante.class);
        pS.insert(p);
    }

    @GetMapping
    public List<ParticipanteDTO> listar() {
        return pS.list().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, ParticipanteDTO.class);
        }).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable("id") Integer id) {
        pS.delete(id);
    }

    @PutMapping
    public void modificar(@RequestBody ParticipanteDTO dto) {
        ModelMapper m = new ModelMapper();
        Participante p = m.map(dto, Participante.class);
        pS.insert(p);
    }

    @GetMapping("/{id}")
    public ParticipanteDTO listarId(@PathVariable("id") Integer id) {
        ModelMapper m = new ModelMapper();
        Participante p = pS.listId(id);
        return m.map(p, ParticipanteDTO.class);
    }
}