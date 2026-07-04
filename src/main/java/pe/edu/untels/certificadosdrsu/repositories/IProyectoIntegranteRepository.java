package pe.edu.untels.certificadosdrsu.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.untels.certificadosdrsu.entities.Participacion;
import pe.edu.untels.certificadosdrsu.entities.Participante;

import java.util.List;

/**
 * Repositorio exclusivo del Módulo 2 (Gestión de proyectos) para consultar
 * los integrantes de un proyecto. No reemplaza a ParticipacionRepository.
 */
@Repository
public interface IProyectoIntegranteRepository extends JpaRepository<Participacion, Long> {

    // HU-13: integrantes de un proyecto ordenados alfabéticamente
    @Query("""
            SELECT pa FROM Participacion pa
            JOIN FETCH pa.participante p
            WHERE pa.idProyecto = :idProyecto
            ORDER BY LOWER(p.apellidos) ASC, LOWER(p.nombres) ASC
            """)
    List<Participacion> findIntegrantesByProyecto(@Param("idProyecto") Long idProyecto);

    boolean existsByIdParticipanteAndIdProyecto(Long idParticipante, Long idProyecto);

    // HU-06: conteo de integrantes por proyecto en una sola consulta (evita N+1)
    @Query("SELECT pa.idProyecto, COUNT(pa) FROM Participacion pa GROUP BY pa.idProyecto")
    List<Object[]> contarIntegrantesPorProyecto();

    // HU-19: sugerencias de participantes activos por nombre, apellido o email
    @Query("""
            SELECT p FROM Participante p
            WHERE p.activo = true
              AND (LOWER(p.nombres) LIKE LOWER(CONCAT('%', :q, '%'))
                OR LOWER(p.apellidos) LIKE LOWER(CONCAT('%', :q, '%'))
                OR LOWER(p.email) LIKE LOWER(CONCAT('%', :q, '%')))
            ORDER BY LOWER(p.apellidos) ASC, LOWER(p.nombres) ASC
            """)
    List<Participante> buscarSugerencias(@Param("q") String q, Pageable pageable);
}
