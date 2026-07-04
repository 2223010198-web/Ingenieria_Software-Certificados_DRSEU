package pe.edu.untels.certificadosdrsu.dtos;

import lombok.Data;
import pe.edu.untels.certificadosdrsu.enums.CategoriaParticipante;

@Data
public class ParticipanteProyectoDTO {
    private Long participacionId;
    private Long idParticipante;
    private Long idProyecto;
    private String dni;
    private String nombres;
    private String apellidos;
    private String nombreCompleto;
    private String email;
    private String celular;
    private CategoriaParticipante categoria;
    private boolean activo;
    private String tipoParticipacion;
    private String descripcionParticipante;
}
