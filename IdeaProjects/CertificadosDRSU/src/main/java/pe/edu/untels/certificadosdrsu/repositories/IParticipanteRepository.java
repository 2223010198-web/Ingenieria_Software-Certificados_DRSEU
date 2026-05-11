package pe.edu.untels.certificadosdrsu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.untels.certificadosdrsu.entities.Participante;

import java.util.UUID;


@Repository
public interface IParticipanteRepository extends JpaRepository<Participante, Integer> {
}