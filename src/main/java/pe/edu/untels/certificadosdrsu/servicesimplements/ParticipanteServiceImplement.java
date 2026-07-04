package pe.edu.untels.certificadosdrsu.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.untels.certificadosdrsu.entities.Participante;
import pe.edu.untels.certificadosdrsu.repositories.ParticipanteRepository;
import pe.edu.untels.certificadosdrsu.servicesinterface.ParticipanteService;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipanteServiceImplement implements ParticipanteService {
    @Autowired
    private ParticipanteRepository participanteRepository;

    @Override
    public List<Participante> list(){
        return participanteRepository.findAll();
    }

    @Override
    public List<Participante> search(String q, boolean includeInactive) {
        return participanteRepository.buscar(normalizeNullable(q), includeInactive);
    }

    @Override
    @Transactional
    public Participante insert(Participante p) {
        normalize(p);
        validateForSave(p, null);
        p.setActivo(true);
        return participanteRepository.save(p);
    }

    @Override
    public Optional<Participante> listId(Long id) {
        return participanteRepository.findById(id);
    }

    @Override
    public Optional<Participante> findByDni(String dni) {
        String normalizedDni = normalizeNullable(dni);
        if (normalizedDni == null) {
            return Optional.empty();
        }
        return participanteRepository.findByDniIgnoreCase(normalizedDni);
    }

    @Override
    @Transactional
    public Participante update(Participante p) {
        Long id = p.getIdParticipante();
        Participante existente = participanteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Participante no encontrado"));

        normalize(p);
        validateForSave(p, id);

        existente.setDni(p.getDni());
        existente.setNombres(p.getNombres());
        existente.setApellidos(p.getApellidos());
        existente.setEmail(p.getEmail());
        existente.setCelular(p.getCelular());
        existente.setCategoria(p.getCategoria());
        existente.setActivo(p.isActivo());

        return participanteRepository.save(existente);
    }

    @Override
    @Transactional
    public Participante deactivate(Long id) {
        Participante participante = participanteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Participante no encontrado"));
        participante.setActivo(false);
        return participanteRepository.save(participante);
    }

    public Participante saveFromProject(Participante p) {
        normalize(p);
        validateForSave(p, p.getIdParticipante());
        return participanteRepository.save(p);
    }

    @Override
    public void delete(Long id) {
        participanteRepository.deleteById(id);
    }

    private void validateForSave(Participante p, Long currentId) {
        require(p.getDni(), "El DNI es obligatorio");
        require(p.getNombres(), "Los nombres son obligatorios");
        require(p.getApellidos(), "Los apellidos son obligatorios");
        require(p.getEmail(), "El correo electrónico es obligatorio");

        if (!p.getDni().matches("\\d{8,12}")) {
            throw new IllegalArgumentException("El DNI debe tener entre 8 y 12 dígitos");
        }

        if (p.getEmail() != null && !p.getEmail().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException("El correo electrónico no tiene un formato válido");
        }

        boolean duplicatedDni = currentId == null
                ? participanteRepository.existsByDniIgnoreCase(p.getDni())
                : participanteRepository.existsByDniIgnoreCaseAndIdParticipanteNot(p.getDni(), currentId);
        if (duplicatedDni) {
            throw new IllegalArgumentException("Ya existe un participante con ese DNI");
        }

        boolean duplicatedEmail = p.getEmail() != null && (currentId == null
                ? participanteRepository.existsByEmailIgnoreCase(p.getEmail())
                : participanteRepository.existsByEmailIgnoreCaseAndIdParticipanteNot(p.getEmail(), currentId));
        if (duplicatedEmail) {
            throw new IllegalArgumentException("Ya existe otro participante con ese correo electrónico");
        }
    }

    private void normalize(Participante p) {
        p.setDni(normalizeNullable(p.getDni()));
        p.setNombres(normalizeNullable(p.getNombres()));
        p.setApellidos(normalizeNullable(p.getApellidos()));
        p.setEmail(normalizeNullable(p.getEmail()));
        p.setCelular(normalizeNullable(p.getCelular()));

        if (p.getEmail() != null) {
            p.setEmail(p.getEmail().toLowerCase());
        }
    }

    private String normalizeNullable(String value) {
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
