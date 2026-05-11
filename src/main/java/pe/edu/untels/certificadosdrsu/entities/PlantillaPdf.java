package pe.edu.untels.certificadosdrsu.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "plantillas_pdf")
public class PlantillaPdf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nombre_plantilla", length = 100, nullable = false)
    private String nombrePlantilla;

    @Column(name = "archivo", length = 255)
    private String archivo;

    @Column(name = "palabras_clave", columnDefinition = "JSON")
    private String palabrasClave;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public PlantillaPdf() {
    }

    public PlantillaPdf(long id, String nombrePlantilla, String archivo, String palabrasClave, boolean activo, LocalDateTime createdAt) {
        this.id = id;
        this.nombrePlantilla = nombrePlantilla;
        this.archivo = archivo;
        this.palabrasClave = palabrasClave;
        this.activo = activo;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombrePlantilla() {
        return nombrePlantilla;
    }

    public void setNombrePlantilla(String nombrePlantilla) {
        this.nombrePlantilla = nombrePlantilla;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getPalabrasClave() {
        return palabrasClave;
    }

    public void setPalabrasClave(String palabrasClave) {
        this.palabrasClave = palabrasClave;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}