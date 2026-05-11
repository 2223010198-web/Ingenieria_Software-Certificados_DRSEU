package pe.edu.untels.certificadosdrsu.servicesinterface;

import pe.edu.untels.certificadosdrsu.entities.Usuario;
import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
  public void insert(Usuario usuario);

  public List<Usuario> list();

  public Optional<Usuario> listId(Long id);

  public Usuario update(Usuario usuario);

  public void delete(Long id);
}
