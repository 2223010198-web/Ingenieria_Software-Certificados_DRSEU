package pe.edu.untels.certificadosdrsu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.untels.certificadosdrsu.entities.PlantillaPdf;

@Repository
public interface IPlantillaPdfRepository extends JpaRepository<PlantillaPdf, Long> {
}