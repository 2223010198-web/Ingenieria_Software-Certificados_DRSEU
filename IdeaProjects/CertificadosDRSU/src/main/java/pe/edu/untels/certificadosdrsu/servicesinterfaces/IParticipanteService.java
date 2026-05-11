package pe.edu.untels.certificadosdrsu.servicesinterfaces;

import pe.edu.untels.certificadosdrsu.entities.Participante;
import java.util.List;

public interface IParticipanteService {
    public void insert(Participante participante);
    public List<Participante> list();
    public void delete(Integer id); // Cambiado a Integer
    public Participante listId(Integer id); // Cambiado a Integer
}
