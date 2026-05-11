package pe.edu.untels.certificadosdrsu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.untels.certificadosdrsu.entities.*;
import pe.edu.untels.certificadosdrsu.enums.*;
import pe.edu.untels.certificadosdrsu.servicesinterface.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/seed")
public class SeedController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private ITipoProyectoService tipoProyectoService;

    @Autowired
    private IProyectoService proyectoService;

    @Autowired
    private pe.edu.untels.certificadosdrsu.servicesinterface.ParticipanteService participanteService;

    @PostMapping("/cargar")
    public ResponseEntity<?> cargarDatos() {
        Map<String, Integer> resultados = new LinkedHashMap<>();

        resultados.put("TipoProyecto", seedTipoProyecto());
        resultados.put("Usuario", seedUsuario());
        resultados.put("Participante", seedParticipante());
        resultados.put("Proyecto", seedProyecto());

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "mensaje", "Datos de prueba cargados exitosamente",
                "registros", resultados));
    }

    private int seedTipoProyecto() {
        int count = 0;
        String[] nombres = {
                "Investigación", "Proyección Social", "Extensión Universitaria",
                "Innovación Tecnológica", "Desarrollo Sostenible"
        };
        for (String nombre : nombres) {
            TipoProyecto tp = new TipoProyecto();
            tp.setTipo_proyecto_nombre(nombre);
            tipoProyectoService.insert(tp);
            count++;
        }
        return count;
    }

    private int seedUsuario() {
        int count = 0;
        String[][] datos = {
                { "admin", "72938475", "admin@untels.edu.pe", "hash123", "ADMIN" },
                { "Juan Carlos Mendoza", "65839201", "juan.mendoza@untels.edu.pe", "hash456", "DOCENTE" },
                { "María Elena Vásquez", "58201937", "maria.vasquez@untels.edu.pe", "hash789", "DOCENTE" },
                { "Carlos Alberto Ruiz", "49283017", "carlos.ruiz@untels.edu.pe", "hash012", "SUPERVISOR" },
                { "Ana Lucía Torres", "37465928", "ana.torres@untels.edu.pe", "hash345", "ESTUDIANTE" }
        };
        for (String[] d : datos) {
            Usuario u = new Usuario();
            u.setNombreCompleto(d[0]);
            u.setDni(d[1]);
            u.setEmail(d[2]);
            u.setPasswordHash(d[3]);
            u.setRol(RolUsuario.valueOf(d[4]));
            u.setEsTemporal(false);
            u.setActivo(true);
            u.setCreatedAt(LocalDateTime.now());
            usuarioService.insert(u);
            count++;
        }
        return count;
    }

    private int seedParticipante() {
        int count = 0;
        String[][] datos = {
                { "72938475", "Luis Fernando", "García Quispe", "luis.garcia@email.com", "987654321", "COORDINADOR" },
                { "65839201", "Rosa Mercedes", "López Sánchez", "rosa.lopez@email.com", "987654322", "PONENTE" },
                { "58201937", "Diego Alejandro", "Ramírez Flores", "diego.ramirez@email.com", "987654323",
                        "ORGANIZADOR" },
                { "49283017", "Sofía Carmen", "Mamani Condori", "sofia.mamani@email.com", "987654324", "COLABORADOR" },
                { "37465928", "Jorge Luis", "Huaranga Pérez", "jorge.huaranga@email.com", "987654325", "VOLUNTARIO" }
        };
        for (String[] d : datos) {
            Participante p = new Participante();
            p.setDni(d[0]);
            p.setNombres(d[1]);
            p.setApellidos(d[2]);
            p.setEmail(d[3]);
            p.setCelular(d[4]);
            p.setCategoria(CategoriaParticipante.valueOf(d[5]));
            p.setActivo(true);
            p.setUsuarioId(1);
            participanteService.insert(p);
            count++;
        }
        return count;
    }

    private int seedProyecto() {
        List<Usuario> usuarios = usuarioService.list();
        List<TipoProyecto> tipos = tipoProyectoService.list();

        if (usuarios.isEmpty() || tipos.isEmpty()) {
            return 0;
        }

        String[][] datos = {
                { "Sistema de Gestión Académica", "Plataforma web para administración de notas y matrículas",
                        "REG-2026-001", "APROBADO" },
                { "App de Seguimiento de Prácticas", "Aplicación móvil para monitoreo de prácticas pre-profesionales",
                        "REG-2026-002", "PENDIENTE" },
                { "Portal de Investigación", "Repositorio digital de proyectos de investigación universitaria",
                        "REG-2026-003", "BORRADOR" },
                { "Sistema de Certificados Digital", "Generación y validación de certificados con código QR",
                        "REG-2026-004", "APROBADO" },
                { "Plataforma de Eventos Académicos", "Gestión de inscripción y asistencia a eventos universitarios",
                        "REG-2026-005", "RECHAZADO" }
        };

        int count = 0;
        for (String[] d : datos) {
            Proyecto p = new Proyecto();
            p.setTitulo(d[0]);
            p.setDescripcion(d[1]);
            p.setNumeroRegistro(d[2]);
            p.setEstado(EstadoProyecto.valueOf(d[3]));
            p.setCreadoPor(usuarios.get(count % usuarios.size()));
            p.setTipoProyecto(tipos.get(count % tipos.size()));
            p.setCreatedAt(LocalDateTime.now());
            proyectoService.insert(p);
            count++;
        }
        return count;
    }
}
