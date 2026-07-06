package pe.edu.untels.certificadosdrsu.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pe.edu.untels.certificadosdrsu.entities.TipoProyecto;


public class ProyectoInsertDto {

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    private String descripcion;
    private String numeroRegistro;

    @NotBlank(message = "El tipo de evento es obligatorio")
    private String tipoEvento;

    @NotBlank(message = "La modalidad es obligatoria")
    private String modalidad;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDate fechaFin;

    private String documentoAprobacion;
    private LocalDate fechaAprobacion;
    private String estado;
    private TipoProyecto tipoProyecto;
    private Long idCreadoPor;
    private Long idAprobadoPor;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

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

    public String getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
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

    public TipoProyecto getTipoProyecto() {
        return tipoProyecto;
    }

    public void setTipoProyecto(TipoProyecto tipoProyecto) {
        this.tipoProyecto = tipoProyecto;
    }

    public Long getIdCreadoPor() {
        return idCreadoPor;
    }

    public void setIdCreadoPor(Long idCreadoPor) {
        this.idCreadoPor = idCreadoPor;
    }

    public Long getIdAprobadoPor() {
        return idAprobadoPor;
    }

    public void setIdAprobadoPor(Long idAprobadoPor) {
        this.idAprobadoPor = idAprobadoPor;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
