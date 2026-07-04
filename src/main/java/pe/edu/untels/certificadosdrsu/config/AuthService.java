package pe.edu.untels.certificadosdrsu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pe.edu.untels.certificadosdrsu.dtos.ChangePasswordRequest;
import pe.edu.untels.certificadosdrsu.dtos.LoginRequest;
import pe.edu.untels.certificadosdrsu.dtos.LoginResponse;
import pe.edu.untels.certificadosdrsu.dtos.UserProfileDTO;
import pe.edu.untels.certificadosdrsu.entities.Participante;
import pe.edu.untels.certificadosdrsu.entities.PasswordResetToken;
import pe.edu.untels.certificadosdrsu.entities.Usuario;
import pe.edu.untels.certificadosdrsu.repositories.IPasswordResetTokenRepository;
import pe.edu.untels.certificadosdrsu.repositories.ParticipanteRepository;
import pe.edu.untels.certificadosdrsu.repositories.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ParticipanteRepository participanteRepository;

    @Autowired
    private IPasswordResetTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String token = jwtUtil.generateToken(usuario.getUsername(), usuario.getRol());
        return new LoginResponse(token, usuario.getUsername(), usuario.getRol());
    }

    public UserProfileDTO getMe(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return new UserProfileDTO(usuario.getId(), usuario.getUsername(), usuario.getRol());
    }

    public void recoverPassword(String email) {
        // Busca el participante por email
        Optional<Participante> participanteOpt = participanteRepository.findByEmail(email);
        if (participanteOpt.isEmpty()) return; // Nunca revela si el correo existe o no

        // Busca el usuario vinculado a ese participante
        Optional<Usuario> usuarioOpt = usuarioRepository.findByIdParticipante(
                participanteOpt.get().getIdParticipante()
        );
        if (usuarioOpt.isEmpty()) return;

        String uuid = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(
                uuid, usuarioOpt.get(), LocalDateTime.now().plusMinutes(15)
        );
        tokenRepository.save(resetToken);
        emailService.enviarCorreoRecuperacion(email, uuid);
    }

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido o expirado"));

        if (resetToken.isUsado() || resetToken.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token inválido o expirado");
        }

        Usuario usuario = resetToken.getUsuario();
        usuario.setPasswordHash(passwordEncoder.encode(newPassword));
        usuarioRepository.save(usuario);

        resetToken.setUsado(true);
        tokenRepository.save(resetToken);
    }

    public void changePassword(String username, ChangePasswordRequest request) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPasswordActual(), usuario.getPasswordHash())) {
            throw new RuntimeException("Contraseña actual incorrecta");
        }

        usuario.setPasswordHash(passwordEncoder.encode(request.getPasswordNueva()));
        usuarioRepository.save(usuario);
    }
}