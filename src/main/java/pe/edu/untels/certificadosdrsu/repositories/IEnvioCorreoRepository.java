package pe.edu.untels.certificadosdrsu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.untels.certificadosdrsu.entities.EnvioCorreo;

@Repository
public interface IEnvioCorreoRepository extends JpaRepository<EnvioCorreo, Long> {
}
