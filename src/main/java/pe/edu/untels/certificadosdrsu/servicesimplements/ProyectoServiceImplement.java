package pe.edu.untels.certificadosdrsu.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.untels.certificadosdrsu.entities.Proyecto;
import pe.edu.untels.certificadosdrsu.repositories.IProyectoRepository;
import pe.edu.untels.certificadosdrsu.servicesinterface.IProyectoService;

import java.util.List;

@Service
public class ProyectoServiceImplement implements IProyectoService {

  @Autowired
  private IProyectoRepository proyectoRepository;

  @Override
  public List<Proyecto> list() {
    return proyectoRepository.findAll();
  }

}
