package pe.edu.untels.certificadosdrsu.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.untels.certificadosdrsu.entities.Usuario;
import pe.edu.untels.certificadosdrsu.repositories.IUsuarioRepository;
import pe.edu.untels.certificadosdrsu.servicesinterfaces.IUsuarioService;
import java.util.List;

@Service
public class UsuarioServiceImplement implements IUsuarioService {
    @Autowired
    private IUsuarioRepository uR;

    @Override
    public void insert(Usuario usuario) {
        uR.save(usuario);
    }

    @Override
    public List<Usuario> list() {
        return uR.findAll();
    }

    @Override
    public void delete(Integer id) {
        uR.deleteById(id);
    }

    @Override
    public Usuario listId(Integer id) {
        return uR.findById(id).orElse(new Usuario());
    }
}