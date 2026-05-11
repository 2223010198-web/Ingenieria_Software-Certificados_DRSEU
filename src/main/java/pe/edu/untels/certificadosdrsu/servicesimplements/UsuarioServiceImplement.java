package pe.edu.untels.certificadosdrsu.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.untels.certificadosdrsu.entities.Usuario;
import pe.edu.untels.certificadosdrsu.repositories.IUsuarioRepository;
import pe.edu.untels.certificadosdrsu.servicesinterface.IUsuarioService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
  public Optional<Usuario> listId(UUID id) {
    return uR.findById(id);
  }

  @Override
  public Usuario update(Usuario usuario) {
    return uR.save(usuario);
  }

  @Override
  public void delete(UUID id) {
    uR.deleteById(id);
  }
}
