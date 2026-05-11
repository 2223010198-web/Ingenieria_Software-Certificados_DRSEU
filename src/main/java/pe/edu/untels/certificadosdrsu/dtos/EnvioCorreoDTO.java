package pe.edu.untels.certificadosdrsu.dtos;

import java.time.LocalDateTime;

public class EnvioCorreoDTO {
    private String emailDestino;
    private String asunto;
    private String cuerpo;
    private String estado;
    private boolean esReenvio;
    private LocalDateTime enviadoAt;

    public String getEmailDestino() {
        return emailDestino;
    }

    public void setEmailDestino(String emailDestino) {
        this.emailDestino = emailDestino;
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
