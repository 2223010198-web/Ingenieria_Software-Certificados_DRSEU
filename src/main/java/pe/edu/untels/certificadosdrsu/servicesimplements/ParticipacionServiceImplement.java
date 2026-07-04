package pe.edu.untels.certificadosdrsu.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.untels.certificadosdrsu.dtos.ParticipanteProyectoDTO;
import pe.edu.untels.certificadosdrsu.dtos.ParticipanteProyectoRequestDTO;
import pe.edu.untels.certificadosdrsu.entities.Participante;
import pe.edu.untels.certificadosdrsu.entities.Participacion;
import pe.edu.untels.certificadosdrsu.repositories.IProyectoRepository;
import pe.edu.untels.certificadosdrsu.repositories.ParticipanteRepository;
import pe.edu.untels.certificadosdrsu.repositories.ParticipacionRepository;
import pe.edu.untels.certificadosdrsu.servicesinterface.ParticipanteService;
import pe.edu.untels.certificadosdrsu.servicesinterface.ParticipacionService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ParticipacionServiceImplement implements ParticipacionService {
    @Autowired
    private ParticipacionRepository participacionRepository;

    @Autowired
    private ParticipanteRepository participanteRepository;

    @Autowired
    private ParticipanteService participanteService;

    @Autowired
    private IProyectoRepository proyectoRepository;

    @Override
    public List<ParticipanteProyectoDTO> listByProject(Long idProyecto) {
        validateProjectExists(idProyecto);
        return participacionRepository.findByIdProyectoOrderByTipoParticipacionAsc(idProyecto)
                .stream()
                .map(this::toProjectDto)
                .sorted(Comparator
                        .comparing(ParticipanteProyectoDTO::getApellidos, Comparator.nullsLast(String::compareToIgnoreCase))
                        .thenComparing(ParticipanteProyectoDTO::getNombres, Comparator.nullsLast(String::compareToIgnoreCase)))
                .toList();
    }

    @Override
    public Optional<ParticipanteProyectoDTO> findByProjectAndParticipant(Long idProyecto, Long idParticipante) {
        validateProjectExists(idProyecto);
        return participacionRepository.findByIdProyectoAndIdParticipante(idProyecto, idParticipante)
                .map(this::toProjectDto);
    }

    @Override
    @Transactional
    public ParticipanteProyectoDTO assignToProject(Long idProyecto, ParticipanteProyectoRequestDTO request) {
        validateProjectExists(idProyecto);
        require(request.getTipoParticipacion(), "El tipo de participación es obligatorio");

        Participante participante = resolveParticipant(request);

        if (participacionRepository.existsByIdProyectoAndIdParticipante(idProyecto, participante.getIdParticipante())) {
            throw new IllegalArgumentException("El participante ya está asignado a este proyecto");
        }

        Participacion participacion = new Participacion();
        participacion.setIdProyecto(idProyecto);
        participacion.setIdParticipante(participante.getIdParticipante());
        participacion.setTipoParticipacion(normalize(request.getTipoParticipacion()));
        participacion.setDescripcionParticipante(normalize(request.getDescripcionParticipante()));

        return toProjectDto(participacionRepository.save(participacion));
    }

    @Override
    @Transactional
    public ParticipanteProyectoDTO updateProjectParticipant(
            Long idProyecto,
            Long idParticipante,
            ParticipanteProyectoRequestDTO request
    ) {
        validateProjectExists(idProyecto);

        Participacion participacion = participacionRepository.findByIdProyectoAndIdParticipante(idProyecto, idParticipante)
                .orElseThrow(() -> new IllegalArgumentException("Participación no encontrada"));

        Participante participante = participanteRepository.findById(idParticipante)
                .orElseThrow(() -> new IllegalArgumentException("Participante no encontrado"));

        participante.setDni(request.getDni());
        participante.setNombres(request.getNombres());
        participante.setApellidos(request.getApellidos());
        participante.setEmail(request.getEmail());
        participante.setCelular(request.getCelular());
        participante.setCategoria(request.getCategoria());
        participante.setActivo(true);
        participanteService.update(participante);

        require(request.getTipoParticipacion(), "El tipo de participación es obligatorio");
        participacion.setTipoParticipacion(normalize(request.getTipoParticipacion()));
        participacion.setDescripcionParticipante(normalize(request.getDescripcionParticipante()));

        return toProjectDto(participacionRepository.save(participacion));
    }

    @Override
    @Transactional
    public void removeFromProject(Long idProyecto, Long idParticipante) {
        validateProjectExists(idProyecto);
        Participacion participacion = participacionRepository.findByIdProyectoAndIdParticipante(idProyecto, idParticipante)
                .orElseThrow(() -> new IllegalArgumentException("Participación no encontrada"));
        participacionRepository.delete(participacion);
    }

    private Participante resolveParticipant(ParticipanteProyectoRequestDTO request) {
        if (request.getIdParticipante() != null) {
            return participanteRepository.findById(request.getIdParticipante())
                    .orElseThrow(() -> new IllegalArgumentException("Participante no encontrado"));
        }

        String dni = normalize(request.getDni());
        if (dni != null) {
            Optional<Participante> existente = participanteRepository.findByDniIgnoreCase(dni);
            if (existente.isPresent()) {
                return existente.get();
            }
        }

        Participante participante = new Participante();
        participante.setDni(request.getDni());
        participante.setNombres(request.getNombres());
        participante.setApellidos(request.getApellidos());
        participante.setEmail(request.getEmail());
        participante.setCelular(request.getCelular());
        participante.setCategoria(request.getCategoria());
        participante.setActivo(true);
        return participanteService.insert(participante);
    }

    private ParticipanteProyectoDTO toProjectDto(Participacion participacion) {
        Participante participante = participanteRepository.findById(participacion.getIdParticipante())
                .orElseThrow(() -> new IllegalArgumentException("Participante no encontrado"));

        ParticipanteProyectoDTO dto = new ParticipanteProyectoDTO();
        dto.setParticipacionId(participacion.getId());
        dto.setIdParticipante(participante.getIdParticipante());
        dto.setIdProyecto(participacion.getIdProyecto());
        dto.setDni(participante.getDni());
        dto.setNombres(participante.getNombres());
        dto.setApellidos(participante.getApellidos());
        dto.setNombreCompleto(buildFullName(participante));
        dto.setEmail(participante.getEmail());
        dto.setCelular(participante.getCelular());
        dto.setCategoria(participante.getCategoria());
        dto.setActivo(participante.isActivo());
        dto.setTipoParticipacion(participacion.getTipoParticipacion());
        dto.setDescripcionParticipante(participacion.getDescripcionParticipante());
        return dto;
    }

    private void validateProjectExists(Long idProyecto) {
        if (!proyectoRepository.existsById(idProyecto)) {
            throw new IllegalArgumentException("Proyecto no encontrado");
        }
    }

    private String buildFullName(Participante participante) {
        String nombres = participante.getNombres() == null ? "" : participante.getNombres().trim();
        String apellidos = participante.getApellidos() == null ? "" : participante.getApellidos().trim();
        return (nombres + " " + apellidos).trim();
    }

    private String normalize(String value) {
        if (value == null) {
            return null;
        }
        String normalized = value.trim();
        return normalized.isEmpty() ? null : normalized;
    }

    private void require(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }
}
