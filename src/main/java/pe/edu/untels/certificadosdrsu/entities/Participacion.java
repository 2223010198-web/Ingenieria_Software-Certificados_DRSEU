package pe.edu.untels.certificadosdrsu.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Participaciones")
public class Participacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_participante", nullable = false, unique = true)
    private int idParticipante;

    @Column(name = "id_proyecto", nullable = false, unique = true)
    private int idProyecto;

    @Column(name = "tipo_participacion", length = 100, unique = true)
    private String tipoParticipacion;

    @Column(name = "descripcion_participante", length = 255)
    private String descripcionParticipante;

    public Participacion() {
    }

    public Participacion(int id, int idParticipante, int idProyecto, String tipoParticipacion, String descripcionParticipante) {
        this.id = id;
        this.idParticipante = idParticipante;
        this.idProyecto = idProyecto;
        this.tipoParticipacion = tipoParticipacion;
        this.descripcionParticipante = descripcionParticipante;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(int idParticipante) {
        this.idParticipante = idParticipante;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getTipoParticipacion() {
        return tipoParticipacion;
    }

    public void setTipoParticipacion(String tipoParticipacion) {
        this.tipoParticipacion = tipoParticipacion;
    }

    public String getDescripcionParticipante() {
        return descripcionParticipante;
    }

    public void setDescripcionParticipante(String descripcionParticipante) {
        this.descripcionParticipante = descripcionParticipante;
    }
}