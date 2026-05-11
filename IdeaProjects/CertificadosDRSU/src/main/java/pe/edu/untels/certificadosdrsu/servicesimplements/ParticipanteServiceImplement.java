package pe.edu.untels.certificadosdrsu.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.untels.certificadosdrsu.entities.Participante;
import pe.edu.untels.certificadosdrsu.repositories.IParticipanteRepository;
import pe.edu.untels.certificadosdrsu.servicesinterfaces.IParticipanteService;

import java.util.List;

@Service
public class ParticipanteServiceImplement implements IParticipanteService {
    @Autowired
    private IParticipanteRepository pR;

    @Override
    public void insert(Participante participante) {
        pR.save(participante);
    }

    @Override
    public List<Participante> list() {
        return pR.findAll();
    }

    @Override
    public void delete(Integer id) {
        pR.deleteById(id);
    }

    @Override
    public Participante listId(Integer id) {
        return pR.findById(id).orElse(new Participante());
    }
}
