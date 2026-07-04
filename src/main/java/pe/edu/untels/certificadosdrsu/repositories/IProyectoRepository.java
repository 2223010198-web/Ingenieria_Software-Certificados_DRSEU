package pe.edu.untels.certificadosdrsu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pe.edu.untels.certificadosdrsu.entities.Proyecto;

import java.util.List;

@Repository
public interface IProyectoRepository extends JpaRepository<Proyecto, Long>, JpaSpecificationExecutor<Proyecto> {

    List<Proyecto> findTop10ByTituloContainingIgnoreCaseOrderByTituloAsc(String titulo);
}
