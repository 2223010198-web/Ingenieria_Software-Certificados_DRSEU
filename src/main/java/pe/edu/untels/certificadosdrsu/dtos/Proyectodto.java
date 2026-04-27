package pe.edu.untels.certificadosdrsu.dtos;

import java.time.LocalDate;

public class Proyectodto {

    private String titulo;
    private String descripcion;
    private String codigoCertificado;
    private String numeroFolio;
    private String numeroRegistro;
    private String documentoAprobacion;
    private LocalDate fechaAprobacion;
    private String estado;

    private String tipoCertificadoNombre;

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

    public String getTipoCertificadoNombre() {
        return tipoCertificadoNombre;
    }

    public void setTipoCertificadoNombre(String tipoCertificadoNombre) {
        this.tipoCertificadoNombre = tipoCertificadoNombre;
    }
}
