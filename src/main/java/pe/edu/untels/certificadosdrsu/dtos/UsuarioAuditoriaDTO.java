package pe.edu.untels.certificadosdrsu.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UsuarioAuditoriaDTO {

    private int id;
    private int idUsuario;
    private int userCreadoPor;
    private LocalDate cambioPasword;
    private LocalDateTime createdAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getUserCreadoPor() {
        return userCreadoPor;
    }

    public void setUserCreadoPor(int userCreadoPor) {
        this.userCreadoPor = userCreadoPor;
    }

    public LocalDate getCambioPasword() {
        return cambioPasword;
    }

    public void setCambioPasword(LocalDate cambioPasword) {
        this.cambioPasword = cambioPasword;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}