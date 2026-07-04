package pe.edu.untels.certificadosdrsu.dtos;

import lombok.Data;
import pe.edu.untels.certificadosdrsu.enums.CategoriaParticipante;

@Data
public class ParticipanteProyectoRequestDTO {
    private Long idParticipante;
    private String dni;
    private String nombres;
    private String apellidos;
    private String email;
    private String celular;
    private CategoriaParticipante categoria;
    private String tipoParticipacion;
    private String descripcionParticipante;
}
