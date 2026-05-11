package pe.edu.untels.certificadosdrsu.dtos;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CertificadoDTO {
    private int id;
    private int idIntegranteProyecto;
    private int idParticipante;
    private int idTipoCertificado;
    private int idProyecto;
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
    private int idGeneradoPor;
}
