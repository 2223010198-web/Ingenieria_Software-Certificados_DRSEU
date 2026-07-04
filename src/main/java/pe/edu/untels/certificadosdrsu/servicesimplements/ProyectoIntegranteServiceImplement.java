package pe.edu.untels.certificadosdrsu.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pe.edu.untels.certificadosdrsu.entities.Participacion;
import pe.edu.untels.certificadosdrsu.entities.Participante;
import pe.edu.untels.certificadosdrsu.repositories.IProyectoIntegranteRepository;
import pe.edu.untels.certificadosdrsu.servicesinterface.IProyectoIntegranteService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProyectoIntegranteServiceImplement implements IProyectoIntegranteService {

    @Autowired
    private IProyectoIntegranteRepository integranteRepository;

    @Override
    public List<Participacion> listarPorProyecto(Long idProyecto) {
        return integranteRepository.findIntegrantesByProyecto(idProyecto);
    }

    @Override
    public Optional<Participacion> listId(Long idParticipacion) {
        return integranteRepository.findById(idParticipacion);
    }

    @Override
    public boolean existeEnProyecto(Long idParticipante, Long idProyecto) {
        return integranteRepository.existsByIdParticipanteAndIdProyecto(idParticipante, idProyecto);
    }

    @Override
    public Participacion insert(Participacion p) {
        return integranteRepository.save(p);
    }

    @Override
    public Participacion update(Participacion p) {
        return integranteRepository.save(p);
    }

    @Override
    public void delete(Long idParticipacion) {
        integranteRepository.deleteById(idParticipacion);
    }

    @Override
    public List<Participante> sugerencias(String q, int limite) {
        return integranteRepository.buscarSugerencias(q, PageRequest.of(0, limite));
    }

    @Override
    public Map<Long, Long> contarIntegrantesPorProyecto() {
        return integranteRepository.contarIntegrantesPorProyecto()
                .stream()
                .collect(Collectors.toMap(fila -> (Long) fila[0], fila -> (Long) fila[1]));
    }
}
