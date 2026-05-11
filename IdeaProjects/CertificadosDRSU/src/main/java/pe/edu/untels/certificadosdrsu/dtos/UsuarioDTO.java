package pe.edu.untels.certificadosdrsu.dtos;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;
import pe.edu.untels.certificadosdrsu.entities.Participante;

@Data
public class UsuarioDTO {
    private Integer id;
    private String username;
    private String passwordHash;
    private String rol;
    private boolean activo;
    private Participante participante;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }
}
