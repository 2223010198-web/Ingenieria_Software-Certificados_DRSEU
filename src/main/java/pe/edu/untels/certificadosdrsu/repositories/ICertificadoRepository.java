package pe.edu.untels.certificadosdrsu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pe.edu.untels.certificadosdrsu.entities.Certificado;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICertificadoRepository extends JpaRepository<Certificado, Long>, JpaSpecificationExecutor<Certificado> {
    Optional<Certificado> findByCodigoCertificado(String codigoCertificado);
    List<Certificado> findTop10ByCodigoCertificadoContainingIgnoreCaseOrNumeroFolioContainingIgnoreCase(
            String codigo, String folio);
}
