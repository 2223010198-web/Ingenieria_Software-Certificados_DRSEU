package pe.edu.untels.certificadosdrsu.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TipoCertificadoInsertDTO {
    @NotBlank
    private String nombre;
    private String descripcion;
    private Boolean esPredeterminado;
}
