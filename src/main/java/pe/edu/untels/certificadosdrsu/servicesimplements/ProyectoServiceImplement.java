package pe.edu.untels.certificadosdrsu.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.untels.certificadosdrsu.entities.Proyecto;
import pe.edu.untels.certificadosdrsu.enums.EstadoProyecto;
import pe.edu.untels.certificadosdrsu.repositories.IProyectoRepository;
import pe.edu.untels.certificadosdrsu.repositories.ProyectoSpecifications;
import pe.edu.untels.certificadosdrsu.servicesinterface.IProyectoService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProyectoServiceImplement implements IProyectoService {

  @Autowired
  private IProyectoRepository proyectoRepository;

  @Override
  public List<Proyecto> list() {
    return proyectoRepository.findAll();
  }

  @Override
  public Proyecto insert(Proyecto p) {
    p.setEstado(EstadoProyecto.EN_PROCESO);
    return proyectoRepository.save(p);
  }

  @Override
  public Optional<Proyecto> listId(Long id) {
    return proyectoRepository.findById(id);
  }

  @Override
  public Proyecto update(Proyecto p) {
    return proyectoRepository.save(p);
  }

  @Override
  public void delete(Long id) {
    proyectoRepository.deleteById(id);
  }

  @Override
  public List<Proyecto> search(String titulo, LocalDate fechaDesde, LocalDate fechaHasta, EstadoProyecto estado) {
    return proyectoRepository.findAll(ProyectoSpecifications.conFiltros(titulo, fechaDesde, fechaHasta, estado));
  }

  @Override
  public List<Proyecto> sugerencias(String query) {
    return proyectoRepository.findTop10ByTituloContainingIgnoreCaseOrderByTituloAsc(query);
  }

}
