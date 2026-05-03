package pe.edu.untels.certificadosdrsu.servicesinterface;

import pe.edu.untels.certificadosdrsu.entities.Proyecto;

import java.util.List;
import java.util.Optional;

public interface IProyectoService {
    public List<Proyecto> list();
    public Proyecto insert(Proyecto p);
    public Optional<Proyecto> listId(int id);
    public Proyecto update(Proyecto p);
    public void delete(int id);
}
