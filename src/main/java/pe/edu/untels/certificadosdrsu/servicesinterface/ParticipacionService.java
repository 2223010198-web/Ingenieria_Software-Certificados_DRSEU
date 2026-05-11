package pe.edu.untels.certificadosdrsu.servicesinterface;

import pe.edu.untels.certificadosdrsu.entities.Participacion;

import java.util.List;
import java.util.Optional;

public interface ParticipacionService {
    public List<Participacion> list();
    public Participacion insert(Participacion p);
    public Optional<Participacion> listId(Long id);
    public Participacion update(Participacion p);
    public void delete(Long id);
}
