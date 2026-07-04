package pe.edu.untels.certificadosdrsu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.untels.certificadosdrsu.entities.Participacion;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipacionRepository extends JpaRepository<Participacion, Long> {
    List<Participacion> findByIdProyectoOrderByTipoParticipacionAsc(Long idProyecto);

    Optional<Participacion> findByIdProyectoAndIdParticipante(Long idProyecto, Long idParticipante);

    boolean existsByIdProyectoAndIdParticipante(Long idProyecto, Long idParticipante);
}
