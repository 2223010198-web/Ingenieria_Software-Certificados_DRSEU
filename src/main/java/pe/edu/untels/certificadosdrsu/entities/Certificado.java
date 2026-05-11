package pe.edu.untels.certificadosdrsu.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "certificados")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Certificado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // [cite: 32]

    @Column(name = "id_integrante_proyecto")
    private Long idIntegranteProyecto;

    @Column(name = "id_participante")
    private Long idParticipante;

    @Column(name = "id_tipo_certificado")
    private Long idTipoCertificado;

    @Column(name = "id_proyecto")
    private Long idProyecto;

    @Column(name = "numero_folio")
    private String numeroFolio;

    @Column(name = "tipo_participacion")
    private String tipoParticipacion;

    @Column(name = "numero_registro")
    private String numeroRegistro;

    @Column(name = "descripcion_usuario")
    private String descripcionUsuario;

    @Column(name = "codigo_certificado")
    private String codigoCertificado;

    @Column(name = "fecha_emision")
    private LocalDate fechaEmision;

    @Column(name = "archivo_borrador_path")
    private String archivoBorradorPath;

    @Column(name = "archivo_firmado_path")
    private String archivoFirmadoPath;

    @Column(name = "estado_firma")
    private String estadoFirma;

    @Column(name = "generado_at")
    private LocalDateTime generadoAt;

    @Column(name = "firmado_at")
    private LocalDateTime firmadoAt;

    @Column(name = "id_generado_por")
    private Long idGeneradoPor;
}
