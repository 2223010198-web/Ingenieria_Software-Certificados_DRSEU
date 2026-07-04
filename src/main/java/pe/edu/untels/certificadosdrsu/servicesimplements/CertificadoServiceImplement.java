package pe.edu.untels.certificadosdrsu.servicesimplements;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pe.edu.untels.certificadosdrsu.dtos.CertificadoDetalleDTO;
import pe.edu.untels.certificadosdrsu.dtos.SugerenciaCertificadoDTO;
import pe.edu.untels.certificadosdrsu.dtos.VerificacionCertificadoDTO;
import pe.edu.untels.certificadosdrsu.entities.Certificado;
import pe.edu.untels.certificadosdrsu.entities.Participante;
import pe.edu.untels.certificadosdrsu.entities.Proyecto;
import pe.edu.untels.certificadosdrsu.entities.TipoCertificado;
import pe.edu.untels.certificadosdrsu.repositories.ICertificadoRepository;
import pe.edu.untels.certificadosdrsu.repositories.ITipoCertificadoRepository;
import pe.edu.untels.certificadosdrsu.repositories.IProyectoRepository;
import pe.edu.untels.certificadosdrsu.repositories.ParticipanteRepository;
import pe.edu.untels.certificadosdrsu.servicesinterface.ICertificadoService;
import pe.edu.untels.certificadosdrsu.specifications.CertificadoSpecification;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CertificadoServiceImplement implements ICertificadoService {

    private final ICertificadoRepository certificadoRepository;
    private final ParticipanteRepository participanteRepository;
    private final IProyectoRepository proyectoRepository;
    private final ITipoCertificadoRepository tipoCertificadoRepository;

    public CertificadoServiceImplement(
            ICertificadoRepository certificadoRepository,
            ParticipanteRepository participanteRepository,
            IProyectoRepository proyectoRepository,
            ITipoCertificadoRepository tipoCertificadoRepository) {
        this.certificadoRepository = certificadoRepository;
        this.participanteRepository = participanteRepository;
        this.proyectoRepository = proyectoRepository;
        this.tipoCertificadoRepository = tipoCertificadoRepository;
    }

    @Override
    public List<Certificado> list() {
        return certificadoRepository.findAll();
    }

    @Override
    public Certificado insert(Certificado c) {
        return certificadoRepository.save(c);
    }

    @Override
    public Optional<Certificado> listId(Long id) {
        return certificadoRepository.findById(id);
    }

    @Override
    public Certificado update(Certificado c) {
        return certificadoRepository.save(c);
    }

    @Override
    public void delete(Long id) {
        certificadoRepository.deleteById(id);
    }

    @Override
    public Page<CertificadoDetalleDTO> buscarFiltrado(
            String participante, Long proyectoId, Long tipoId,
            String estado, String codigo, LocalDate desde, LocalDate hasta,
            Pageable pageable) {

        Specification<Certificado> spec = (root, query, cb) -> cb.conjunction();

        if (participante != null && !participante.isBlank()) {
            List<Long> ids = participanteRepository
                    .findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCase(participante, participante)
                    .stream()
                    .map(Participante::getIdParticipante)
                    .collect(Collectors.toList());
            if (ids.isEmpty()) {
                return Page.empty(pageable);
            }
            spec = spec.and(CertificadoSpecification.idParticipanteIn(ids));
        }
        if (proyectoId != null) {
            spec = spec.and(CertificadoSpecification.idProyecto(proyectoId));
        }
        if (tipoId != null) {
            spec = spec.and(CertificadoSpecification.idTipoCertificado(tipoId));
        }
        if (estado != null && !estado.isBlank()) {
            spec = spec.and(CertificadoSpecification.estadoFirma(estado));
        }
        if (codigo != null && !codigo.isBlank()) {
            spec = spec.and(CertificadoSpecification.codigoCertificado(codigo));
        }
        if (desde != null) {
            spec = spec.and(CertificadoSpecification.fechaDesde(desde));
        }
        if (hasta != null) {
            spec = spec.and(CertificadoSpecification.fechaHasta(hasta));
        }

        return certificadoRepository.findAll(spec, pageable).map(this::toDetalle);
    }

    @Override
    public CertificadoDetalleDTO obtenerDetalle(Long id) {
        Certificado c = certificadoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Certificado no encontrado: " + id));
        return toDetalle(c);
    }

    @Override
    public List<SugerenciaCertificadoDTO> sugerencias(String q) {
        if (q == null || q.isBlank()) return List.of();
        return certificadoRepository
                .findTop10ByCodigoCertificadoContainingIgnoreCaseOrNumeroFolioContainingIgnoreCase(q, q)
                .stream()
                .map(c -> {
                    String nombre = resolverNombreParticipante(c.getIdParticipante());
                    return new SugerenciaCertificadoDTO(c.getId(), c.getCodigoCertificado(), c.getNumeroFolio(), nombre);
                })
                .collect(Collectors.toList());
    }

    @Override
    public VerificacionCertificadoDTO verificar(String codigoCertificado) {
        VerificacionCertificadoDTO resultado = new VerificacionCertificadoDTO();
        Optional<Certificado> opt = certificadoRepository.findByCodigoCertificado(codigoCertificado);
        if (opt.isEmpty()) {
            resultado.setValido(false);
            return resultado;
        }
        Certificado c = opt.get();
        resultado.setValido(true);
        resultado.setCodigoCertificado(c.getCodigoCertificado());
        resultado.setParticipanteNombre(resolverNombreParticipante(c.getIdParticipante()));
        resultado.setProyectoTitulo(resolverTituloProyecto(c.getIdProyecto()));
        resultado.setTipoCertificadoNombre(resolverNombreTipo(c.getIdTipoCertificado()));
        resultado.setTipoParticipacion(c.getTipoParticipacion());
        resultado.setFechaEmision(c.getFechaEmision());
        return resultado;
    }

    @Override
    public Path resolverArchivoPdf(Long id) {
        Certificado c = certificadoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Certificado no encontrado: " + id));
        String ruta = c.getArchivoFirmadoPath() != null
                ? c.getArchivoFirmadoPath()
                : c.getArchivoBorradorPath();
        if (ruta == null) {
            throw new NoSuchElementException("No hay archivo PDF disponible para el certificado: " + id);
        }
        return Paths.get(ruta);
    }

    private CertificadoDetalleDTO toDetalle(Certificado c) {
        CertificadoDetalleDTO dto = new CertificadoDetalleDTO();
        dto.setId(c.getId());
        dto.setCodigoCertificado(c.getCodigoCertificado());
        dto.setNumeroFolio(c.getNumeroFolio());
        dto.setTipoParticipacion(c.getTipoParticipacion());
        dto.setEstadoFirma(c.getEstadoFirma());
        dto.setFechaEmision(c.getFechaEmision());
        dto.setArchivoBorradorPath(c.getArchivoBorradorPath());
        dto.setArchivoFirmadoPath(c.getArchivoFirmadoPath());

        dto.setIdParticipante(c.getIdParticipante());
        dto.setParticipanteNombre(resolverNombreParticipante(c.getIdParticipante()));

        dto.setIdProyecto(c.getIdProyecto());
        dto.setProyectoTitulo(resolverTituloProyecto(c.getIdProyecto()));

        dto.setIdTipoCertificado(c.getIdTipoCertificado());
        dto.setTipoCertificadoNombre(resolverNombreTipo(c.getIdTipoCertificado()));

        return dto;
    }

    private String resolverNombreParticipante(Long idParticipante) {
        if (idParticipante == null) return null;
        return participanteRepository.findById(idParticipante)
                .map(p -> (p.getNombres() + " " + p.getApellidos()).trim())
                .orElse(null);
    }

    private String resolverTituloProyecto(Long idProyecto) {
        if (idProyecto == null) return null;
        return proyectoRepository.findById(idProyecto)
                .map(Proyecto::getTitulo)
                .orElse(null);
    }

    private String resolverNombreTipo(Long idTipo) {
        if (idTipo == null) return null;
        return tipoCertificadoRepository.findById(idTipo)
                .map(TipoCertificado::getNombre)
                .orElse(null);
    }
}
