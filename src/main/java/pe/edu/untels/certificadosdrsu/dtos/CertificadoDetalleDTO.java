package pe.edu.untels.certificadosdrsu.dtos;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CertificadoDetalleDTO {
    private Long id;
    private String codigoCertificado;
    private String numeroFolio;
    private String tipoParticipacion;
    private String estadoFirma;
    private LocalDate fechaEmision;
    private String archivoBorradorPath;
    private String archivoFirmadoPath;

    private Long idParticipante;
    private String participanteNombre;

    private Long idProyecto;
    private String proyectoTitulo;

    private Long idTipoCertificado;
    private String tipoCertificadoNombre;
}
