package pe.edu.untels.certificadosdrsu.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.untels.certificadosdrsu.entities.UsuarioAuditoria;
import pe.edu.untels.certificadosdrsu.repositories.IUsuarioAuditoriaRepository;
import pe.edu.untels.certificadosdrsu.servicesinterface.IUsuarioAuditoriaService;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioAuditoriaServiceImplement implements IUsuarioAuditoriaService {

    @Autowired
    private IUsuarioAuditoriaRepository repository;

    @Override
    public List<UsuarioAuditoria> list() {
        return repository.findAll();
    }

    @Override
    public UsuarioAuditoria insert(UsuarioAuditoria u) {
        return repository.save(u);
    }

    @Override
    public Optional<UsuarioAuditoria> listId(int id) {
        return repository.findById(id);
    }

    @Override
    public UsuarioAuditoria update(UsuarioAuditoria u) {
        return repository.save(u);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }
}