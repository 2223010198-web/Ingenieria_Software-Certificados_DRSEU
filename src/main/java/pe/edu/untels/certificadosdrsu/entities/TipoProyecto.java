package pe.edu.untels.certificadosdrsu.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Tipo_proyecto")
public class TipoProyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_proyecto")
    private Integer idTipoProyecto;

    @Column(name = "tipo_proyecto_nombre", nullable = false)
    private String tipoProyectoNombre;

    public TipoProyecto() {

    }

    public TipoProyecto(Integer idTipoProyecto, String tipoProyectoNombre) {
        this.idTipoProyecto = idTipoProyecto;
        this.tipoProyectoNombre = tipoProyectoNombre;
    }

    public Integer getIdTipoProyecto() {
        return idTipoProyecto;
    }

    public void setIdTipoProyecto(Integer idTipoProyecto) {
        this.idTipoProyecto = idTipoProyecto;
    }

    public String getTipoProyectoNombre() {
        return tipoProyectoNombre;
    }

    public void setTipoProyectoNombre(String tipoProyectoNombre) {
        this.tipoProyectoNombre = tipoProyectoNombre;
    }
}
