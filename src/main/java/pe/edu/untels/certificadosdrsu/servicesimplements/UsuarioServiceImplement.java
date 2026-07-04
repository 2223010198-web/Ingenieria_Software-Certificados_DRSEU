package pe.edu.untels.certificadosdrsu.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pe.edu.untels.certificadosdrsu.dtos.UserRequest;
import pe.edu.untels.certificadosdrsu.entities.Usuario;
import pe.edu.untels.certificadosdrsu.repositories.UsuarioRepository;
import pe.edu.untels.certificadosdrsu.servicesinterface.IUsuarioService;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImplement implements IUsuarioService {
  @Autowired
  private UsuarioRepository uR;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public void insert(Usuario usuario) {
    uR.save(usuario);
  }

  @Override
  public Usuario create(UserRequest request) {
    if (uR.existsByUsername(request.getUsername())) {
      throw new RuntimeException("El username ya existe");
    }

    Usuario usuario = new Usuario();
    usuario.setUsername(request.getUsername());
    usuario.setPasswordHash(passwordEncoder.encode(request.getPassword()));
    usuario.setRol(request.getRol() != null ? request.getRol() : "USER");
    usuario.setIdParticipante(request.getIdParticipante());
    usuario.setActivo(true);

    return uR.save(usuario);
  }

  @Override
  public List<Usuario> list() {
    return uR.findAll();
  }

  @Override
  public Optional<Usuario> listId(Long id) {
    return uR.findById(id);
  }

  @Override
  public Usuario update(Usuario usuario) {
    return uR.save(usuario);
  }

  @Override
  public void delete(Long id) {
    uR.deleteById(id);
  }
}
