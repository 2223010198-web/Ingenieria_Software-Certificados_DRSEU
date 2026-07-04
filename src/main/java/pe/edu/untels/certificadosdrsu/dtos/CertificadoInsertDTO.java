package pe.edu.untels.certificadosdrsu.dtos;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CertificadoInsertDTO {
    private Long id;
    private Long idIntegranteProyecto;
    private Long idParticipante;
    private Long idTipoCertificado;
    private Long idProyecto;
    private String numeroFolio;
    private String tipoParticipacion;
    private String numeroRegistro;
    private String descripcionUsuario;
    private String codigoCertificado;
    private LocalDate fechaEmision;
    private String archivoBorradorPath;
    private String archivoFirmadoPath;
    private String estadoFirma;
    private LocalDateTime generadoAt;
    private LocalDateTime firmadoAt;
    private Long idGeneradoPor;
}
