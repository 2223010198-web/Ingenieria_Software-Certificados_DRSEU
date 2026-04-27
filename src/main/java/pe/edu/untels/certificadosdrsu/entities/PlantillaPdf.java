package pe.edu.untels.certificadosdrsu.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "plantillas_pdf")
public class PlantillaPdf {

  @Id
  @GeneratedValue
  @Column(columnDefinition = "uuid")
  private UUID id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "tipo_certificado_id", nullable = false)
  private TipoCertificado tipoCertificado;

  @Column(nullable = false)
  private String nombre;

  @Column(name = "archivo_path", nullable = false)
  private String archivoPath;

  @Column(name = "metodo_extraccion")
  private String metodoExtraccion;

  @Column(name = "texto_extraido", columnDefinition = "TEXT")
  private String textoExtraido;

  @Column(nullable = false)
  private Boolean activo = true;

  @Column(nullable = false)
  private Integer version = 1;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @ManyToOne
  @JoinColumn(name = "creado_por_id")
  private Usuario creadoPor;

  public PlantillaPdf() {
    this.createdAt = LocalDateTime.now();
  }

  // ===== GETTERS AND SETTERS =====

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public TipoCertificado getTipoCertificado() {
    return tipoCertificado;
  }

  public void setTipoCertificado(TipoCertificado tipoCertificado) {
    this.tipoCertificado = tipoCertificado;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getArchivoPath() {
    return archivoPath;
  }

  public void setArchivoPath(String archivoPath) {
    this.archivoPath = archivoPath;
  }

  public String getMetodoExtraccion() {
    return metodoExtraccion;
  }

  public void setMetodoExtraccion(String metodoExtraccion) {
    this.metodoExtraccion = metodoExtraccion;
  }

  public String getTextoExtraido() {
    return textoExtraido;
  }

  public void setTextoExtraido(String textoExtraido) {
    this.textoExtraido = textoExtraido;
  }

  public Boolean getActivo() {
    return activo;
  }

  public void setActivo(Boolean activo) {
    this.activo = activo;
  }

  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
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
