package pe.edu.untels.certificadosdrsu.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProyectoSearchResponseDTO {
    private Long idProyecto;
    private String titulo;
    private String descripcion;
    private String estado;
    private LocalDate fechaAprobacion;
    private String tipoProyectoNombre;
    private String creadoPorUsername;
}
