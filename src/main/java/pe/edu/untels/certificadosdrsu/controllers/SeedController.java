package pe.edu.untels.certificadosdrsu.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import pe.edu.untels.certificadosdrsu.entities.Certificado;
import pe.edu.untels.certificadosdrsu.entities.EnvioCorreo;
import pe.edu.untels.certificadosdrsu.entities.Participacion;
import pe.edu.untels.certificadosdrsu.entities.Participante;
import pe.edu.untels.certificadosdrsu.entities.PlantillaPdf;
import pe.edu.untels.certificadosdrsu.entities.Proyecto;
import pe.edu.untels.certificadosdrsu.entities.TipoCertificado;
import pe.edu.untels.certificadosdrsu.entities.TipoProyecto;
import pe.edu.untels.certificadosdrsu.entities.Usuario;
import pe.edu.untels.certificadosdrsu.entities.UsuarioAuditoria;
import pe.edu.untels.certificadosdrsu.enums.CategoriaParticipante;
import pe.edu.untels.certificadosdrsu.enums.EstadoProyecto;
import pe.edu.untels.certificadosdrsu.repositories.ICertificadoRepository;
import pe.edu.untels.certificadosdrsu.repositories.IEnvioCorreoRepository;
import pe.edu.untels.certificadosdrsu.repositories.IPasswordResetTokenRepository;
import pe.edu.untels.certificadosdrsu.repositories.IPlantillaPdfRepository;
import pe.edu.untels.certificadosdrsu.repositories.ITipoCertificadoRepository;
import pe.edu.untels.certificadosdrsu.repositories.ITipoProyectoRepository;
import pe.edu.untels.certificadosdrsu.repositories.IProyectoRepository;
import pe.edu.untels.certificadosdrsu.repositories.IUsuarioAuditoriaRepository;
import pe.edu.untels.certificadosdrsu.repositories.ParticipacionRepository;
import pe.edu.untels.certificadosdrsu.repositories.ParticipanteRepository;
import pe.edu.untels.certificadosdrsu.repositories.UsuarioRepository;

@RestController
@RequestMapping("/seed")
public class SeedController {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private ITipoProyectoRepository tipoProyectoRepository;
    @Autowired private IProyectoRepository proyectoRepository;
    @Autowired private ParticipanteRepository participanteRepository;
    @Autowired private ParticipacionRepository participacionRepository;
    @Autowired private ITipoCertificadoRepository tipoCertificadoRepository;
    @Autowired private IPlantillaPdfRepository plantillaPdfRepository;
    @Autowired private ICertificadoRepository certificadoRepository;
    @Autowired private IEnvioCorreoRepository envioCorreoRepository;
    @Autowired private IUsuarioAuditoriaRepository usuarioAuditoriaRepository;
    @Autowired private IPasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    private static final List<String> SEEDABLE_TABLES = List.of(
            "envios_correos",
            "usuario_auditoria",
            "certificados",
            "plantillas_pdf",
            "tipos_certificado",
            "participaciones",
            "proyectos",
            "tipo_proyecto",
            "password_reset_token",
            "usuario",
            "participantes");

    @PostMapping("/admin")
    public ResponseEntity<String> crearAdmin() {
        if (usuarioRepository.existsByUsername("admin")) {
            return ResponseEntity.ok("Usuario admin ya existe");
        }
        Usuario admin = new Usuario();
        admin.setUsername("admin");
        admin.setPasswordHash(passwordEncoder.encode("admin123"));
        admin.setRol("ADMIN");
        admin.setActivo(true);
        usuarioRepository.save(admin);
        return ResponseEntity.ok("Usuario admin creado");
    }

    @PostMapping("/reset")
    @Transactional
    public ResponseEntity<Map<String, Object>> resetAll() {
        Map<String, Object> resumen = new java.util.LinkedHashMap<>();
        long totalAntes = usuarioRepository.count()
                + tipoProyectoRepository.count()
                + proyectoRepository.count()
                + participanteRepository.count()
                + participacionRepository.count()
                + tipoCertificadoRepository.count()
                + plantillaPdfRepository.count()
                + certificadoRepository.count()
                + envioCorreoRepository.count()
                + usuarioAuditoriaRepository.count()
                + passwordResetTokenRepository.count();

        String tablas = String.join(", ", SEEDABLE_TABLES);
        entityManager.createNativeQuery("TRUNCATE TABLE " + tablas + " RESTART IDENTITY CASCADE")
                .executeUpdate();

        resumen.put("registrosBorrados", totalAntes);
        resumen.put("secuenciasReiniciadas", true);
        return ResponseEntity.ok(resumen);
    }

