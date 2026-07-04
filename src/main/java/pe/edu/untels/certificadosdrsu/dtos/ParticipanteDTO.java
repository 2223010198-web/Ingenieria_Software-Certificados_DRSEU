package pe.edu.untels.certificadosdrsu.dtos;

import lombok.Data;
import pe.edu.untels.certificadosdrsu.enums.CategoriaParticipante;

@Data
public class ParticipanteDTO {
    private Long id;
    private Long idParticipante;
    private String dni;
    private String nombres;
    private String apellidos;
    private String nombreCompleto;
    private String email;
    private String celular;
    private CategoriaParticipante categoria;
    private boolean activo;
    private Long usuarioId;
}
