package pe.edu.untels.certificadosdrsu.servicesinterface;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.edu.untels.certificadosdrsu.dtos.CertificadoDetalleDTO;
import pe.edu.untels.certificadosdrsu.dtos.SugerenciaCertificadoDTO;
import pe.edu.untels.certificadosdrsu.dtos.VerificacionCertificadoDTO;
import pe.edu.untels.certificadosdrsu.entities.Certificado;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ICertificadoService {
    List<Certificado> list();
    Certificado insert(Certificado c);
    Optional<Certificado> listId(Long id);
    Certificado update(Certificado c);
    void delete(Long id);

    Page<CertificadoDetalleDTO> buscarFiltrado(
            String participante, Long proyectoId, Long tipoId,
            String estado, String codigo, LocalDate desde, LocalDate hasta,
            Pageable pageable);

    CertificadoDetalleDTO obtenerDetalle(Long id);

    List<SugerenciaCertificadoDTO> sugerencias(String q);

    VerificacionCertificadoDTO verificar(String codigoCertificado);

    java.nio.file.Path resolverArchivoPdf(Long id);
}
