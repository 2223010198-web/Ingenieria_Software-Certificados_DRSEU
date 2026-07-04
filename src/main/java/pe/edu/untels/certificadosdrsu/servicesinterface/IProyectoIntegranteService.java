package pe.edu.untels.certificadosdrsu.servicesinterface;

import pe.edu.untels.certificadosdrsu.entities.Participacion;
import pe.edu.untels.certificadosdrsu.entities.Participante;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IProyectoIntegranteService {
    public List<Participacion> listarPorProyecto(Long idProyecto);
    public Optional<Participacion> listId(Long idParticipacion);
    public boolean existeEnProyecto(Long idParticipante, Long idProyecto);
    public Participacion insert(Participacion p);
    public Participacion update(Participacion p);
    public void delete(Long idParticipacion);
    public List<Participante> sugerencias(String q, int limite);
    public Map<Long, Long> contarIntegrantesPorProyecto();
}
