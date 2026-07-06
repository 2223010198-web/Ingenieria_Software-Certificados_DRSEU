package pe.edu.untels.certificadosdrsu;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.untels.certificadosdrsu.entities.Participante;
import pe.edu.untels.certificadosdrsu.enums.CategoriaParticipante;
import pe.edu.untels.certificadosdrsu.repositories.ParticipanteRepository;
import pe.edu.untels.certificadosdrsu.servicesimplements.ParticipanteServiceImplement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParticipanteServiceTest {

    @Mock
    private ParticipanteRepository participanteRepository;

    @InjectMocks
    private ParticipanteServiceImplement participanteService;

    @Test
    void insertar_participanteValido_retornaGuardado() {
        Participante participante = participanteValido();
        when(participanteRepository.existsByDniIgnoreCase("12345678")).thenReturn(false);
        when(participanteRepository.existsByEmailIgnoreCase("juan@ejemplo.com")).thenReturn(false);
        when(participanteRepository.save(any(Participante.class))).thenReturn(participante);

        Participante resultado = participanteService.insert(participante);

        assertThat(resultado.getNombres()).isEqualTo("Juan");
        assertThat(resultado.getApellidos()).isEqualTo("Pérez");
        verify(participanteRepository).save(any(Participante.class));
    }

    @Test
    void insertar_estableceActivoEnTrue() {
        Participante participante = participanteValido();
        participante.setActivo(false);
        when(participanteRepository.existsByDniIgnoreCase(anyString())).thenReturn(false);
        when(participanteRepository.existsByEmailIgnoreCase(anyString())).thenReturn(false);
        when(participanteRepository.save(any(Participante.class))).thenAnswer(i -> i.getArgument(0));

        participanteService.insert(participante);

        ArgumentCaptor<Participante> captor = ArgumentCaptor.forClass(Participante.class);
        verify(participanteRepository).save(captor.capture());
        assertThat(captor.getValue().isActivo()).isTrue();
    }

    @Test
    void insertar_normalizaEmailAMinusculas() {
        Participante participante = participanteValido();
        participante.setEmail("JUAN@EJEMPLO.COM");
        when(participanteRepository.existsByDniIgnoreCase(anyString())).thenReturn(false);
        when(participanteRepository.existsByEmailIgnoreCase(anyString())).thenReturn(false);
        when(participanteRepository.save(any(Participante.class))).thenAnswer(i -> i.getArgument(0));

        participanteService.insert(participante);

        ArgumentCaptor<Participante> captor = ArgumentCaptor.forClass(Participante.class);
        verify(participanteRepository).save(captor.capture());
        assertThat(captor.getValue().getEmail()).isEqualTo("juan@ejemplo.com");
    }

    @Test
    void insertar_sinNombres_lanzaExcepcion() {
        Participante participante = participanteValido();
        participante.setNombres(null);

        assertThatThrownBy(() -> participanteService.insert(participante))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Los nombres son obligatorios");

        verify(participanteRepository, never()).save(any());
    }

    @Test
    void insertar_sinApellidos_lanzaExcepcion() {
        Participante participante = participanteValido();
        participante.setApellidos(null);

        assertThatThrownBy(() -> participanteService.insert(participante))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Los apellidos son obligatorios");

        verify(participanteRepository, never()).save(any());
    }

    @Test
    void insertar_sinDni_lanzaExcepcion() {
        Participante participante = participanteValido();
        participante.setDni(null);

        assertThatThrownBy(() -> participanteService.insert(participante))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El DNI es obligatorio");

        verify(participanteRepository, never()).save(any());
    }

    @Test
    void insertar_sinEmail_lanzaExcepcion() {
        Participante participante = participanteValido();
        participante.setEmail(null);

        assertThatThrownBy(() -> participanteService.insert(participante))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El correo electrónico es obligatorio");

        verify(participanteRepository, never()).save(any());
    }

    @Test
    void insertar_conFormatoEmailInvalido_lanzaExcepcion() {
        Participante participante = participanteValido();
        participante.setEmail("no-es-un-email");

        assertThatThrownBy(() -> participanteService.insert(participante))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El correo electrónico no tiene un formato válido");

        verify(participanteRepository, never()).save(any());
    }

    @Test
    void insertar_conEmailSinArroba_lanzaExcepcion() {
        Participante participante = participanteValido();
        participante.setEmail("juanejemplo.com");

        assertThatThrownBy(() -> participanteService.insert(participante))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El correo electrónico no tiene un formato válido");
    }

    @Test
    void insertar_conDniDuplicado_lanzaExcepcion() {
        Participante participante = participanteValido();
        when(participanteRepository.existsByDniIgnoreCase("12345678")).thenReturn(true);

        assertThatThrownBy(() -> participanteService.insert(participante))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Ya existe un participante con ese DNI");

        verify(participanteRepository, never()).save(any());
    }

    @Test
    void insertar_conEmailDuplicado_lanzaExcepcion() {
        Participante participante = participanteValido();
        when(participanteRepository.existsByDniIgnoreCase(anyString())).thenReturn(false);
        when(participanteRepository.existsByEmailIgnoreCase("juan@ejemplo.com")).thenReturn(true);

        assertThatThrownBy(() -> participanteService.insert(participante))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Ya existe otro participante con ese correo electrónico");

        verify(participanteRepository, never()).save(any());
    }

    @Test
    void insertar_conDniMenorDe8Digitos_lanzaExcepcion() {
        Participante participante = participanteValido();
        participante.setDni("1234567");

        assertThatThrownBy(() -> participanteService.insert(participante))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El DNI debe tener entre 8 y 12 dígitos");
    }

    @Test
    void insertar_conCategoria_persisteCorrectamente() {
        Participante participante = participanteValido();
        participante.setCategoria(CategoriaParticipante.DOCENTE);
        when(participanteRepository.existsByDniIgnoreCase(anyString())).thenReturn(false);
        when(participanteRepository.existsByEmailIgnoreCase(anyString())).thenReturn(false);
        when(participanteRepository.save(any(Participante.class))).thenAnswer(i -> i.getArgument(0));

        participanteService.insert(participante);

        ArgumentCaptor<Participante> captor = ArgumentCaptor.forClass(Participante.class);
        verify(participanteRepository).save(captor.capture());
        assertThat(captor.getValue().getCategoria()).isEqualTo(CategoriaParticipante.DOCENTE);
    }

    private Participante participanteValido() {
        Participante p = new Participante();
        p.setNombres("Juan");
        p.setApellidos("Pérez");
        p.setDni("12345678");
        p.setEmail("juan@ejemplo.com");
        p.setCategoria(CategoriaParticipante.ALUMNO);
        return p;
    }
}
