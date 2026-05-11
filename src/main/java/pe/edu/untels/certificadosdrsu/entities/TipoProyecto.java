package pe.edu.untels.certificadosdrsu.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tipo_proyecto")
public class TipoProyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "tipo_proyecto_nombre", length = 100, nullable = false)
    private String tipoProyectoNombre;

    public TipoProyecto() {
    }

    public TipoProyecto(int id, String tipoProyectoNombre) {
        this.id = id;
        this.tipoProyectoNombre = tipoProyectoNombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoProyectoNombre() {
        return tipoProyectoNombre;
    }

    public void setTipoProyectoNombre(String tipoProyectoNombre) {
        this.tipoProyectoNombre = tipoProyectoNombre;
    }
}