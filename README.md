# Sistema de Gestión de Certificados — DRSEU UNTELS

API RESTful para la gestión integral de certificados digitales, proyectos académicos y participantes de la Dirección de Responsabilidad Social y Extensión Universitaria (DRSEU) de la Universidad Nacional Tecnológica de Lima Sur (UNTELS).

## Descripción

El sistema centraliza y automatiza el ciclo de vida completo de los certificados institucionales: desde el registro de proyectos y participantes, hasta la generación de documentos PDF personalizados, firma digital, distribución por correo electrónico y validación mediante códigos únicos.

### Funcionalidades principales

- **Gestión de Proyectos**: CRUD completo con estados (borrador, pendiente, aprobado, rechazado, anulado), tipos de proyecto y trazabilidad de creación/aprobación.
- **Registro de Participantes**: Administración de personas (alumnos, docentes, externos) con datos de contacto y categoría.
- **Asignación de Participaciones**: Vinculación de participantes a proyectos con tipo de rol jerárquico (coordinador, organizador, ponente, colaborador, participante, voluntario).
- **Configuración de Certificados**: Gestión de tipos de certificado, metadatos (folio, registro, código único) y vinculación con plantillas PDF.
- **Gestión de Plantillas PDF**: Carga y configuración de plantillas base con palabras clave para personalización dinámica.
- **Envío de Correos**: Registro y seguimiento de envíos de certificados por correo electrónico con control de reenvíos.
- **Auditoría de Usuarios**: Trazabilidad de acciones y cambios de contraseña por usuario.
- **Documentación interactiva**: Swagger UI integrado para exploración y prueba de endpoints.

## Stack Tecnológico

| Capa | Tecnología |
|---|---|
| Lenguaje | Java 17 |
| Framework | Spring Boot 4.0.6 |
| Persistencia | Spring Data JPA + Hibernate |
| Base de datos | PostgreSQL |
| Mapeo de objetos | ModelMapper 3.2.6 |
| Reducción de boilerplate | Lombok 1.18.46 |
| Documentación API | SpringDoc OpenAPI 3.0.3 (Swagger UI) |
| Build | Maven (Maven Wrapper incluido) |
| Control de versiones | Git + GitHub |

## Arquitectura

El proyecto implementa una **arquitectura en capas** con separación de responsabilidades mediante interfaces:

```
pe.edu.untels.certificadosdrsu
│
├── controllers/            → Endpoints REST (recibe peticiones HTTP)
├── dtos/                   → Data Transfer Objects (contrato de entrada/salida)
├── entities/               → Entidades JPA (mapeo objeto-relacional)
├── enums/                  → Enumeraciones de dominio
├── repositories/           → Interfaces JPA Repository (acceso a datos)
├── servicesinterface/      → Contratos de servicio (interfaces)
└── servicesimplements/     → Lógica de negocio (implementaciones)
```

```
Cliente HTTP  →  Controller  →  Service (Interface)  →  Repository  →  PostgreSQL
                     ↕                  ↕
                    DTOs             Entities
```

## Endpoints de la API

| Recurso | Base URL | Operaciones |
|---|---|---|
| Proyectos | `/api/proyectos` | GET `/lista`, GET `/{id}`, POST `/nuevo`, PUT `/{id}`, DELETE `/{id}` |
| Certificados | `/api/certificados` | GET `/lista`, GET `/{id}`, POST `/nuevo`, PUT `/{id}`, DELETE `/{id}` |
| Participantes | `/api/participantes` | GET `/lista`, GET `/{id}`, POST `/nuevo`, PUT `/{id}`, DELETE `/{id}` |
| Participaciones | `/api/participaciones` | GET `/lista`, GET `/{id}`, POST `/nuevo`, PUT `/{id}`, DELETE `/{id}` |
| Envíos de Correo | `/api/envios-correo` | GET `/lista`, GET `/{id}`, POST `/nuevo`, PUT `/{id}`, DELETE `/{id}` |
| Plantillas PDF | `/api/plantillas-pdf` | GET, GET `/{id}`, POST, PUT `/{id}`, DELETE `/{id}` |
| Usuarios | `/usuarios` | GET, GET `/{id}`, POST, PUT `/{id}`, DELETE `/{id}` |
| Tipos de Proyecto | `/tipoproyectos` | GET, GET `/{id}`, POST, PUT `/{id}`, DELETE `/{id}` |
| Auditoría de Usuarios | `/api/usuario-auditoria` | GET, GET `/{id}`, POST, PUT `/{id}`, DELETE `/{id}` |

> Documentación interactiva disponible en: `http://localhost:8080/swagger-ui.html`

## Configuración y Ejecución

### Prerrequisitos

- Java 17+
- PostgreSQL 14+
- Maven 3.9+ (o usar el wrapper incluido)

### Configuración de la base de datos

Crear el archivo `src/main/resources/application-local.properties`:

```properties
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.datasource.url=jdbc:postgresql://localhost:5432/certificados_drseu
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña

# Solo si tu módulo necesita enviar correo o firmar JWT localmente:
jwt.secret=una-clave-larga-solo-para-tu-entorno-local
jwt.expiration-ms=86400000
```

> **Cada desarrollador crea y configura su propio `application-local.properties` con su propia base de datos local y sus propias credenciales.** Este archivo está en `.gitignore` (nunca debe commitearse) porque cada quien tiene su propia instancia de PostgreSQL con su propio usuario/contraseña — no existe una base de datos ni credenciales compartidas del equipo. Si accidentalmente lo agregas con `git add -f`, elimínalo del staging antes de commitear.

### Ejecución

```bash
# Clonar el repositorio
git clone https://github.com/2223010198-web/Ingenieria_Software-Certificados_DRSEU.git
cd Ingenieria_Software-Certificados_DRSEU

# Ejecutar con Maven Wrapper
./mvnw spring-boot:run
```

La API estará disponible en `http://localhost:8080`.

## Roadmap

- [ ] Autenticación y autorización con Spring Security + JWT
- [ ] Validación de datos de entrada con Bean Validation
- [ ] Generación automática de certificados PDF (iText / Apache PDFBox)
- [ ] Firma digital de documentos
- [ ] Integración con Gmail SMTP para envío masivo de certificados
- [ ] Portal de validación de certificados mediante código único
- [ ] Frontend SPA con Angular
- [ ] Paginación y filtros avanzados en listados
- [ ] Manejo global de excepciones
- [ ] Tests unitarios y de integración

## Equipo

| Integrante |
|---|
| Marcelo Samuel Molina Vera |
| Jorge Rafael Roncal Saravia |
| Diego Armando Gamero Palacios |
| Junior Joel Pérez Damián |
| Ronny Luis Pumaricra Meneses |

## Licencia

Proyecto académico desarrollado para el curso de Ingeniería de Software — UNTELS.
