package pe.edu.untels.certificadosdrsu.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.untels.certificadosdrsu.entities.TipoProyecto;
import pe.edu.untels.certificadosdrsu.repositories.ITipoProyectoRepository;
import pe.edu.untels.certificadosdrsu.servicesinterface.ITipoProyectoService;

import java.util.List;
import java.util.Optional;

@Service
public class TipoProyectoServiceImplement implements ITipoProyectoService {
    @Autowired
    private ITipoProyectoRepository repository;

    @Override
    public List<TipoProyecto> list() {
        return repository.findAll();
    }

    @Override
    public TipoProyecto insert(TipoProyecto t) {
        return repository.save(t);
    }

    @Override
    public Optional<TipoProyecto> listId(int id) {
        return repository.findById(id);
    }

    @Override
    public TipoProyecto update(TipoProyecto t) {
        return repository.save(t);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }
}
