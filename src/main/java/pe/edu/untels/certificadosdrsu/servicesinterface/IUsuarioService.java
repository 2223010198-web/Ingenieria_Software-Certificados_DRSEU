package pe.edu.untels.certificadosdrsu.servicesinterface;

import pe.edu.untels.certificadosdrsu.entities.Usuario;
import java.util.List;

public interface IUsuarioService {
  public void insert(Usuario usuario);

  public List<Usuario> list();
}