    @PostMapping("/all")
    @Transactional
    public ResponseEntity<Map<String, Object>> seedAll() {
        Map<String, Object> resumen = new java.util.LinkedHashMap<>();

        Usuario admin = seedAdminUser();
        resumen.put("participantes", seedParticipantes());
        resumen.put("usuarios", seedUsuarios());
        resumen.put("tiposProyecto", seedTiposProyecto());
        resumen.put("proyectos", seedProyectos(admin));
        resumen.put("participaciones", seedParticipaciones());
        resumen.put("tiposCertificado", seedTiposCertificado());
        resumen.put("plantillasPdf", seedPlantillasPdf());
        resumen.put("certificados", seedCertificados());
        resumen.put("enviosCorreo", seedEnviosCorreo(admin));
        resumen.put("auditoriaUsuario", seedUsuarioAuditoria(admin));

        return ResponseEntity.ok(resumen);
    }

    private Usuario seedAdminUser() {
        return usuarioRepository.findByUsername("admin").orElseGet(() -> {
            Usuario a = new Usuario();
            a.setUsername("admin");
            a.setPasswordHash(passwordEncoder.encode("admin123"));
            a.setRol("ADMIN");
            a.setActivo(true);
            return usuarioRepository.save(a);
        });
    }

    private int seedUsuarios() {
        if (usuarioRepository.count() > 1) {
            return 0;
        }
        List<Participante> participantes = participanteRepository.findAll();
        if (participantes.size() < 3) {
            return 0;
        }
        String hash = passwordEncoder.encode("admin123");
        List<Usuario> nuevos = List.of(
                buildUsuario("ana.torres", hash, "COORDINADOR", participantes.get(0).getIdParticipante()),
                buildUsuario("luis.ramirez", hash, "DOCENTE", participantes.get(1).getIdParticipante()),
                buildUsuario("sofia.quispe", hash, "DOCENTE", participantes.get(2).getIdParticipante()));
        usuarioRepository.saveAll(nuevos);
        return nuevos.size();
    }

    private Usuario buildUsuario(String username, String hash, String rol, Long idParticipante) {
        Usuario u = new Usuario();
        u.setUsername(username);
        u.setPasswordHash(hash);
        u.setRol(rol);
        u.setActivo(true);
        u.setIdParticipante(idParticipante);
        return u;
    }

    private int seedTiposProyecto() {
        if (tipoProyectoRepository.count() > 0) {
            return 0;
        }
        List<TipoProyecto> tipos = List.of(
                new TipoProyecto(null, "Investigación"),
                new TipoProyecto(null, "Extensión Universitaria"),
                new TipoProyecto(null, "Responsabilidad Social"),
                new TipoProyecto(null, "Voluntariado"));
        tipoProyectoRepository.saveAll(tipos);
        return tipos.size();
    }

    private int seedParticipantes() {
        if (participanteRepository.count() > 0) {
            return 0;
        }
        List<Participante> personas = List.of(
                buildParticipante("70123456", "Ana", "Torres Vega", "ana.torres@untels.edu.pe", "987654321",
                        CategoriaParticipante.ESTUDIANTE),
                buildParticipante("70234567", "Luis", "Ramírez Solís", "luis.ramirez@untels.edu.pe", "987654322",
                        CategoriaParticipante.DOCENTE),
                buildParticipante("70345678", "Sofía", "Quispe Mendoza", "sofia.quispe@untels.edu.pe", "987654323",
                        CategoriaParticipante.PONENTE),
                buildParticipante("70456789", "Diego", "Vargas Silva", "diego.vargas@untels.edu.pe", "987654324",
                        CategoriaParticipante.VOLUNTARIO));
        participanteRepository.saveAll(personas);
        return personas.size();
    }

    private Participante buildParticipante(String dni, String nombres, String apellidos, String email, String celular,
            CategoriaParticipante categoria) {
        Participante p = new Participante();
        p.setDni(dni);
        p.setNombres(nombres);
        p.setApellidos(apellidos);
        p.setEmail(email);
        p.setCelular(celular);
        p.setCategoria(categoria);
        p.setActivo(true);
        return p;
    }

    private int seedProyectos(Usuario creador) {
        if (proyectoRepository.count() > 0) {
            return 0;
        }
        List<TipoProyecto> tipos = tipoProyectoRepository.findAll();
        if (tipos.isEmpty()) {
            return 0;
        }
        List<Proyecto> proyectos = List.of(
                buildProyecto("Feria de Ciencias UNTELS 2026", "Difusión de proyectos de investigación estudiantil.",
                        "REG-2026-001", EstadoProyecto.APROBADO, tipos.get(0 % tipos.size()), creador),
                buildProyecto("Campaña de Alfabetización Digital", "Talleres gratuitos para adultos mayores de Villa El Salvador.",
                        "REG-2026-002", EstadoProyecto.APROBADO, tipos.get(1 % tipos.size()), creador),
                buildProyecto("Voluntariado Ambiental Lomas de Villa", "Reforestación y limpieza de zonas críticas.",
                        "REG-2026-003", EstadoProyecto.PENDIENTE, tipos.get(3 % tipos.size()), creador),
                buildProyecto("Jornada de Salud Comunitaria", "Atención médica preventiva en asentamientos humanos.",
                        "REG-2026-004", EstadoProyecto.BORRADOR, tipos.get(2 % tipos.size()), creador));
        proyectoRepository.saveAll(proyectos);
        return proyectos.size();
    }

