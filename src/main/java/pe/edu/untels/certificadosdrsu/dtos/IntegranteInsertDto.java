package pe.edu.untels.certificadosdrsu.dtos;

import lombok.Data;

@Data
public class IntegranteInsertDto {
    private Long idParticipante;
    private String tipoParticipacion;
    private String descripcionParticipante;
}
