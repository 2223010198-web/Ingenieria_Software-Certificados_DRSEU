package pe.edu.untels.certificadosdrsu.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.untels.certificadosdrsu.enums.CategoriaParticipante;

import java.time.LocalDateTime;

@Entity
@Table(name = "Participantes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Participante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_participante")
    private Long idParticipante;

    @Column(length = 20, unique = true)
    private String dni;

    @Column(nullable = false, length = 120)
    private String nombres;

    @Column(nullable = false, length = 120)
    private String apellidos;

    @Column(length = 120)
    private String email;

    @Column(length = 20)
    private String celular;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private CategoriaParticipante categoria;

    @Column(nullable = false)
    private boolean activo = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        if (createdAt == null) {
            createdAt = now;
        }
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
