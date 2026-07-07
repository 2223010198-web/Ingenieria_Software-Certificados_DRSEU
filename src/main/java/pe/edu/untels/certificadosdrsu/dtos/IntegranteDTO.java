package pe.edu.untels.certificadosdrsu.dtos;

import lombok.Data;

@Data
public class IntegranteDTO {
    private Long idParticipacion;
    private Long idParticipante;
    private String nombres;
    private String apellidos;
    private String email;
    private String tipoParticipacion;
    private String descripcionParticipante;
}
