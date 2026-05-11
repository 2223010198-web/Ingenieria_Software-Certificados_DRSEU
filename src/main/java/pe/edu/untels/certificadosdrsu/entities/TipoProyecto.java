package pe.edu.untels.certificadosdrsu.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Tipo_proyecto")
public class TipoProyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "tipo_proyecto_nombre", nullable = false)
    private String tipo_proyecto_nombre;

    public TipoProyecto() {
    }

    public TipoProyecto(int id, String tipo_proyecto_nombre) {
        this.id = id;
        this.tipo_proyecto_nombre = tipo_proyecto_nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo_proyecto_nombre() {
        return tipo_proyecto_nombre;
    }

    public void setTipo_proyecto_nombre(String tipo_proyecto_nombre) {
        this.tipo_proyecto_nombre = tipo_proyecto_nombre;
    }
}
