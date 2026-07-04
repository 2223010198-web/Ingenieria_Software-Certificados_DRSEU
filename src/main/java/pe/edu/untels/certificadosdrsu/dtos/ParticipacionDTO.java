package pe.edu.untels.certificadosdrsu.dtos;

import lombok.Data;

@Data
public class ParticipacionDTO {
    private Long id;
    private Long idParticipante;
    private Long idProyecto;
    private String tipoParticipacion;
    private String descripcionParticipante;
}
