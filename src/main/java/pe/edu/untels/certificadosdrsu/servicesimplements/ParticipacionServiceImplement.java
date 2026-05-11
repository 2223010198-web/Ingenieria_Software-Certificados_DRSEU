package pe.edu.untels.certificadosdrsu.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.untels.certificadosdrsu.entities.Participacion;
import pe.edu.untels.certificadosdrsu.repositories.ParticipacionRepository;
import pe.edu.untels.certificadosdrsu.servicesinterface.ParticipacionService;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipacionServiceImplement implements ParticipacionService {
    @Autowired
    private ParticipacionRepository participacionRepository;

    @Override
    public List<Participacion> list(){
        return participacionRepository.findAll();
    }

    @Override
    public Participacion insert(Participacion p) {
        return participacionRepository.save(p);
    }

    @Override
    public Optional<Participacion> listId(Long id) {
        return participacionRepository.findById(id);
    }

    @Override
    public Participacion update(Participacion p) {
        return participacionRepository.save(p);
    }

    @Override
    public void delete(Long id) {
        participacionRepository.deleteById(id);
    }
}
