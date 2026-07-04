package pe.edu.untels.certificadosdrsu.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Participaciones", uniqueConstraints = {
        @UniqueConstraint(name = "uk_participacion_participante_proyecto",
                columnNames = {"id_participante", "id_proyecto"})
})
public class Participacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_participante", nullable = false)
    private Long idParticipante;

    @Column(name = "id_proyecto", nullable = false)
    private Long idProyecto;

    // Relaciones de solo lectura sobre las mismas columnas FK: permiten joins
    // (p. ej. ordenar integrantes por apellidos) sin alterar el contrato de los
    // DTOs existentes, que escriben mediante los campos Long de arriba.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_participante", insertable = false, updatable = false)
    private Participante participante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proyecto", insertable = false, updatable = false)
    private Proyecto proyecto;

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

    public Participante getParticipante() {
        return participante;
    }

    public Proyecto getProyecto() {
        return proyecto;
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
