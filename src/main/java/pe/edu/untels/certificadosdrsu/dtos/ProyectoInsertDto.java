package pe.edu.untels.certificadosdrsu.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class ProyectoInsertDto {

    private Integer idProyecto;
    private String titulo;
    private String descripcion;
    private String numeroRegistro;
    private String documentoAprobacion;
    private LocalDate fechaAprobacion;
    private String estado;
    private UUID idCreadoPor;
    private UUID idAprobadoPor;
    private LocalDateTime createdAt;
    private LocalDateTime updatedUp;
}
