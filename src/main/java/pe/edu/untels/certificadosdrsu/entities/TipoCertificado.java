package pe.edu.untels.certificadosdrsu.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tipos_certificado")
public class TipoCertificado {

  @Id
  @GeneratedValue
  @Column(columnDefinition = "uuid")
  private UUID id;

  @Column(nullable = false, unique = true)
  private String nombre;

  @Column(columnDefinition = "TEXT")
  private String descripcion;

  @Column(name = "es_predeterminado", nullable = false)
  private Boolean esPredeterminado = false;

  @Column(nullable = false)
  private Boolean activo = true;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  public TipoCertificado() {
    this.createdAt = LocalDateTime.now();
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Boolean getEsPredeterminado() {
    return esPredeterminado;
  }

  public void setEsPredeterminado(Boolean esPredeterminado) {
    this.esPredeterminado = esPredeterminado;
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

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

}
