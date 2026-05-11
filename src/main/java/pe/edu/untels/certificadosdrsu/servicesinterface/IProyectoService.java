package pe.edu.untels.certificadosdrsu.servicesinterface;

import pe.edu.untels.certificadosdrsu.entities.Proyecto;

import java.util.List;
import java.util.Optional;

public interface IProyectoService {
    public List<Proyecto> list();
    public Optional<Proyecto> listId(int id);
    public Proyecto insert(Proyecto proyecto);
    public Proyecto update(Proyecto proyecto);
    public void delete(int id);
}
