package pe.edu.untels.certificadosdrsu.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Tipo_proyecto")
public class TipoProyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_proyecto")
    private Long idTipoProyecto;

    @Column(name = "tipo_proyecto_nombre", nullable = false)
    private String tipoProyectoNombre;

    public TipoProyecto() {

    }

    public TipoProyecto(Long idTipoProyecto, String tipoProyectoNombre) {
        this.idTipoProyecto = idTipoProyecto;
        this.tipoProyectoNombre = tipoProyectoNombre;
    }

    public Long getIdTipoProyecto() {
        return idTipoProyecto;
    }

    public void setIdTipoProyecto(Long idTipoProyecto) {
        this.idTipoProyecto = idTipoProyecto;
    }

    public String getTipoProyectoNombre() {
        return tipoProyectoNombre;
    }

    public void setTipoProyectoNombre(String tipoProyectoNombre) {
        this.tipoProyectoNombre = tipoProyectoNombre;
    }
}
