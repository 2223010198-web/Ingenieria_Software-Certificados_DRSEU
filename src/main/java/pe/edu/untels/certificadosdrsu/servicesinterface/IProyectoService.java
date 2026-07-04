package pe.edu.untels.certificadosdrsu.servicesinterface;

import pe.edu.untels.certificadosdrsu.entities.Proyecto;
import pe.edu.untels.certificadosdrsu.enums.EstadoProyecto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IProyectoService {
    public List<Proyecto> list();
    public Optional<Proyecto> listId(Long id);
    public Proyecto insert(Proyecto proyecto);
    public Proyecto update(Proyecto proyecto);
    public void delete(Long id);
    public List<Proyecto> search(String titulo, LocalDate fechaDesde, LocalDate fechaHasta, EstadoProyecto estado);
    public List<Proyecto> sugerencias(String query);
}
