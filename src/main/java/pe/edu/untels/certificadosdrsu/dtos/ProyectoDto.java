package pe.edu.untels.certificadosdrsu.dtos;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ProyectoDto {

    private String titulo;
    private String descripcion;
    private String numeroRegistro;
    private String documentoAprobacion;
    private LocalDate fechaAprobacion;
    private String estado;
}
