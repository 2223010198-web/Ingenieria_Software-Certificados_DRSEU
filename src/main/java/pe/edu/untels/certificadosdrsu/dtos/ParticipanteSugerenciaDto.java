package pe.edu.untels.certificadosdrsu.dtos;

import lombok.Data;

@Data
public class ParticipanteSugerenciaDto {
    private Long idParticipante;
    private String nombres;
    private String apellidos;
    private String email;
}
