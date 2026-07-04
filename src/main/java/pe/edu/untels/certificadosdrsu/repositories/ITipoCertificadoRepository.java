package pe.edu.untels.certificadosdrsu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.untels.certificadosdrsu.entities.TipoCertificado;

import java.util.List;

@Repository
public interface ITipoCertificadoRepository extends JpaRepository<TipoCertificado, Long> {
    List<TipoCertificado> findByActivoTrue();
    boolean existsByNombreIgnoreCase(String nombre);
    boolean existsByNombreIgnoreCaseAndIdTipoCertificadoNot(String nombre, Long id);
}
