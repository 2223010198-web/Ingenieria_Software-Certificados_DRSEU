package pe.edu.untels.certificadosdrsu.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserProfileDTO {
    private Long id;
    private String username;
    private String rol;
}
