package pe.edu.untels.certificadosdrsu.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "plantillas_pdf")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
