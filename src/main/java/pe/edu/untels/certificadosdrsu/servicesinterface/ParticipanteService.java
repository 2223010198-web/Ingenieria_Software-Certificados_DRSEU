package pe.edu.untels.certificadosdrsu.servicesinterface;

import pe.edu.untels.certificadosdrsu.entities.Participante;

import java.util.List;
import java.util.Optional;

public interface ParticipanteService {
    public List<Participante> list();
    public Participante insert(Participante p);
    public Optional<Participante> listId(int id);
    public Participante update(Participante p);
    public void delete(int id);
}