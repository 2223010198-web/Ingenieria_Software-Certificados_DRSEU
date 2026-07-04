package pe.edu.untels.certificadosdrsu.repositories;

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

    Optional<Participante> findByDniIgnoreCase(String dni);

    boolean existsByDniIgnoreCase(String dni);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByDniIgnoreCaseAndIdParticipanteNot(String dni, Long idParticipante);

    boolean existsByEmailIgnoreCaseAndIdParticipanteNot(String email, Long idParticipante);

    @Query("""
            SELECT p FROM Participante p
            WHERE (:includeInactive = true OR p.activo = true)
              AND (
                :q IS NULL OR :q = ''
                OR LOWER(p.dni) LIKE LOWER(CONCAT('%', :q, '%'))
                OR LOWER(p.nombres) LIKE LOWER(CONCAT('%', :q, '%'))
                OR LOWER(p.apellidos) LIKE LOWER(CONCAT('%', :q, '%'))
                OR LOWER(p.email) LIKE LOWER(CONCAT('%', :q, '%'))
              )
            ORDER BY p.apellidos ASC, p.nombres ASC
            """)
    List<Participante> buscar(@Param("q") String q, @Param("includeInactive") boolean includeInactive);
}
