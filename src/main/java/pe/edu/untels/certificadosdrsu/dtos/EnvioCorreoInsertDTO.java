package pe.edu.untels.certificadosdrsu.dtos;

import java.time.LocalDateTime;

public class EnvioCorreoInsertDTO {
    private Long id;
    private Long idEnviadoPor;
    private Long idCertificado;
    private Long idParticipante;
    private String emailDestino;
    private String plantillaCorreo;
    private String asunto;
    private String cuerpo;
    private String estado;
    private boolean esReenvio;
    private LocalDateTime enviadoAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdEnviadoPor() {
        return idEnviadoPor;
    }

    public void setIdEnviadoPor(Long idEnviadoPor) {
        this.idEnviadoPor = idEnviadoPor;
    }

    public Long getIdCertificado() {
        return idCertificado;
    }

    public void setIdCertificado(Long idCertificado) {
        this.idCertificado = idCertificado;
    }

    public Long getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(Long idParticipante) {
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
