package pe.edu.untels.certificadosdrsu.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.untels.certificadosdrsu.entities.Participante;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, Long> {
    Optional<Participante> findByEmail(String email);

    @Query("SELECT p FROM Participante p WHERE p.activo = true AND ("
            + "LOWER(p.nombres) LIKE LOWER(CONCAT('%', :q, '%')) "
            + "OR LOWER(p.apellidos) LIKE LOWER(CONCAT('%', :q, '%')) "
            + "OR LOWER(p.email) LIKE LOWER(CONCAT('%', :q, '%'))) "
            + "ORDER BY p.apellidos, p.nombres")
    List<Participante> buscarSugerencias(@Param("q") String q, Pageable pageable);
}