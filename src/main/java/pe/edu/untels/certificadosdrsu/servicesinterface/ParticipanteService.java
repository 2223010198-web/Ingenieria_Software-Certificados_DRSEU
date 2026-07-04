package pe.edu.untels.certificadosdrsu.servicesinterface;

import pe.edu.untels.certificadosdrsu.entities.Participante;

import java.util.List;
import java.util.Optional;

public interface ParticipanteService {
    public List<Participante> list();
    public List<Participante> search(String q, boolean includeInactive);
    public Participante insert(Participante p);
    public Optional<Participante> listId(Long id);
    public Optional<Participante> findByDni(String dni);
    public Participante update(Participante p);
    public void delete(Long id);
    public Participante deactivate(Long id);
}
