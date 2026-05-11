package pe.edu.untels.certificadosdrsu.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Proyectos")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proyecto")
    private int idProyecto;

    @Column(name = "titulo", length = 200, nullable = false)
    private String titulo;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "documento_aprobacion", length = 100)
    private String documentoAprobacion;

    @Column(name = "fecha_aprobacion")
    private LocalDate fechaAprobacion;

    @Column(name = "estado", length = 50)
    private String estado;

    @Column(name = "id_tipo_proyecto", nullable = false)
    private int idTipoProyecto;

    @Column(name = "id_creado_por", nullable = false)
    private int idCreadoPor;

    @Column(name = "id_aprobado_por")
    private int idAprobadoPor;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Proyecto() {
    }

    public Proyecto(int idProyecto, String titulo, String descripcion, String documentoAprobacion, LocalDate fechaAprobacion, String estado, int idTipoProyecto, int idCreadoPor, int idAprobadoPor, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.idProyecto = idProyecto;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.documentoAprobacion = documentoAprobacion;
        this.fechaAprobacion = fechaAprobacion;
        this.estado = estado;
        this.idTipoProyecto = idTipoProyecto;
        this.idCreadoPor = idCreadoPor;
        this.idAprobadoPor = idAprobadoPor;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
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

    public int getIdTipoProyecto() {
        return idTipoProyecto;
    }

    public void setIdTipoProyecto(int idTipoProyecto) {
        this.idTipoProyecto = idTipoProyecto;
    }

    public int getIdCreadoPor() {
        return idCreadoPor;
    }

    public void setIdCreadoPor(int idCreadoPor) {
        this.idCreadoPor = idCreadoPor;
    }

    public int getIdAprobadoPor() {
        return idAprobadoPor;
    }

    public void setIdAprobadoPor(int idAprobadoPor) {
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