package pe.edu.untels.certificadosdrsu.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.untels.certificadosdrsu.entities.Participante;
import pe.edu.untels.certificadosdrsu.repositories.ParticipanteRepository;
import pe.edu.untels.certificadosdrsu.servicesinterface.ParticipanteService;

import java.util.List;

@Service
public class ParticipanteServiceImplement implements ParticipanteService {
    @Autowired
    private ParticipanteRepository participanteRepository;

    @Override
    public List<Participante> list(){
        return participanteRepository.findAll();
    }
}