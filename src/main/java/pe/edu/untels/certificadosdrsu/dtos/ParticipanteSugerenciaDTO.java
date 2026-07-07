package pe.edu.untels.certificadosdrsu.dtos;

import lombok.Data;

@Data
public class ParticipanteSugerenciaDTO {
    private Long idParticipante;
    private String nombres;
    private String apellidos;
    private String email;
}
