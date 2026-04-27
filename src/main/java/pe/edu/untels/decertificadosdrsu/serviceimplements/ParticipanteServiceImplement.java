package pe.edu.untels.decertificadosdrsu.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.untels.decertificadosdrsu.entities.Participante;
import pe.edu.untels.decertificadosdrsu.repositories.ParticipanteRepository;
import pe.edu.untels.decertificadosdrsu.servicesinterfaces.ParticipanteService;

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
