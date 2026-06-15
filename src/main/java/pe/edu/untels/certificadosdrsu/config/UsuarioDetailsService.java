package pe.edu.untels.certificadosdrsu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pe.edu.untels.certificadosdrsu.entities.Usuario;
import pe.edu.untels.certificadosdrsu.repositories.UsuarioRepository;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        if (!usuario.isActivo()) {
            throw new DisabledException("Usuario inactivo");
        }

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPasswordHash())
                .roles(usuario.getRol() != null ? usuario.getRol() : "USER")
                .build();
    }
}