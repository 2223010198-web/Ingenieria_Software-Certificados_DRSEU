package pe.edu.untels.certificadosdrsu.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class UsuarioDTO {
  private UUID id;
  private String nombreCompleto;
  private String dni;
  private String email;
  private String passwordHash;
  private String rol;
  private boolean esTemporal;
  private boolean activo;
  private LocalDateTime createdAt;
  private UUID creadoPorId;
}
