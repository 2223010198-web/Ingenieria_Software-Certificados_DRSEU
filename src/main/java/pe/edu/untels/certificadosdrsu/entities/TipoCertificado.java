package pe.edu.untels.certificadosdrsu.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tipos_certificado")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoCertificado {

  @Id
  @GeneratedValue
  @Column(columnDefinition = "uuid")
  private UUID idid_tipo_proyecto;

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
}
