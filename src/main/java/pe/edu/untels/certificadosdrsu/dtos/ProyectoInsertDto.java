package pe.edu.untels.certificadosdrsu.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class ProyectoInsertDto {

    private Integer idProyecto;
    private String titulo;
    private String descripcion;
    private String codigoCertificado;
    private String numeroFolio;
    private String numeroRegistro;
    private String documentoAprobacion;
    private LocalDate fechaAprobacion;
    private String estado;
    private UUID idTipoCertificado;
    private UUID idPlantillaPdf;
    private UUID idCreadoPor;
    private UUID idAprobadoPor;
    private LocalDateTime createdAt;
    private LocalDateTime updatedUp;

    public Integer getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Integer idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigoCertificado() {
        return codigoCertificado;
    }

    public void setCodigoCertificado(String codigoCertificado) {
        this.codigoCertificado = codigoCertificado;
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

    public String getDocumentoAprobacion() {
        return documentoAprobacion;
    }

    public void setDocumentoAprobacion(String documentoAprobacion) {
        this.documentoAprobacion = documentoAprobacion;
    }

    public LocalDate getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(LocalDate fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public UUID getIdTipoCertificado() {
        return idTipoCertificado;
    }

    public void setIdTipoCertificado(UUID idTipoCertificado) {
        this.idTipoCertificado = idTipoCertificado;
    }

    public UUID getIdPlantillaPdf() {
        return idPlantillaPdf;
    }

    public void setIdPlantillaPdf(UUID idPlantillaPdf) {
        this.idPlantillaPdf = idPlantillaPdf;
    }

    public UUID getIdCreadoPor() {
        return idCreadoPor;
    }

    public void setIdCreadoPor(UUID idCreadoPor) {
        this.idCreadoPor = idCreadoPor;
    }

    public UUID getIdAprobadoPor() {
        return idAprobadoPor;
    }

    public void setIdAprobadoPor(UUID idAprobadoPor) {
        this.idAprobadoPor = idAprobadoPor;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedUp() {
        return updatedUp;
    }

    public void setUpdatedUp(LocalDateTime updatedUp) {
        this.updatedUp = updatedUp;
    }
}
