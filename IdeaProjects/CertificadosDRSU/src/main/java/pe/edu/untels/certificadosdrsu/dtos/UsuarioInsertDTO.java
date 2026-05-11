package pe.edu.untels.certificadosdrsu.dtos;

import pe.edu.untels.certificadosdrsu.entities.Participante;

public class UsuarioInsertDTO {
    private String username;
    private String passwordHash;
    private String rol;
    private Participante participante;

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

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }
}
