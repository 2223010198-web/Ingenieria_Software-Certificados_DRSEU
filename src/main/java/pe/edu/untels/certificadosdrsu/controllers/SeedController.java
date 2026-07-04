package pe.edu.untels.certificadosdrsu.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.untels.certificadosdrsu.entities.Certificado;
import pe.edu.untels.certificadosdrsu.entities.EnvioCorreo;
import pe.edu.untels.certificadosdrsu.entities.Participacion;
import pe.edu.untels.certificadosdrsu.entities.Participante;
import pe.edu.untels.certificadosdrsu.entities.PasswordResetToken;
import pe.edu.untels.certificadosdrsu.entities.PlantillaPdf;
import pe.edu.untels.certificadosdrsu.entities.Proyecto;
import pe.edu.untels.certificadosdrsu.entities.TipoCertificado;
import pe.edu.untels.certificadosdrsu.entities.TipoProyecto;
import pe.edu.untels.certificadosdrsu.entities.Usuario;
import pe.edu.untels.certificadosdrsu.entities.UsuarioAuditoria;
import pe.edu.untels.certificadosdrsu.enums.EstadoProyecto;
import pe.edu.untels.certificadosdrsu.repositories.ICertificadoRepository;
import pe.edu.untels.certificadosdrsu.repositories.IEnvioCorreoRepository;
import pe.edu.untels.certificadosdrsu.repositories.IPasswordResetTokenRepository;
import pe.edu.untels.certificadosdrsu.repositories.IPlantillaPdfRepository;
import pe.edu.untels.certificadosdrsu.repositories.IProyectoRepository;
import pe.edu.untels.certificadosdrsu.repositories.ITipoCertificadoRepository;
import pe.edu.untels.certificadosdrsu.repositories.ITipoProyectoRepository;
import pe.edu.untels.certificadosdrsu.repositories.IUsuarioAuditoriaRepository;
import pe.edu.untels.certificadosdrsu.repositories.ParticipacionRepository;
import pe.edu.untels.certificadosdrsu.repositories.ParticipanteRepository;
import pe.edu.untels.certificadosdrsu.repositories.UsuarioRepository;

@RestController
@RequestMapping("/seed")
public class SeedController {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private ParticipanteRepository participanteRepository;
    @Autowired private ITipoProyectoRepository tipoProyectoRepository;
    @Autowired private ITipoCertificadoRepository tipoCertificadoRepository;
    @Autowired private IPlantillaPdfRepository plantillaPdfRepository;
    @Autowired private IProyectoRepository proyectoRepository;
    @Autowired private ParticipacionRepository participacionRepository;
    @Autowired private ICertificadoRepository certificadoRepository;
    @Autowired private IEnvioCorreoRepository envioCorreoRepository;
    @Autowired private IUsuarioAuditoriaRepository usuarioAuditoriaRepository;
    @Autowired private IPasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired private PasswordEncoder passwordEncoder;

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

