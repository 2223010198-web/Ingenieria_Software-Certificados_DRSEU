package pe.edu.untels.certificadosdrsu.servicesinterface;

import pe.edu.untels.certificadosdrsu.entities.Usuario;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUsuarioService {
  public void insert(Usuario usuario);

  public List<Usuario> list();

  public Optional<Usuario> listId(UUID id);

  public Usuario update(Usuario usuario);

  public void delete(UUID id);
}
