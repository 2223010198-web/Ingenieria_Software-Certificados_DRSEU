package pe.edu.untels.certificadosdrsu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
<<<<<<<< HEAD:src/main/java/pe/edu/untels/certificadosdrsu/repositories/ICertificadoRepository.java
import pe.edu.untels.certificadosdrsu.entities.Certificado;

@Repository
public interface ICertificadoRepository extends JpaRepository<Certificado, Long> {
========
import pe.edu.untels.certificadosdrsu.entities.Participacion;

@Repository
public interface ParticipacionRepository extends JpaRepository<Participacion, Long> {

>>>>>>>> Jorge_Rafael_Roncal_Saravia:src/main/java/pe/edu/untels/certificadosdrsu/repositories/ParticipacionRepository.java
}
