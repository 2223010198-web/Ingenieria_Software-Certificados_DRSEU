package pe.edu.untels.certificadosdrsu.repositories;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import pe.edu.untels.certificadosdrsu.entities.Proyecto;
import pe.edu.untels.certificadosdrsu.enums.EstadoProyecto;

import java.time.LocalDate;

public class ProyectoSpecifications {

    private ProyectoSpecifications() {
    }

    public static Specification<Proyecto> conFiltros(String titulo, LocalDate fechaDesde, LocalDate fechaHasta, EstadoProyecto estado) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (titulo != null && !titulo.isBlank()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("titulo")), "%" + titulo.toLowerCase() + "%"));
            }
            if (fechaDesde != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("fechaAprobacion"), fechaDesde));
            }
            if (fechaHasta != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("fechaAprobacion"), fechaHasta));
            }
            if (estado != null) {
                predicate = cb.and(predicate, cb.equal(root.get("estado"), estado));
            }

            return predicate;
        };
    }
}
