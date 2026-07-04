package pe.edu.untels.certificadosdrsu.dtos;

import lombok.Data;
import java.time.LocalDate;

@Data
public class VerificacionCertificadoDTO {
    private boolean valido;
    private String codigoCertificado;
    private String participanteNombre;
    private String proyectoTitulo;
    private String tipoCertificadoNombre;
    private String tipoParticipacion;
    private LocalDate fechaEmision;
}
