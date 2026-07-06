package pe.edu.untels.certificadosdrsu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import pe.edu.untels.certificadosdrsu.config.JwtAuthFilter;
import pe.edu.untels.certificadosdrsu.controllers.ProyectoController;
import pe.edu.untels.certificadosdrsu.dtos.ProyectoInsertDto;
import pe.edu.untels.certificadosdrsu.entities.Proyecto;
import pe.edu.untels.certificadosdrsu.entities.Usuario;
import pe.edu.untels.certificadosdrsu.enums.EstadoProyecto;
import pe.edu.untels.certificadosdrsu.servicesinterface.IProyectoIntegranteService;
import pe.edu.untels.certificadosdrsu.servicesinterface.IProyectoService;
import pe.edu.untels.certificadosdrsu.servicesinterface.IUsuarioService;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProyectoController.class)
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(properties = {
    "app.dominio=http://localhost:4200",
    "jwt.secret=TestSecretKeyForJwtTestingPurposesOnly1234",
    "jwt.expiration-ms=3600000",
    "spring.mail.host=localhost",
    "spring.mail.port=25"
})
class ProyectoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper()
        .registerModule(new JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @MockitoBean
    private IProyectoService proyectoService;

    @MockitoBean
    private IUsuarioService usuarioService;

    @MockitoBean
    private IProyectoIntegranteService integranteService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @MockitoBean
    private JwtAuthFilter jwtAuthFilter;

    private Usuario usuarioCreador;
    private Proyecto proyectoGuardado;

    @BeforeEach
    void setUp() {
        usuarioCreador = new Usuario();
        usuarioCreador.setId(1L);
        usuarioCreador.setUsername("admin");

        proyectoGuardado = new Proyecto();
        proyectoGuardado.setIdProyecto(1L);
        proyectoGuardado.setTitulo("Seminario de Investigación 2026");
        proyectoGuardado.setTipoEvento("Seminario");
        proyectoGuardado.setModalidad("Presencial");
        proyectoGuardado.setFechaInicio(LocalDate.of(2026, 8, 1));
        proyectoGuardado.setFechaFin(LocalDate.of(2026, 8, 5));
        proyectoGuardado.setEstado(EstadoProyecto.EN_PROCESO);
        proyectoGuardado.setCreadoPor(usuarioCreador);
    }

    @Test
    void registrar_conDatosCompletos_devuelve201() throws Exception {
        when(usuarioService.listId(1L)).thenReturn(Optional.of(usuarioCreador));
        when(proyectoService.insert(any(Proyecto.class))).thenReturn(proyectoGuardado);

        mockMvc.perform(post("/api/proyectos/nuevo")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoValido())))
            .andExpect(status().isCreated());
    }

    @Test
    void registrar_sinTitulo_devuelve400() throws Exception {
        ProyectoInsertDto dto = dtoValido();
        dto.setTitulo(null);

        mockMvc.perform(post("/api/proyectos/nuevo")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void registrar_conTituloVacio_devuelve400() throws Exception {
        ProyectoInsertDto dto = dtoValido();
        dto.setTitulo("  ");

        mockMvc.perform(post("/api/proyectos/nuevo")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void registrar_sinTipoEvento_devuelve400() throws Exception {
        ProyectoInsertDto dto = dtoValido();
        dto.setTipoEvento(null);

        mockMvc.perform(post("/api/proyectos/nuevo")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void registrar_sinModalidad_devuelve400() throws Exception {
        ProyectoInsertDto dto = dtoValido();
        dto.setModalidad(null);

        mockMvc.perform(post("/api/proyectos/nuevo")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void registrar_sinFechaInicio_devuelve400() throws Exception {
        ProyectoInsertDto dto = dtoValido();
        dto.setFechaInicio(null);

        mockMvc.perform(post("/api/proyectos/nuevo")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void registrar_sinFechaFin_devuelve400() throws Exception {
        ProyectoInsertDto dto = dtoValido();
        dto.setFechaFin(null);

        mockMvc.perform(post("/api/proyectos/nuevo")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void registrar_conCreadorInexistente_devuelve404() throws Exception {
        when(usuarioService.listId(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/proyectos/nuevo")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoValido())))
            .andExpect(status().isNotFound());
    }

    @Test
    void proyectoCreado_tieneEstadoEN_PROCESO_en_respuesta() throws Exception {
        when(usuarioService.listId(1L)).thenReturn(Optional.of(usuarioCreador));
        when(proyectoService.insert(any(Proyecto.class))).thenReturn(proyectoGuardado);

        mockMvc.perform(post("/api/proyectos/nuevo")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoValido())))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.estado").value("EN_PROCESO"));
    }

    private ProyectoInsertDto dtoValido() {
        ProyectoInsertDto dto = new ProyectoInsertDto();
        dto.setTitulo("Seminario de Investigación 2026");
        dto.setTipoEvento("Seminario");
        dto.setModalidad("Presencial");
        dto.setFechaInicio(LocalDate.of(2026, 8, 1));
        dto.setFechaFin(LocalDate.of(2026, 8, 5));
        dto.setIdCreadoPor(1L);
        return dto;
    }
}
