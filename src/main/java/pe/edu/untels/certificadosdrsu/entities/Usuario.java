package pe.edu.untels.certificadosdrsu.entities;

import jakarta.persistence.*;
import pe.edu.untels.certificadosdrsu.enums.RolUsuario;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
public class Usuario {

  @Id
  @GeneratedValue
  @Column(columnDefinition = "uuid")
  private UUID id;

  @Column(name = "nombre_completo", nullable = false)
  private String nombreCompleto;

  @Column(nullable = false, unique = true, length = 8)
  private String dni;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(name = "password_hash", nullable = false)
  private String passwordHash;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private RolUsuario rol;

  @Column(name = "es_temporal", nullable = false)
  private Boolean esTemporal = false;

  @Column(nullable = false)
  private Boolean activo = true;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @ManyToOne
  @JoinColumn(name = "creado_por_id")
  private Usuario creadoPor;

  public Usuario() {
    this.createdAt = LocalDateTime.now();
  }

  // ===== GETTERS AND SETTERS =====

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getNombreCompleto() {
    return nombreCompleto;
  }

  public void setNombreCompleto(String nombreCompleto) {
    this.nombreCompleto = nombreCompleto;
  }

  public String getDni() {
    return dni;
  }

  public void setDni(String dni) {
    this.dni = dni;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public RolUsuario getRol() {
    return rol;
  }

  public void setRol(RolUsuario rol) {
    this.rol = rol;
  }

  public Boolean getEsTemporal() {
    return esTemporal;
  }

  public void setEsTemporal(Boolean esTemporal) {
    this.esTemporal = esTemporal;
  }

  public Boolean getActivo() {
    return activo;
  }

  public void setActivo(Boolean activo) {
    this.activo = activo;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public Usuario getCreadoPor() {
    return creadoPor;
  }

  public void setCreadoPor(Usuario creadoPor) {
    this.creadoPor = creadoPor;
  }
}
