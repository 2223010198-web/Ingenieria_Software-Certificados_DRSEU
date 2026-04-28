package pe.edu.untels.certificadosdrsu.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.certificadosdrsu.dtos.UsuarioDTO;
import pe.edu.untels.certificadosdrsu.entities.Usuario;
import pe.edu.untels.certificadosdrsu.servicesinterface.IUsuarioService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private IUsuarioService uS;

    @PostMapping
    public void registrar(@RequestBody UsuarioDTO dto) {
        ModelMapper m = new ModelMapper();
        Usuario u = m.map(dto, Usuario.class);
        uS.insert(u);
    }

    @GetMapping
    public List<UsuarioDTO> listar() {
        return uS.list().stream().map(y -> {
            ModelMapper m = new ModelMapper();
            return m.map(y, UsuarioDTO.class);
        }).collect(Collectors.toList());
    }
}