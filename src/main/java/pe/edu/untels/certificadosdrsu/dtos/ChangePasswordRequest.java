package pe.edu.untels.certificadosdrsu.dtos;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String passwordActual;
    private String passwordNueva;
}