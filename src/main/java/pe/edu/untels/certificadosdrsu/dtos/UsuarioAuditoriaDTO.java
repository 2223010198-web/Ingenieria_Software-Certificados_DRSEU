package pe.edu.untels.certificadosdrsu.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UsuarioAuditoriaDTO {

    private Long id;
    private Long idUsuario;
    private Long userCreadoPor;
    private LocalDate cambioPasword;
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getUserCreadoPor() {
        return userCreadoPor;
    }

    public void setUserCreadoPor(Long userCreadoPor) {
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