    private Proyecto buildProyecto(String titulo, String descripcion, String numeroRegistro, EstadoProyecto estado,
            TipoProyecto tipo, Usuario creador) {
        Proyecto p = new Proyecto();
        p.setTitulo(titulo);
        p.setDescripcion(descripcion);
        p.setNumeroRegistro(numeroRegistro);
        p.setEstado(estado);
        p.setTipoProyecto(tipo);
        p.setCreadoPor(creador);
        if (estado == EstadoProyecto.APROBADO) {
            p.setAprobadoPor(creador);
            p.setFechaAprobacion(LocalDate.now().minusDays(10));
        }
        return p;
    }

    private int seedParticipaciones() {
        if (participacionRepository.count() > 0) {
            return 0;
        }
        List<Proyecto> proyectos = proyectoRepository.findAll();
        List<Participante> participantes = participanteRepository.findAll();
        if (proyectos.isEmpty() || participantes.isEmpty()) {
            return 0;
        }
        List<Participacion> filas = List.of(
                buildParticipacion(participantes.get(0).getIdParticipante(), proyectos.get(0).getIdProyecto(),
                        "PONENTE", "Presenta póster de investigación"),
                buildParticipacion(participantes.get(1).getIdParticipante(), proyectos.get(0).getIdProyecto(),
                        "ORGANIZADOR", "Coordinador general"),
                buildParticipacion(participantes.get(2).getIdParticipante(), proyectos.get(1).getIdProyecto(),
                        "PONENTE", "Dicta taller de ofimática"),
                buildParticipacion(participantes.get(3).getIdParticipante(), proyectos.get(2).getIdProyecto(),
                        "VOLUNTARIO", "Brigada de reforestación"));
        participacionRepository.saveAll(filas);
        return filas.size();
    }

    private Participacion buildParticipacion(Long idParticipante, long idProyecto, String tipo, String descripcion) {
        Participacion p = new Participacion();
        p.setIdParticipante(idParticipante);
        p.setIdProyecto(idProyecto);
        p.setTipoParticipacion(tipo);
        p.setDescripcionParticipante(descripcion);
        return p;
    }

    private int seedTiposCertificado() {
        if (tipoCertificadoRepository.count() > 0) {
            return 0;
        }
        LocalDateTime now = LocalDateTime.now();
        List<TipoCertificado> tipos = List.of(
                new TipoCertificado(null, "Participación", "Certificado estándar de participación.", true, true, now),
                new TipoCertificado(null, "Ponente", "Reconocimiento a ponentes o expositores.", false, true, now),
                new TipoCertificado(null, "Organizador", "Reconocimiento a organizadores del evento.", false, true, now),
                new TipoCertificado(null, "Colaborador", "Reconocimiento a colaboradores y voluntarios.", false, true, now));
        tipoCertificadoRepository.saveAll(tipos);
        return tipos.size();
    }

    private int seedPlantillasPdf() {
        if (plantillaPdfRepository.count() > 0) {
            return 0;
        }
        List<PlantillaPdf> plantillas = List.of(
                buildPlantilla("Plantilla Estándar", "plantilla_estandar.pdf",
                        "{\"nombre\":\"{nombres}\",\"apellidos\":\"{apellidos}\",\"proyecto\":\"{proyecto}\"}"),
                buildPlantilla("Plantilla Ponente", "plantilla_ponente.pdf",
                        "{\"nombre\":\"{nombres}\",\"tema\":\"{tema}\"}"),
                buildPlantilla("Plantilla Organizador", "plantilla_organizador.pdf",
                        "{\"nombre\":\"{nombres}\",\"evento\":\"{proyecto}\"}"));
        plantillaPdfRepository.saveAll(plantillas);
        return plantillas.size();
    }

    private PlantillaPdf buildPlantilla(String nombre, String archivo, String palabrasClave) {
        PlantillaPdf p = new PlantillaPdf();
        p.setNombrePlantilla(nombre);
        p.setArchivo(archivo);
        p.setPalabrasClave(palabrasClave);
        p.setActivo(true);
        return p;
    }

