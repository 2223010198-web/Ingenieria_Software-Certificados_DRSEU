package pe.edu.untels.certificadosdrsu.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Participaciones")
public class Participacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_participante", nullable = false)
    private Long idParticipante;

    @Column(name = "id_proyecto", nullable = false)
    private Long idProyecto;

    @Column(name = "tipo_participacion", length = 100)
    private String tipoParticipacion;

    @Column(name = "descripcion_participante", length = 255)
    private String descripcionParticipante;

    public Participacion() {
    }

    public Participacion(Long id, Long idParticipante, Long idProyecto, String tipoParticipacion,
            String descripcionParticipante) {
        this.id = id;
        this.idParticipante = idParticipante;
        this.idProyecto = idProyecto;
        this.tipoParticipacion = tipoParticipacion;
        this.descripcionParticipante = descripcionParticipante;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(Long idParticipante) {
        this.idParticipante = idParticipante;
    }

    public Long getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Long idProyecto) {
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
