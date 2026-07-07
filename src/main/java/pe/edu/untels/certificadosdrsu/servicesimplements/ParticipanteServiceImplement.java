package pe.edu.untels.certificadosdrsu.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pe.edu.untels.certificadosdrsu.entities.Participante;
import pe.edu.untels.certificadosdrsu.repositories.ParticipanteRepository;
import pe.edu.untels.certificadosdrsu.servicesinterface.ParticipanteService;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipanteServiceImplement implements ParticipanteService {
    @Autowired
    private ParticipanteRepository participanteRepository;

    @Override
    public List<Participante> list(){
        return participanteRepository.findAll();
    }

    @Override
    public Participante insert(Participante p) {
        return participanteRepository.save(p);
    }

    @Override
    public Optional<Participante> listId(Long id) {
        return participanteRepository.findById(id);
    }

    @Override
    public Participante update(Participante p) {
        return participanteRepository.save(p);
    }

    @Override
    public void delete(Long id) {
        participanteRepository.deleteById(id);
    }

    @Override
    public List<Participante> buscarSugerencias(String q) {
        return participanteRepository.buscarSugerencias(q, PageRequest.of(0, 10));
    }
}