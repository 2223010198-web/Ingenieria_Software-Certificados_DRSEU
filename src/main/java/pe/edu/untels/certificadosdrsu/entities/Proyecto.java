package pe.edu.untels.certificadosdrsu.entities;

import jakarta.persistence.*;
import pe.edu.untels.certificadosdrsu.enums.Proyectoenum;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "proyectos")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proyecto")
    private Integer idProyecto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_certificado", nullable = false)
    private TipoCertificado tipoCertificado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_plantilla_pdf", nullable = false)
    private PlantillaPdf plantillaPdf;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "codigo_certificado", nullable = false, unique = true)
    private String codigoCertificado;

    @Column(name = "numero_folio")
    private String numeroFolio;

    @Column(name = "numero_registro")
    private String numeroRegistro;

    @Column(name = "documento_aprobacion")
    private String documentoAprobacion;

    @Column(name = "fecha_aprobacion")
    private LocalDate fechaAprobacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Proyectoenum estado = Proyectoenum.PENDIENTE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_creado_por", nullable = false)
    private Usuario creadoPor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aprobado_por")
    private Usuario aprobadoPor;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_up")
    private LocalDateTime updatedUp;

    public Proyecto() {
    }


    public Proyecto(Integer idProyecto, TipoCertificado tipoCertificado, PlantillaPdf plantillaPdf,
                    String titulo, String descripcion, String codigoCertificado,
                    String numeroFolio, String numeroRegistro, String documentoAprobacion,
                    LocalDate fechaAprobacion, EstadoProyecto estado, Usuario creadoPor,
                    Usuario aprobadoPor, LocalDateTime createdAt, LocalDateTime updatedUp) {
        this.idProyecto = idProyecto;
        this.tipoCertificado = tipoCertificado;
        this.plantillaPdf = plantillaPdf;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.codigoCertificado = codigoCertificado;
        this.numeroFolio = numeroFolio;
        this.numeroRegistro = numeroRegistro;
        this.documentoAprobacion = documentoAprobacion;
        this.fechaAprobacion = fechaAprobacion;
        this.estado = estado;
        this.creadoPor = creadoPor;
        this.aprobadoPor = aprobadoPor;
        this.createdAt = createdAt;
        this.updatedUp = updatedUp;
    }


    public Integer getIdProyecto() { return idProyecto; }
    public void setIdProyecto(Integer idProyecto) { this.idProyecto = idProyecto; }

    public TipoCertificado getTipoCertificado() { return tipoCertificado; }
    public void setTipoCertificado(TipoCertificado tipoCertificado) { this.tipoCertificado = tipoCertificado; }

    public PlantillaPdf getPlantillaPdf() { return plantillaPdf; }
    public void setPlantillaPdf(PlantillaPdf plantillaPdf) { this.plantillaPdf = plantillaPdf; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getCodigoCertificado() { return codigoCertificado; }
    public void setCodigoCertificado(String codigoCertificado) { this.codigoCertificado = codigoCertificado; }

    public String getNumeroFolio() { return numeroFolio; }
    public void setNumeroFolio(String numeroFolio) { this.numeroFolio = numeroFolio; }

    public String getNumeroRegistro() { return numeroRegistro; }
    public void setNumeroRegistro(String numeroRegistro) { this.numeroRegistro = numeroRegistro; }

    public String getDocumentoAprobacion() { return documentoAprobacion; }
    public void setDocumentoAprobacion(String documentoAprobacion) { this.documentoAprobacion = documentoAprobacion; }

    public LocalDate getFechaAprobacion() { return fechaAprobacion; }
    public void setFechaAprobacion(LocalDate fechaAprobacion) { this.fechaAprobacion = fechaAprobacion; }

    public EstadoProyecto getEstado() { return estado; }
    public void setEstado(EstadoProyecto estado) { this.estado = estado; }

    public Usuario getCreadoPor() { return creadoPor; }
    public void setCreadoPor(Usuario creadoPor) { this.creadoPor = creadoPor; }

    public Usuario getAprobadoPor() { return aprobadoPor; }
    public void setAprobadoPor(Usuario aprobadoPor) { this.aprobadoPor = aprobadoPor; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedUp() { return updatedUp; }
    public void setUpdatedUp(LocalDateTime updatedUp) { this.updatedUp = updatedUp; }
}