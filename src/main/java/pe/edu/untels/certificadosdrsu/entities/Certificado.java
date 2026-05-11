package pe.edu.untels.certificadosdrsu.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "certificados")
public class Certificado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_participacion", nullable = false)
    private int idParticipacion;

    @Column(name = "id_plantilla_pdf", nullable = false)
    private int idPlantillaPdf;

    @Column(name = "numero_folio", length = 50)
    private String numeroFolio;

    @Column(name = "numero_registro", length = 50)
    private String numeroRegistro;

    @Column(name = "codigo_certificado", length = 50)
    private String codigoCertificado;

    @Column(name = "fecha_emision")
    private LocalDate fechaEmision;

    @Column(name = "archivo_borrador_path", length = 255)
    private String archivoBorradorPath;

    @Column(name = "archivo_firmado_path", length = 255)
    private String archivoFirmadoPath;

    @Column(name = "estado_firma", length = 50)
    private String estadoFirma;

    @Column(name = "generado_at", updatable = false)
    private LocalDateTime generadoAt;

    @Column(name = "firmado_at")
    private LocalDateTime firmadoAt;

    @Column(name = "id_generado_por", nullable = false)
    private int idGeneradoPor;

    @PrePersist
    protected void onCreate() {
        generadoAt = LocalDateTime.now();
    }

    public Certificado() {
    }

    public Certificado(int id, int idParticipacion, int idPlantillaPdf, String numeroFolio, String numeroRegistro, String codigoCertificado, LocalDate fechaEmision, String archivoBorradorPath, String archivoFirmadoPath, String estadoFirma, LocalDateTime generadoAt, LocalDateTime firmadoAt, int idGeneradoPor) {
        this.id = id;
        this.idParticipacion = idParticipacion;
        this.idPlantillaPdf = idPlantillaPdf;
        this.numeroFolio = numeroFolio;
        this.numeroRegistro = numeroRegistro;
        this.codigoCertificado = codigoCertificado;
        this.fechaEmision = fechaEmision;
        this.archivoBorradorPath = archivoBorradorPath;
        this.archivoFirmadoPath = archivoFirmadoPath;
        this.estadoFirma = estadoFirma;
        this.generadoAt = generadoAt;
        this.firmadoAt = firmadoAt;
        this.idGeneradoPor = idGeneradoPor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdParticipacion() {
        return idParticipacion;
    }

    public void setIdParticipacion(int idParticipacion) {
        this.idParticipacion = idParticipacion;
    }

    public int getIdPlantillaPdf() {
        return idPlantillaPdf;
    }

    public void setIdPlantillaPdf(int idPlantillaPdf) {
        this.idPlantillaPdf = idPlantillaPdf;
    }

    public String getNumeroFolio() {
        return numeroFolio;
    }

    public void setNumeroFolio(String numeroFolio) {
        this.numeroFolio = numeroFolio;
    }

    public String getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public String getCodigoCertificado() {
        return codigoCertificado;
    }

    public void setCodigoCertificado(String codigoCertificado) {
        this.codigoCertificado = codigoCertificado;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getArchivoBorradorPath() {
        return archivoBorradorPath;
    }

    public void setArchivoBorradorPath(String archivoBorradorPath) {
        this.archivoBorradorPath = archivoBorradorPath;
    }

    public String getArchivoFirmadoPath() {
        return archivoFirmadoPath;
    }

    public void setArchivoFirmadoPath(String archivoFirmadoPath) {
        this.archivoFirmadoPath = archivoFirmadoPath;
    }

    public String getEstadoFirma() {
        return estadoFirma;
    }

    public void setEstadoFirma(String estadoFirma) {
        this.estadoFirma = estadoFirma;
    }

    public LocalDateTime getGeneradoAt() {
        return generadoAt;
    }

    public void setGeneradoAt(LocalDateTime generadoAt) {
        this.generadoAt = generadoAt;
    }

    public LocalDateTime getFirmadoAt() {
        return firmadoAt;
    }

    public void setFirmadoAt(LocalDateTime firmadoAt) {
        this.firmadoAt = firmadoAt;
    }

    public int getIdGeneradoPor() {
        return idGeneradoPor;
    }

    public void setIdGeneradoPor(int idGeneradoPor) {
        this.idGeneradoPor = idGeneradoPor;
    }
}