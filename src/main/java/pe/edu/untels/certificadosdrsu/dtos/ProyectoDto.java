package pe.edu.untels.certificadosdrsu.dtos;

import java.time.LocalDate;

import lombok.Data;


public class ProyectoDto {

    private long idProyecto;
    private String titulo;
    private String descripcion;
    private String numeroRegistro;
    private String documentoAprobacion;
    private LocalDate fechaAprobacion;
    private String estado;
    private long cantidadIntegrantes;

    public long getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(long idProyecto) {
        this.idProyecto = idProyecto;
    }

    public long getCantidadIntegrantes() {
        return cantidadIntegrantes;
    }

    public void setCantidadIntegrantes(long cantidadIntegrantes) {
        this.cantidadIntegrantes = cantidadIntegrantes;
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
}
