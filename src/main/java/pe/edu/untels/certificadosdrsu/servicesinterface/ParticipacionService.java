package pe.edu.untels.certificadosdrsu.servicesinterface;

import pe.edu.untels.certificadosdrsu.dtos.ParticipanteProyectoDTO;
import pe.edu.untels.certificadosdrsu.dtos.ParticipanteProyectoRequestDTO;

import java.util.List;
import java.util.Optional;

public interface ParticipacionService {
    public List<ParticipanteProyectoDTO> listByProject(Long idProyecto);
    public Optional<ParticipanteProyectoDTO> findByProjectAndParticipant(Long idProyecto, Long idParticipante);
    public ParticipanteProyectoDTO assignToProject(Long idProyecto, ParticipanteProyectoRequestDTO request);
    public ParticipanteProyectoDTO updateProjectParticipant(Long idProyecto, Long idParticipante, ParticipanteProyectoRequestDTO request);
    public void removeFromProject(Long idProyecto, Long idParticipante);
}
