package pe.edu.untels.certificadosdrsu.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "envios_correos")
public class EnvioCorreo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_enviado_por", nullable = false)
    private int idEnviadoPor;

    @Column(name = "id_certificado", nullable = false)
    private int idCertificado;

    @Column(name = "id_participante", nullable = false)
    private int idParticipante;

    @Column(name = "email_destino", length = 100)
    private String emailDestino;

    @Column(name = "plantilla_correo", columnDefinition = "JSON")
    private String plantillaCorreo;

    @Column(name = "asunto", length = 200)
    private String asunto;

    @Column(name = "cuerpo", columnDefinition = "TEXT")
    private String cuerpo;

    @Column(name = "estado", length = 50)
    private String estado;

    @Column(name = "es_reenvio", nullable = false)
    private boolean esReenvio = false;

    @Column(name = "enviado_at")
    private LocalDateTime enviadoAt;

    public EnvioCorreo() {
    }

    public EnvioCorreo(int id, int idEnviadoPor, int idCertificado, int idParticipante, String emailDestino, String plantillaCorreo, String asunto, String cuerpo, String estado, boolean esReenvio, LocalDateTime enviadoAt) {
        this.id = id;
        this.idEnviadoPor = idEnviadoPor;
        this.idCertificado = idCertificado;
        this.idParticipante = idParticipante;
        this.emailDestino = emailDestino;
        this.plantillaCorreo = plantillaCorreo;
        this.asunto = asunto;
        this.cuerpo = cuerpo;
        this.estado = estado;
        this.esReenvio = esReenvio;
        this.enviadoAt = enviadoAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEnviadoPor() {
        return idEnviadoPor;
    }

    public void setIdEnviadoPor(int idEnviadoPor) {
        this.idEnviadoPor = idEnviadoPor;
    }

    public int getIdCertificado() {
        return idCertificado;
    }

    public void setIdCertificado(int idCertificado) {
        this.idCertificado = idCertificado;
    }

    public int getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(int idParticipante) {
        this.idParticipante = idParticipante;
    }

    public String getEmailDestino() {
        return emailDestino;
    }

    public void setEmailDestino(String emailDestino) {
        this.emailDestino = emailDestino;
    }

    public String getPlantillaCorreo() {
        return plantillaCorreo;
    }

    public void setPlantillaCorreo(String plantillaCorreo) {
        this.plantillaCorreo = plantillaCorreo;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isEsReenvio() {
        return esReenvio;
    }

    public void setEsReenvio(boolean esReenvio) {
        this.esReenvio = esReenvio;
    }

    public LocalDateTime getEnviadoAt() {
        return enviadoAt;
    }

    public void setEnviadoAt(LocalDateTime enviadoAt) {
        this.enviadoAt = enviadoAt;
    }
}