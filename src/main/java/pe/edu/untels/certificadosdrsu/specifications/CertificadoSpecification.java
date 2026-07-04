package pe.edu.untels.certificadosdrsu.specifications;

import org.springframework.data.jpa.domain.Specification;
import pe.edu.untels.certificadosdrsu.entities.Certificado;

import java.time.LocalDate;
import java.util.List;

public class CertificadoSpecification {

    public static Specification<Certificado> idParticipanteIn(List<Long> ids) {
        return (root, query, cb) -> root.get("idParticipante").in(ids);
    }

    public static Specification<Certificado> idProyecto(Long id) {
        return (root, query, cb) -> cb.equal(root.get("idProyecto"), id);
    }

    public static Specification<Certificado> idTipoCertificado(Long id) {
        return (root, query, cb) -> cb.equal(root.get("idTipoCertificado"), id);
    }

    public static Specification<Certificado> estadoFirma(String estado) {
        return (root, query, cb) -> cb.equal(root.get("estadoFirma"), estado);
    }

    public static Specification<Certificado> codigoCertificado(String codigo) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("codigoCertificado")), "%" + codigo.toLowerCase() + "%");
    }

    public static Specification<Certificado> fechaDesde(LocalDate desde) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("fechaEmision"), desde);
    }

    public static Specification<Certificado> fechaHasta(LocalDate hasta) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("fechaEmision"), hasta);
    }
}
