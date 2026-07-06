package pe.edu.untels.certificadosdrsu;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.untels.certificadosdrsu.entities.Proyecto;
import pe.edu.untels.certificadosdrsu.enums.EstadoProyecto;
import pe.edu.untels.certificadosdrsu.repositories.IProyectoRepository;
import pe.edu.untels.certificadosdrsu.servicesimplements.ProyectoServiceImplement;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProyectoServiceTest {

    @Mock
    private IProyectoRepository proyectoRepository;

    @InjectMocks
    private ProyectoServiceImplement proyectoService;

    @Test
    void insertar_retorna_proyecto_guardado() {
        Proyecto proyecto = proyectoConCamposRequeridos("Seminario de Investigación 2026");
        when(proyectoRepository.save(any(Proyecto.class))).thenReturn(proyecto);

        Proyecto resultado = proyectoService.insert(proyecto);

        assertThat(resultado.getTitulo()).isEqualTo("Seminario de Investigación 2026");
        verify(proyectoRepository).save(any(Proyecto.class));
    }

    @Test
    void insertar_siempre_establece_estado_EN_PROCESO_sin_importar_el_estado_recibido() {
        Proyecto proyecto = proyectoConCamposRequeridos("Taller de Liderazgo");
        proyecto.setEstado(EstadoProyecto.BORRADOR);

        Proyecto proyectoGuardado = proyectoConCamposRequeridos("Taller de Liderazgo");
        proyectoGuardado.setEstado(EstadoProyecto.EN_PROCESO);
        when(proyectoRepository.save(any(Proyecto.class))).thenReturn(proyectoGuardado);

        Proyecto resultado = proyectoService.insert(proyecto);

        ArgumentCaptor<Proyecto> captor = ArgumentCaptor.forClass(Proyecto.class);
        verify(proyectoRepository).save(captor.capture());
        assertThat(captor.getValue().getEstado()).isEqualTo(EstadoProyecto.EN_PROCESO);
        assertThat(resultado.getEstado()).isEqualTo(EstadoProyecto.EN_PROCESO);
    }

    @Test
    void insertar_proyecto_con_todos_los_campos_requeridos_HU17() {
        Proyecto proyecto = new Proyecto();
        proyecto.setTitulo("Congreso Internacional de Ciencias");
        proyecto.setTipoEvento("Congreso");
        proyecto.setModalidad("Presencial");
        proyecto.setFechaInicio(LocalDate.of(2026, 8, 1));
        proyecto.setFechaFin(LocalDate.of(2026, 8, 3));

        Proyecto guardado = new Proyecto();
        guardado.setTitulo("Congreso Internacional de Ciencias");
        guardado.setTipoEvento("Congreso");
        guardado.setModalidad("Presencial");
        guardado.setFechaInicio(LocalDate.of(2026, 8, 1));
        guardado.setFechaFin(LocalDate.of(2026, 8, 3));
        guardado.setEstado(EstadoProyecto.EN_PROCESO);
        when(proyectoRepository.save(any(Proyecto.class))).thenReturn(guardado);

        Proyecto resultado = proyectoService.insert(proyecto);

        assertThat(resultado.getTitulo()).isEqualTo("Congreso Internacional de Ciencias");
        assertThat(resultado.getTipoEvento()).isEqualTo("Congreso");
        assertThat(resultado.getModalidad()).isEqualTo("Presencial");
        assertThat(resultado.getFechaInicio()).isEqualTo(LocalDate.of(2026, 8, 1));
        assertThat(resultado.getFechaFin()).isEqualTo(LocalDate.of(2026, 8, 3));
        assertThat(resultado.getEstado()).isEqualTo(EstadoProyecto.EN_PROCESO);
    }

    private Proyecto proyectoConCamposRequeridos(String titulo) {
        Proyecto p = new Proyecto();
        p.setTitulo(titulo);
        p.setTipoEvento("Seminario");
        p.setModalidad("Presencial");
        p.setFechaInicio(LocalDate.of(2026, 1, 1));
        p.setFechaFin(LocalDate.of(2026, 12, 31));
        return p;
    }
}
