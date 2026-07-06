package pe.edu.untels.certificadosdrsu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import pe.edu.untels.certificadosdrsu.config.JwtAuthFilter;
import pe.edu.untels.certificadosdrsu.controllers.ParticipanteController;
import pe.edu.untels.certificadosdrsu.dtos.ParticipanteInsertDTO;
import pe.edu.untels.certificadosdrsu.entities.Participante;
import pe.edu.untels.certificadosdrsu.enums.CategoriaParticipante;
import pe.edu.untels.certificadosdrsu.servicesinterface.ParticipanteService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ParticipanteController.class)
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(properties = {
    "app.dominio=http://localhost:4200",
    "jwt.secret=TestSecretKeyForJwtTestingPurposesOnly1234",
    "jwt.expiration-ms=3600000",
    "spring.mail.host=localhost",
    "spring.mail.port=25"
})
class ParticipanteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper()
        .registerModule(new JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @MockitoBean
    private ParticipanteService participanteService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @MockitoBean
    private JwtAuthFilter jwtAuthFilter;

    private Participante participanteGuardado;

    @BeforeEach
    void setUp() {
        participanteGuardado = new Participante();
        participanteGuardado.setIdParticipante(1L);
        participanteGuardado.setNombres("María");
        participanteGuardado.setApellidos("García");
        participanteGuardado.setDni("87654321");
        participanteGuardado.setEmail("maria@ejemplo.com");
        participanteGuardado.setCategoria(CategoriaParticipante.ALUMNO);
        participanteGuardado.setActivo(true);
    }

    @Test
    void registrar_conDatosCompletos_devuelve201() throws Exception {
        when(participanteService.insert(any(Participante.class))).thenReturn(participanteGuardado);

        mockMvc.perform(post("/api/participantes")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoValido())))
            .andExpect(status().isCreated());
    }

    @Test
    void registrar_conDatosCompletos_respuestaContieneCampos() throws Exception {
        when(participanteService.insert(any(Participante.class))).thenReturn(participanteGuardado);

        mockMvc.perform(post("/api/participantes")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoValido())))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.nombres").value("María"))
            .andExpect(jsonPath("$.apellidos").value("García"))
            .andExpect(jsonPath("$.dni").value("87654321"))
            .andExpect(jsonPath("$.email").value("maria@ejemplo.com"))
            .andExpect(jsonPath("$.categoria").value("ALUMNO"));
    }

    @Test
    void registrar_conDniDuplicado_devuelve400() throws Exception {
        when(participanteService.insert(any(Participante.class)))
            .thenThrow(new IllegalArgumentException("Ya existe un participante con ese DNI"));

        mockMvc.perform(post("/api/participantes")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoValido())))
            .andExpect(status().isBadRequest());
    }

    @Test
    void registrar_conDniDuplicado_respuestaContieneMensajeError() throws Exception {
        when(participanteService.insert(any(Participante.class)))
            .thenThrow(new IllegalArgumentException("Ya existe un participante con ese DNI"));

        mockMvc.perform(post("/api/participantes")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoValido())))
            .andExpect(status().isBadRequest());
    }

    @Test
    void registrar_conFormatoEmailInvalido_devuelve400() throws Exception {
        when(participanteService.insert(any(Participante.class)))
            .thenThrow(new IllegalArgumentException("El correo electrónico no tiene un formato válido"));

        ParticipanteInsertDTO dto = dtoValido();
        dto.setEmail("no-es-email");

        mockMvc.perform(post("/api/participantes")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void registrar_conEmailDuplicado_devuelve400() throws Exception {
        when(participanteService.insert(any(Participante.class)))
            .thenThrow(new IllegalArgumentException("Ya existe otro participante con ese correo electrónico"));

        mockMvc.perform(post("/api/participantes")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoValido())))
            .andExpect(status().isBadRequest());
    }

    @Test
    void registrar_sinNombres_devuelve400() throws Exception {
        when(participanteService.insert(any(Participante.class)))
            .thenThrow(new IllegalArgumentException("Los nombres son obligatorios"));

        ParticipanteInsertDTO dto = dtoValido();
        dto.setNombres(null);

        mockMvc.perform(post("/api/participantes")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void registrar_sinApellidos_devuelve400() throws Exception {
        when(participanteService.insert(any(Participante.class)))
            .thenThrow(new IllegalArgumentException("Los apellidos son obligatorios"));

        ParticipanteInsertDTO dto = dtoValido();
        dto.setApellidos(null);

        mockMvc.perform(post("/api/participantes")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void registrar_sinEmail_devuelve400() throws Exception {
        when(participanteService.insert(any(Participante.class)))
            .thenThrow(new IllegalArgumentException("El correo electrónico es obligatorio"));

        ParticipanteInsertDTO dto = dtoValido();
        dto.setEmail(null);

        mockMvc.perform(post("/api/participantes")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void registrar_conCategoriaDocente_devuelve201() throws Exception {
        participanteGuardado.setCategoria(CategoriaParticipante.DOCENTE);
        when(participanteService.insert(any(Participante.class))).thenReturn(participanteGuardado);

        ParticipanteInsertDTO dto = dtoValido();
        dto.setCategoria(CategoriaParticipante.DOCENTE);

        mockMvc.perform(post("/api/participantes")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.categoria").value("DOCENTE"));
    }

    @Test
    void registrar_conCategoriaExterno_devuelve201() throws Exception {
        participanteGuardado.setCategoria(CategoriaParticipante.EXTERNO);
        when(participanteService.insert(any(Participante.class))).thenReturn(participanteGuardado);

        ParticipanteInsertDTO dto = dtoValido();
        dto.setCategoria(CategoriaParticipante.EXTERNO);

        mockMvc.perform(post("/api/participantes")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.categoria").value("EXTERNO"));
    }

    @Test
    void registrar_conCategoriaAdministrativo_devuelve201() throws Exception {
        participanteGuardado.setCategoria(CategoriaParticipante.ADMINISTRATIVO);
        when(participanteService.insert(any(Participante.class))).thenReturn(participanteGuardado);

        ParticipanteInsertDTO dto = dtoValido();
        dto.setCategoria(CategoriaParticipante.ADMINISTRATIVO);

        mockMvc.perform(post("/api/participantes")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.categoria").value("ADMINISTRATIVO"));
    }

    private ParticipanteInsertDTO dtoValido() {
        ParticipanteInsertDTO dto = new ParticipanteInsertDTO();
        dto.setNombres("María");
        dto.setApellidos("García");
        dto.setDni("87654321");
        dto.setEmail("maria@ejemplo.com");
        dto.setCategoria(CategoriaParticipante.ALUMNO);
        return dto;
    }
}
