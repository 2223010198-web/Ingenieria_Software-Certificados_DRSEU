package pe.edu.untels.certificadosdrsu.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Usuario_auditoria")
public class UsuarioAuditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_usuario", nullable = false)
    private int idUsuario;

    @Column(name = "user_creado_por")
    private int userCreadoPor;

    @Column(name = "cambio_pasword")
    private LocalDate cambioPasword;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public UsuarioAuditoria() {
    }

    public UsuarioAuditoria(int id, int idUsuario, int userCreadoPor, LocalDate cambioPasword, LocalDateTime createdAt) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.userCreadoPor = userCreadoPor;
        this.cambioPasword = cambioPasword;
        this.createdAt = createdAt;
    }

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