    @PostMapping("/all")
    @Transactional
    public ResponseEntity<Map<String, Object>> seedAll() {
        Map<String, Object> resumen = new LinkedHashMap<>();

        // 1. Participantes
        Participante p1, p2, p3, p4;
        if (participanteRepository.count() == 0) {
            LocalDateTime now = LocalDateTime.now();
            p1 = participanteRepository.save(new Participante(null, "Juan", "Pérez Loayza",
                    "juan.perez@untels.edu.pe", "987654321", true, now, now));
            p2 = participanteRepository.save(new Participante(null, "María", "García Ríos",
                    "maria.garcia@untels.edu.pe", "987112233", true, now, now));
            p3 = participanteRepository.save(new Participante(null, "Luis", "Ramos Quispe",
                    "luis.ramos@untels.edu.pe", "987445566", true, now, now));
            p4 = participanteRepository.save(new Participante(null, "Ana", "Torres Mendoza",
                    "ana.torres@untels.edu.pe", "987778899", true, now, now));
            resumen.put("participantes", 4);
        } else {
            List<Participante> todos = participanteRepository.findAll();
            p1 = todos.get(0); p2 = todos.get(1); p3 = todos.get(2);
            p4 = todos.size() > 3 ? todos.get(3) : todos.get(0);
            resumen.put("participantes", "ya existían (" + todos.size() + ")");
        }

        // 2. Usuarios
        Usuario admin, jperez, mgarcia, lramos;
        if (usuarioRepository.count() == 0) {
            admin = new Usuario();
            admin.setUsername("admin");
            admin.setPasswordHash(passwordEncoder.encode("admin123"));
            admin.setRol("ADMIN");
            admin.setActivo(true);
            admin = usuarioRepository.save(admin);

            jperez = new Usuario();
            jperez.setUsername("jperez");
            jperez.setPasswordHash(passwordEncoder.encode("docente123"));
            jperez.setRol("DOCENTE");
            jperez.setActivo(true);
            jperez.setIdParticipante(p1.getIdParticipante());
            jperez = usuarioRepository.save(jperez);

            mgarcia = new Usuario();
            mgarcia.setUsername("mgarcia");
            mgarcia.setPasswordHash(passwordEncoder.encode("super123"));
            mgarcia.setRol("SUPERVISOR");
            mgarcia.setActivo(true);
            mgarcia.setIdParticipante(p2.getIdParticipante());
            mgarcia = usuarioRepository.save(mgarcia);

            lramos = new Usuario();
            lramos.setUsername("lramos");
            lramos.setPasswordHash(passwordEncoder.encode("estudi123"));
            lramos.setRol("ESTUDIANTE");
            lramos.setActivo(true);
            lramos.setIdParticipante(p3.getIdParticipante());
            lramos = usuarioRepository.save(lramos);

            resumen.put("usuarios", 4);
        } else {
            List<Usuario> todos = usuarioRepository.findAll();
            admin = todos.get(0);
            jperez = todos.size() > 1 ? todos.get(1) : admin;
            mgarcia = todos.size() > 2 ? todos.get(2) : admin;
            lramos = todos.size() > 3 ? todos.get(3) : admin;
            resumen.put("usuarios", "ya existían (" + todos.size() + ")");
        }

        // 3. TipoProyecto
        TipoProyecto tp1, tp2, tp3;
        if (tipoProyectoRepository.count() == 0) {
            tp1 = tipoProyectoRepository.save(new TipoProyecto(null, "Proyecto Social"));
            tp2 = tipoProyectoRepository.save(new TipoProyecto(null, "Proyecto Ambiental"));
            tp3 = tipoProyectoRepository.save(new TipoProyecto(null, "Extensión Universitaria"));
            resumen.put("tipos_proyecto", 3);
        } else {
            List<TipoProyecto> todos = tipoProyectoRepository.findAll();
            tp1 = todos.get(0);
            tp2 = todos.size() > 1 ? todos.get(1) : tp1;
            tp3 = todos.size() > 2 ? todos.get(2) : tp1;
            resumen.put("tipos_proyecto", "ya existían");
        }

        // 4. TipoCertificado
        TipoCertificado tc1, tc2, tc3;
        if (tipoCertificadoRepository.count() == 0) {
            LocalDateTime now = LocalDateTime.now();
            tc1 = tipoCertificadoRepository.save(new TipoCertificado(null, "Participación",
                    "Certificado de participación general", true, true, now));
            tc2 = tipoCertificadoRepository.save(new TipoCertificado(null, "Ponente",
                    "Certificado para expositores", false, true, now));
            tc3 = tipoCertificadoRepository.save(new TipoCertificado(null, "Organizador",
                    "Certificado para organizadores del evento", false, true, now));
            resumen.put("tipos_certificado", 3);
        } else {
            List<TipoCertificado> todos = tipoCertificadoRepository.findAll();
            tc1 = todos.get(0);
            tc2 = todos.size() > 1 ? todos.get(1) : tc1;
            tc3 = todos.size() > 2 ? todos.get(2) : tc1;
            resumen.put("tipos_certificado", "ya existían");
        }

        // 5. PlantillaPdf
        if (plantillaPdfRepository.count() == 0) {
            PlantillaPdf pl1 = new PlantillaPdf();
            pl1.setNombrePlantilla("Plantilla Participación");
            pl1.setArchivo("plantillas/plantilla_participacion.pdf");
            pl1.setPalabrasClave("{\"nombre\":\"\",\"proyecto\":\"\",\"fecha\":\"\"}");
            pl1.setActivo(true);
            plantillaPdfRepository.save(pl1);

            PlantillaPdf pl2 = new PlantillaPdf();
            pl2.setNombrePlantilla("Plantilla Ponente");
            pl2.setArchivo("plantillas/plantilla_ponente.pdf");
            pl2.setPalabrasClave("{\"nombre\":\"\",\"tema\":\"\",\"fecha\":\"\"}");
            pl2.setActivo(true);
            plantillaPdfRepository.save(pl2);

            PlantillaPdf pl3 = new PlantillaPdf();
            pl3.setNombrePlantilla("Plantilla Organizador");
            pl3.setArchivo("plantillas/plantilla_organizador.pdf");
            pl3.setPalabrasClave("{\"nombre\":\"\",\"evento\":\"\",\"fecha\":\"\"}");
            pl3.setActivo(true);
            plantillaPdfRepository.save(pl3);
            resumen.put("plantillas_pdf", 3);
        } else {
            resumen.put("plantillas_pdf", "ya existían");
        }

        // 6. Proyecto
        Proyecto pr1, pr2, pr3;
        if (proyectoRepository.count() == 0) {
            pr1 = new Proyecto();
            pr1.setTitulo("Feria de Ciencias UNTELS 2026");
            pr1.setDescripcion("Feria anual para exhibir proyectos científicos de estudiantes.");
            pr1.setDocumentoAprobacion("docs/aprobacion_feria_2026.pdf");
            pr1.setFechaAprobacion(LocalDate.of(2026, 3, 15));
            pr1.setEstado(EstadoProyecto.APROBADO);
            pr1.setTipoProyecto(tp3);
            pr1.setCreadoPor(admin);
            pr1.setAprobadoPor(admin);
            pr1 = proyectoRepository.save(pr1);

            pr2 = new Proyecto();
            pr2.setTitulo("Campaña de Reciclaje en Villa El Salvador");
            pr2.setDescripcion("Recolección y clasificación de residuos en zonas del distrito.");
            pr2.setEstado(EstadoProyecto.PENDIENTE);
            pr2.setTipoProyecto(tp2);
            pr2.setCreadoPor(jperez);
            pr2 = proyectoRepository.save(pr2);

            pr3 = new Proyecto();
            pr3.setTitulo("Taller de Programación para Escolares");
            pr3.setDescripcion("Curso gratuito de introducción a la programación para colegios cercanos.");
            pr3.setDocumentoAprobacion("docs/aprobacion_taller_prog.pdf");
            pr3.setFechaAprobacion(LocalDate.of(2026, 4, 10));
            pr3.setEstado(EstadoProyecto.APROBADO);
            pr3.setTipoProyecto(tp1);
            pr3.setCreadoPor(admin);
            pr3.setAprobadoPor(admin);
            pr3 = proyectoRepository.save(pr3);

            resumen.put("proyectos", 3);
        } else {
            List<Proyecto> todos = proyectoRepository.findAll();
            pr1 = todos.get(0);
            pr2 = todos.size() > 1 ? todos.get(1) : pr1;
            pr3 = todos.size() > 2 ? todos.get(2) : pr1;
            resumen.put("proyectos", "ya existían");
        }

        // 7. Participacion
        Participacion pa1, pa2, pa3;
        if (participacionRepository.count() == 0) {
            pa1 = participacionRepository.save(new Participacion(null,
                    p1.getIdParticipante(), pr1.getIdProyecto(), "PONENTE",
                    "Expositor en la mesa de biotecnología"));
            pa2 = participacionRepository.save(new Participacion(null,
                    p2.getIdParticipante(), pr2.getIdProyecto(), "ORGANIZADOR",
                    "Coordinadora general de la campaña"));
            pa3 = participacionRepository.save(new Participacion(null,
                    p3.getIdParticipante(), pr3.getIdProyecto(), "COLABORADOR",
                    "Instructor de módulos de Python básico"));
            resumen.put("participaciones", 3);
        } else {
            List<Participacion> todos = participacionRepository.findAll();
            pa1 = todos.get(0);
            pa2 = todos.size() > 1 ? todos.get(1) : pa1;
            pa3 = todos.size() > 2 ? todos.get(2) : pa1;
            resumen.put("participaciones", "ya existían");
        }

        // 8. Certificado
        Certificado c1, c2, c3;
        if (certificadoRepository.count() == 0) {
            c1 = new Certificado();
            c1.setIdIntegranteProyecto(pa1.getId());
            c1.setIdParticipante(p1.getIdParticipante());
            c1.setIdTipoCertificado(tc2.getIdTipoCertificado());
            c1.setIdProyecto(pr1.getIdProyecto());
            c1.setNumeroFolio("F-2026-001");
            c1.setTipoParticipacion("PONENTE");
            c1.setNumeroRegistro("REG-2026-001");
            c1.setDescripcionUsuario("Ponente en Feria de Ciencias 2026");
            c1.setCodigoCertificado("CERT-2026-001");
            c1.setFechaEmision(LocalDate.of(2026, 4, 1));
            c1.setArchivoBorradorPath("certificados/borrador/cert-001.pdf");
            c1.setArchivoFirmadoPath("certificados/firmado/cert-001.pdf");
            c1.setEstadoFirma("FIRMADO");
            c1.setGeneradoAt(LocalDateTime.now().minusDays(5));
            c1.setFirmadoAt(LocalDateTime.now().minusDays(4));
            c1.setIdGeneradoPor(admin.getId());
            c1 = certificadoRepository.save(c1);

            c2 = new Certificado();
            c2.setIdIntegranteProyecto(pa2.getId());
            c2.setIdParticipante(p2.getIdParticipante());
            c2.setIdTipoCertificado(tc3.getIdTipoCertificado());
            c2.setIdProyecto(pr2.getIdProyecto());
            c2.setNumeroFolio("F-2026-002");
            c2.setTipoParticipacion("ORGANIZADOR");
            c2.setNumeroRegistro("REG-2026-002");
            c2.setDescripcionUsuario("Organizadora Campaña de Reciclaje");
            c2.setCodigoCertificado("CERT-2026-002");
            c2.setFechaEmision(LocalDate.of(2026, 5, 20));
            c2.setArchivoBorradorPath("certificados/borrador/cert-002.pdf");
            c2.setEstadoFirma("PENDIENTE");
            c2.setGeneradoAt(LocalDateTime.now().minusDays(2));
            c2.setIdGeneradoPor(admin.getId());
            c2 = certificadoRepository.save(c2);

            c3 = new Certificado();
            c3.setIdIntegranteProyecto(pa3.getId());
            c3.setIdParticipante(p3.getIdParticipante());
            c3.setIdTipoCertificado(tc1.getIdTipoCertificado());
            c3.setIdProyecto(pr3.getIdProyecto());
            c3.setNumeroFolio("F-2026-003");
            c3.setTipoParticipacion("COLABORADOR");
            c3.setNumeroRegistro("REG-2026-003");
            c3.setDescripcionUsuario("Colaborador Taller Programación");
            c3.setCodigoCertificado("CERT-2026-003");
            c3.setFechaEmision(LocalDate.of(2026, 6, 15));
            c3.setArchivoBorradorPath("certificados/borrador/cert-003.pdf");
            c3.setEstadoFirma("BORRADOR");
            c3.setGeneradoAt(LocalDateTime.now().minusDays(1));
            c3.setIdGeneradoPor(admin.getId());
            c3 = certificadoRepository.save(c3);

            resumen.put("certificados", 3);
        } else {
            List<Certificado> todos = certificadoRepository.findAll();
            c1 = todos.get(0);
            c2 = todos.size() > 1 ? todos.get(1) : c1;
            c3 = todos.size() > 2 ? todos.get(2) : c1;
            resumen.put("certificados", "ya existían");
        }

        // 9. EnvioCorreo
        if (envioCorreoRepository.count() == 0) {
            EnvioCorreo e1 = new EnvioCorreo();
            e1.setIdEnviadoPor(admin.getId());
            e1.setIdCertificado(c1.getId());
            e1.setIdParticipante(p1.getIdParticipante());
            e1.setEmailDestino(p1.getEmail());
            e1.setPlantillaCorreo("{\"template\":\"envio_certificado\"}");
            e1.setAsunto("Su certificado DRSEU - Feria de Ciencias 2026");
            e1.setCuerpo("Estimado(a) Juan, adjunto encontrará su certificado como Ponente.");
            e1.setEstado("ENVIADO");
            e1.setEsReenvio(false);
            e1.setEnviadoAt(LocalDateTime.now().minusDays(3));
            envioCorreoRepository.save(e1);

            EnvioCorreo e2 = new EnvioCorreo();
            e2.setIdEnviadoPor(admin.getId());
            e2.setIdCertificado(c2.getId());
            e2.setIdParticipante(p2.getIdParticipante());
            e2.setEmailDestino(p2.getEmail());
            e2.setPlantillaCorreo("{\"template\":\"envio_certificado\"}");
            e2.setAsunto("Su certificado DRSEU - Campaña de Reciclaje");
            e2.setCuerpo("Estimada María, su certificado como Organizadora está en proceso.");
            e2.setEstado("PENDIENTE");
            e2.setEsReenvio(false);
            envioCorreoRepository.save(e2);

            EnvioCorreo e3 = new EnvioCorreo();
            e3.setIdEnviadoPor(admin.getId());
            e3.setIdCertificado(c3.getId());
            e3.setIdParticipante(p3.getIdParticipante());
            e3.setEmailDestino(p3.getEmail());
            e3.setPlantillaCorreo("{\"template\":\"envio_certificado\"}");
            e3.setAsunto("Su certificado DRSEU - Taller de Programación");
            e3.setCuerpo("Estimado Luis, hubo un problema al enviar su certificado. Reintentando.");
            e3.setEstado("FALLIDO");
            e3.setEsReenvio(true);
            e3.setEnviadoAt(LocalDateTime.now().minusHours(6));
            envioCorreoRepository.save(e3);
            resumen.put("envios_correo", 3);
        } else {
            resumen.put("envios_correo", "ya existían");
        }

        // 10. UsuarioAuditoria
        if (usuarioAuditoriaRepository.count() == 0) {
            UsuarioAuditoria a1 = new UsuarioAuditoria();
            a1.setIdUsuario(jperez.getId());
            a1.setUserCreadoPor(admin.getId());
            a1.setCambioPasword(LocalDate.of(2026, 5, 1));
            usuarioAuditoriaRepository.save(a1);

            UsuarioAuditoria a2 = new UsuarioAuditoria();
            a2.setIdUsuario(mgarcia.getId());
            a2.setUserCreadoPor(admin.getId());
            a2.setCambioPasword(LocalDate.of(2026, 6, 10));
            usuarioAuditoriaRepository.save(a2);

            UsuarioAuditoria a3 = new UsuarioAuditoria();
            a3.setIdUsuario(lramos.getId());
            a3.setUserCreadoPor(admin.getId());
            a3.setCambioPasword(LocalDate.of(2026, 6, 25));
            usuarioAuditoriaRepository.save(a3);
            resumen.put("usuario_auditoria", 3);
        } else {
            resumen.put("usuario_auditoria", "ya existían");
        }

        // 11. PasswordResetToken (solo usado o expirado)
        if (passwordResetTokenRepository.count() == 0) {
            // jperez: usado (usado=true, aún no vencido pero ya consumido)
            PasswordResetToken t1 = new PasswordResetToken(
                    "11111111-1111-1111-1111-111111111111", jperez,
                    LocalDateTime.now().plusDays(2));
            t1.setUsado(true);
            passwordResetTokenRepository.save(t1);

            // mgarcia: expirado (usado=false, fecha pasada)
            PasswordResetToken t2 = new PasswordResetToken(
                    "22222222-2222-2222-2222-222222222222", mgarcia,
                    LocalDateTime.now().minusDays(3));
            passwordResetTokenRepository.save(t2);

            // lramos: expirado y usado
            PasswordResetToken t3 = new PasswordResetToken(
                    "33333333-3333-3333-3333-333333333333", lramos,
                    LocalDateTime.now().minusDays(10));
            t3.setUsado(true);
            passwordResetTokenRepository.save(t3);
            resumen.put("password_reset_tokens", 3);
        } else {
            resumen.put("password_reset_tokens", "ya existían");
        }

        resumen.put("status", "OK");
        return ResponseEntity.ok(resumen);
    }
}
