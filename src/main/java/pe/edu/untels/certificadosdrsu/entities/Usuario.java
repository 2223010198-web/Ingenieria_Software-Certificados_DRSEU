package pe.edu.untels.certificadosdrsu.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.untels.certificadosdrsu.enums.RolUsuario;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
