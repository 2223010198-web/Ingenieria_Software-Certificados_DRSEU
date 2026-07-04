package pe.edu.untels.certificadosdrsu.dtos;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TipoCertificadoDTO {
    private Long idTipoCertificado;
    private String nombre;
    private String descripcion;
    private Boolean esPredeterminado;
    private Boolean activo;
    private LocalDateTime createdAt;
}
