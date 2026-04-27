package pe.edu.untels.decertificadosdrsu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.untels.decertificadosdrsu.entities.Participante;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, Integer> {
}
