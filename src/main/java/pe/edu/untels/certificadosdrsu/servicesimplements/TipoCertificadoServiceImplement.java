package pe.edu.untels.certificadosdrsu.servicesimplements;

import org.springframework.stereotype.Service;
import pe.edu.untels.certificadosdrsu.dtos.TipoCertificadoDTO;
import pe.edu.untels.certificadosdrsu.dtos.TipoCertificadoInsertDTO;
import pe.edu.untels.certificadosdrsu.entities.TipoCertificado;
import pe.edu.untels.certificadosdrsu.repositories.ITipoCertificadoRepository;
import pe.edu.untels.certificadosdrsu.servicesinterface.ITipoCertificadoService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TipoCertificadoServiceImplement implements ITipoCertificadoService {

    private final ITipoCertificadoRepository repo;

    public TipoCertificadoServiceImplement(ITipoCertificadoRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<TipoCertificadoDTO> listarActivos() {
        return repo.findByActivoTrue().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TipoCertificadoDTO crear(TipoCertificadoInsertDTO dto) {
        if (repo.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe un tipo de certificado con ese nombre");
        }
        TipoCertificado entity = new TipoCertificado();
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setEsPredeterminado(dto.getEsPredeterminado() != null && dto.getEsPredeterminado());
        entity.setActivo(true);
        entity.setCreatedAt(LocalDateTime.now());
        return toDTO(repo.save(entity));
    }

    @Override
    public TipoCertificadoDTO actualizar(Long id, TipoCertificadoInsertDTO dto) {
        TipoCertificado entity = repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tipo de certificado no encontrado: " + id));
        if (repo.existsByNombreIgnoreCaseAndIdTipoCertificadoNot(dto.getNombre(), id)) {
            throw new IllegalArgumentException("Ya existe un tipo de certificado con ese nombre");
        }
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        if (dto.getEsPredeterminado() != null) {
            entity.setEsPredeterminado(dto.getEsPredeterminado());
        }
        return toDTO(repo.save(entity));
    }

    @Override
    public void desactivar(Long id) {
        TipoCertificado entity = repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tipo de certificado no encontrado: " + id));
        entity.setActivo(false);
        repo.save(entity);
    }

    private TipoCertificadoDTO toDTO(TipoCertificado e) {
        TipoCertificadoDTO dto = new TipoCertificadoDTO();
        dto.setIdTipoCertificado(e.getIdTipoCertificado());
        dto.setNombre(e.getNombre());
        dto.setDescripcion(e.getDescripcion());
        dto.setEsPredeterminado(e.getEsPredeterminado());
        dto.setActivo(e.getActivo());
        dto.setCreatedAt(e.getCreatedAt());
        return dto;
    }
}