    private int seedCertificados() {
        if (certificadoRepository.count() > 0) {
            return 0;
        }
        List<Participacion> participaciones = participacionRepository.findAll();
        List<TipoCertificado> tipos = tipoCertificadoRepository.findAll();
        if (participaciones.isEmpty() || tipos.isEmpty()) {
            return 0;
        }
        LocalDateTime now = LocalDateTime.now();
        List<Certificado> certificados = List.of(
                buildCertificado(participaciones.get(0), tipos.get(1), "FOLIO-0001", "CERT-2026-0001", "FIRMADO", now),
                buildCertificado(participaciones.get(1), tipos.get(2), "FOLIO-0002", "CERT-2026-0002", "PENDIENTE", now),
                buildCertificado(participaciones.get(2), tipos.get(1), "FOLIO-0003", "CERT-2026-0003", "BORRADOR", now));
        certificadoRepository.saveAll(certificados);
        return certificados.size();
    }

    private Certificado buildCertificado(Participacion p, TipoCertificado tc, String folio, String codigo,
            String estadoFirma, LocalDateTime now) {
        Certificado c = new Certificado();
        c.setIdIntegranteProyecto(p.getId());
        c.setIdParticipante(p.getIdParticipante());
        c.setIdProyecto(p.getIdProyecto());
        c.setIdTipoCertificado(tc.getIdTipoCertificado());
        c.setNumeroFolio(folio);
        c.setTipoParticipacion(p.getTipoParticipacion());
        c.setNumeroRegistro("REG-CERT-" + folio);
        c.setDescripcionUsuario(p.getDescripcionParticipante());
        c.setCodigoCertificado(codigo);
        c.setFechaEmision(LocalDate.now());
        c.setEstadoFirma(estadoFirma);
        c.setGeneradoAt(now);
        if ("FIRMADO".equals(estadoFirma)) {
            c.setFirmadoAt(now);
            c.setArchivoFirmadoPath("/certificados/firmados/" + codigo + ".pdf");
        } else {
            c.setArchivoBorradorPath("/certificados/borradores/" + codigo + ".pdf");
        }
        return c;
    }

    private int seedEnviosCorreo(Usuario admin) {
        if (envioCorreoRepository.count() > 0) {
            return 0;
        }
        List<Certificado> certificados = certificadoRepository.findAll();
        List<Participante> participantes = participanteRepository.findAll();
        if (certificados.isEmpty() || participantes.isEmpty()) {
            return 0;
        }
        LocalDateTime now = LocalDateTime.now();
        List<EnvioCorreo> envios = List.of(
                buildEnvio(admin, certificados.get(0), participantes.get(0), "ENVIADO", false, now),
                buildEnvio(admin, certificados.get(1), participantes.get(1), "PENDIENTE", false, now),
                buildEnvio(admin, certificados.get(2), participantes.get(2), "ERROR", true, now));
        envioCorreoRepository.saveAll(envios);
        return envios.size();
    }

    private EnvioCorreo buildEnvio(Usuario enviadoPor, Certificado cert, Participante destino, String estado,
            boolean esReenvio, LocalDateTime now) {
        EnvioCorreo e = new EnvioCorreo();
        e.setIdEnviadoPor(enviadoPor.getId());
        e.setIdCertificado(cert.getId());
        e.setIdParticipante(destino.getIdParticipante());
        e.setEmailDestino(destino.getEmail());
        e.setPlantillaCorreo("{\"asunto\":\"Certificado DRSEU\",\"variables\":{\"nombre\":\""
                + destino.getNombres() + "\"}}");
        e.setAsunto("Tu certificado DRSEU está listo");
        e.setCuerpo("Hola " + destino.getNombres() + ", adjuntamos tu certificado.");
        e.setEstado(estado);
        e.setEsReenvio(esReenvio);
        if ("ENVIADO".equals(estado)) {
            e.setEnviadoAt(now);
        }
        return e;
    }

    private int seedUsuarioAuditoria(Usuario admin) {
        if (usuarioAuditoriaRepository.count() > 0) {
            return 0;
        }
        List<Usuario> usuarios = usuarioRepository.findAll();
        if (usuarios.size() < 2) {
            return 0;
        }
        List<UsuarioAuditoria> filas = List.of(
                buildAuditoria(usuarios.get(1).getId(), admin.getId(), LocalDate.now().minusDays(5)),
                buildAuditoria(usuarios.get(2).getId(), admin.getId(), LocalDate.now().minusDays(2)),
                buildAuditoria(usuarios.get(3).getId(), admin.getId(), LocalDate.now().minusDays(1)));
        usuarioAuditoriaRepository.saveAll(filas);
        return filas.size();
    }

    private UsuarioAuditoria buildAuditoria(Long idUsuario, Long userCreadoPor, LocalDate cambioPasword) {
        UsuarioAuditoria a = new UsuarioAuditoria();
        a.setIdUsuario(idUsuario);
        a.setUserCreadoPor(userCreadoPor);
        a.setCambioPasword(cambioPasword);
        return a;
    }
}
