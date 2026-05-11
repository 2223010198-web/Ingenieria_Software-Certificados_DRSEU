package pe.edu.untels.certificadosdrsu.servicesinterface;

import pe.edu.untels.certificadosdrsu.entities.TipoProyecto;

import java.util.List;
import java.util.Optional;

public interface ITipoProyectoService {
    public List<TipoProyecto> list();
    public Optional<TipoProyecto> listId(int id);
    public TipoProyecto insert(TipoProyecto tipoproyecto);
    public TipoProyecto update(TipoProyecto tipoproyecto);
    public void delete(int id);
}
