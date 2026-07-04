package pe.edu.untels.certificadosdrsu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.dominio}")
    private String dominio;

    public void enviarCorreoRecuperacion(String destinatario, String token) {
        String enlace = dominio + "/auth/reset-password?token=" + token;

        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject("Recuperación de contraseña — DRSEU UNTELS");
        mensaje.setText(
            "Hola,\n\n" +
            "Recibimos una solicitud para restablecer tu contraseña.\n\n" +
            "Haz clic en el siguiente enlace (válido por 15 minutos):\n" +
            enlace + "\n\n" +
            "Si no solicitaste este cambio, ignora este mensaje.\n\n" +
            "DRSEU — UNTELS"
        );

        mailSender.send(mensaje);
    }
}
