package pe.edu.untels.certificadosdrsu.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SugerenciaCertificadoDTO {
    private Long id;
    private String codigoCertificado;
    private String numeroFolio;
    private String participanteNombre;
}
