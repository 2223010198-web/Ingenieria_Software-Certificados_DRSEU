package pe.edu.untels.decertificadosdrsu.entities;

import jakarta.persistence.*;
import pe.edu.untels.decertificadosdrsu.enums.CategoriaParticipante;

import java.time.LocalDateTime;

@Entity
@Table(name = "Participantes")
public class Participante {
    // ATIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "dni", length = 50, nullable = false)
    private String dni;

    @Column(name = "nombres", length = 50, nullable = false)
    private String nombres;

    @Column(name = "apellidos", length = 50, nullable = false)
    private String apellidos;

    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @Column(name = "celular", length = 50, nullable = false)
    private String celular;

    @Column(name = "categoria", nullable = false)
    private CategoriaParticipante categoria = CategoriaParticipante.PARTICIPANTE;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    @Column(name = "usuario_id", nullable = false)
    private int usuarioId;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // METODOS PARA ASIGNAR Y ACTUALIZAR LA HORA DE CREACION Y UPDATE
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // CONTRUCTORES

    public Participante() {
    }

    public Participante(int id, String dni, String nombres, String apellidos, String email, String celular, CategoriaParticipante categoria, boolean activo, int usuarioId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.celular = celular;
        this.categoria = categoria;
        this.activo = activo;
        this.usuarioId = usuarioId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // GETTERS Y SETTERS


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public CategoriaParticipante getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaParticipante categoria) {
        this.categoria = categoria;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
