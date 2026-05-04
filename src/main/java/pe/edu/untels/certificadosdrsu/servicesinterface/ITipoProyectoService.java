package pe.edu.untels.certificadosdrsu.servicesinterface;

import pe.edu.untels.certificadosdrsu.entities.TipoProyecto;

import java.util.List;
import java.util.Optional;

public interface ITipoProyectoService {
    public List<TipoProyecto> list();
    public TipoProyecto insert(TipoProyecto t);
    public Optional<TipoProyecto> listId(int id);
    public TipoProyecto update(TipoProyecto t);
    public void delete(int id);
}
