package pe.edu.untels.certificadosdrsu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.untels.certificadosdrsu.entities.Participacion;

import java.util.List;

@Repository
public interface ParticipacionRepository extends JpaRepository<Participacion, Long> {

    List<Participacion> findByIdProyecto(Long idProyecto);

    boolean existsByIdProyectoAndIdParticipante(Long idProyecto, Long idParticipante);

    long countByIdProyecto(Long idProyecto);
}
