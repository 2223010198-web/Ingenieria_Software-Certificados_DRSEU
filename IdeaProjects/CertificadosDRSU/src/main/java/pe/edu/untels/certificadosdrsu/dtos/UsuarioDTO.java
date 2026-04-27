package pe.edu.untels.certificadosdrsu.dtos;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

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
