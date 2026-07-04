package pe.edu.untels.certificadosdrsu.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
<<<<<<<< HEAD:src/main/java/pe/edu/untels/certificadosdrsu/repositories/IEnvioCorreoRepository.java
import pe.edu.untels.certificadosdrsu.entities.EnvioCorreo;

@Repository
public interface IEnvioCorreoRepository extends JpaRepository<EnvioCorreo, Long> {
========
import pe.edu.untels.certificadosdrsu.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    boolean existsByUsername(String username);
>>>>>>>> Jorge_Rafael_Roncal_Saravia:src/main/java/pe/edu/untels/certificadosdrsu/repositories/UsuarioRepository.java
}
