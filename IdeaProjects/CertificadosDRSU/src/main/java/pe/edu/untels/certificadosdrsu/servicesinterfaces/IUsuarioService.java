package pe.edu.untels.certificadosdrsu.servicesinterfaces;

import pe.edu.untels.certificadosdrsu.entities.Usuario;
import java.util.List;

public interface IUsuarioService {
    public void insert(Usuario usuario);
    public List<Usuario> list();
    public void delete(Integer id);
    public Usuario listId(Integer id);
}