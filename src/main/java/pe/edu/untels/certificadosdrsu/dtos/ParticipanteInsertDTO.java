package pe.edu.untels.certificadosdrsu.dtos;

import lombok.Data;
import pe.edu.untels.certificadosdrsu.enums.CategoriaParticipante;

import java.time.LocalDateTime;

@Data
public class ParticipanteInsertDTO {
    private Long id;
    private String dni;
    private String nombres;
    private String apellidos;
    private String email;
    private String celular;
    private CategoriaParticipante categoria;
    private boolean activo;
    private Long usuarioId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
