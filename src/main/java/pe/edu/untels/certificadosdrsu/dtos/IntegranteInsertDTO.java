package pe.edu.untels.certificadosdrsu.dtos;

import lombok.Data;

@Data
public class IntegranteInsertDTO {
    private Long idParticipante;
    private String tipoParticipacion;
    private String descripcionParticipante;
}
