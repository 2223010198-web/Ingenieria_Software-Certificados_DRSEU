# README_CONECTA_MODULOS — Backend

Guía para incorporar nuevos módulos al backend sin romper lo que ya está implementado.

---

## Estado actual

El módulo de autenticación (HU-01..05) está completamente implementado. Cualquier módulo nuevo se agrega **encima** de esta base sin tocar los archivos listados en la sección "No modificar".

---

## Credenciales — regla de oro

Todas las credenciales viven en `src/main/resources/application-local.properties`.  
Este archivo está en `.gitignore` y **nunca se sube al repositorio**.

Si tu módulo necesita una nueva credencial (API key, bucket S3, etc.), agrégala ahí usando `@Value("${propiedad}")` en el bean correspondiente. Nunca hardcodear en código Java.

Variables actuales definidas en `application-local.properties`:

```
app.dominio          → URL del frontend (ej: http://localhost:4200)
jwt.secret           → Clave de firma del JWT
jwt.expiration-ms    → Duración del token en milisegundos
spring.datasource.*  → Conexión a PostgreSQL
spring.mail.*        → Credenciales SMTP Gmail
```

---

## Archivos que NO debes modificar

Estos archivos pertenecen al módulo Auth. Tocarlos puede romper el login, la recuperación de contraseña o la seguridad de toda la aplicación.

| Archivo | Razón |
|---|---|
| `config/AuthService.java` | Lógica completa de auth |
| `config/EmailService.java` | Envío de correos de recuperación |
| `config/JwtUtil.java` | Generación y validación de tokens JWT |
| `config/JwtAuthFilter.java` | Filtro que procesa el JWT en cada request |
| `config/UsuarioDetailsService.java` | Carga de usuario para Spring Security |
| `entities/PasswordResetToken.java` | Entidad de tokens de reset |
| `repositories/IPasswordResetTokenRepository.java` | Repo de tokens de reset |
| `dtos/LoginRequest.java` | Contrato de login: `{ username, password }` |
| `dtos/LoginResponse.java` | Respuesta de login: `{ token, username, rol }` |
| `dtos/UserProfileDTO.java` | Respuesta de `GET /auth/me` |
| `dtos/RecoverPasswordRequest.java` | Body de recover-password |
| `dtos/ResetPasswordRequest.java` | Body de reset-password |
| `controllers/AuthController.java` | Endpoints de auth |

---

## Archivos que SÍ necesitan merge al agregar módulos

### `pom.xml`
Agrega dependencias nuevas como bloques `<dependency>` adicionales. No reemplaces el archivo completo.

---

### `config/SecurityConfig.java` ⚠️ CRÍTICO

Es el archivo más sensible. Sigue esta estructura al modificarlo:

```java
.authorizeHttpRequests(auth -> auth
    // Públicos Auth — NO TOCAR
    .requestMatchers(
        "/auth/login", "/auth/recover-password", "/auth/reset-password",
        "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"
    ).permitAll()
    .requestMatchers("/seed/admin").permitAll()

    // Por rol — NO TOCAR
    .requestMatchers("/usuarios").hasRole("ADMIN")

    // ← AQUÍ agrega reglas de tu módulo, por ejemplo:
    // .requestMatchers("/public/certificates/verify/**").permitAll()
    // .requestMatchers("/admin/**").hasRole("ADMIN")

    .anyRequest().authenticated()  // ← Esta línea siempre al final
)
```

**CORS:** El origen permitido es `http://localhost:4200`. Si el frontend cambia de puerto, actualiza `corsConfigurationSource()`.

---

### `repositories/UsuarioRepository.java`
Agrega métodos de consulta nuevos al final de la interfaz. No reescribas la interfaz completa.  
Métodos actuales: `findByUsername`, `findByIdParticipante`, `existsByUsername`.

### `repositories/ParticipanteRepository.java`
Métodos actuales: `findByEmail`.  
Agrega los tuyos al final.

### `entities/Usuario.java`
Campos actuales: `id`, `username`, `passwordHash`, `rol`, `activo`, `idParticipante`, `createdAt`.

> **Importante:** `idParticipante` es un `Long` simple, no un `@ManyToOne`. La relación con `Participante` se navega manualmente vía `ParticipanteRepository.findById(idParticipante)`. Si un módulo necesita navegar esta relación con JPA, coordina antes de cambiar el tipo del campo.

---

## Cómo agregar un módulo nuevo (checklist)

- [ ] Crear entidad en `entities/` con sus anotaciones JPA
- [ ] Crear repositorio en `repositories/` extendiendo `JpaRepository`
- [ ] Crear interfaz de servicio en `servicesinterface/`
- [ ] Crear implementación en `servicesimplements/`
- [ ] Crear DTOs de entrada y salida en `dtos/`
- [ ] Crear controller en `controllers/` con `@RequestMapping("/api/tu-modulo")`
- [ ] Si el módulo tiene endpoints públicos → agregarlos en `SecurityConfig.java`
- [ ] Si el módulo tiene credenciales → agregarlas en `application-local.properties`
- [ ] Si el módulo tiene dependencia Maven → agregarla en `pom.xml`

---

## Flujo de autenticación (referencia para nuevos módulos)

Todos los endpoints bajo `.anyRequest().authenticated()` ya están protegidos automáticamente.  
El filtro `JwtAuthFilter` extrae el username del token y lo inyecta en el contexto de seguridad.

Para obtener el usuario autenticado en cualquier controller:
```java
@GetMapping("/mi-endpoint")
public ResponseEntity<?> ejemplo(Authentication authentication) {
    String username = authentication.getName(); // username del token JWT
    // ...
}
```

El token JWT llega en el header: `Authorization: Bearer <token>`  
Duración: configurable en `jwt.expiration-ms` (default: 86400000 ms = 24h)

---

## Roles disponibles

`ADMIN` · `DOCENTE` · `ESTUDIANTE` · `SUPERVISOR`

Definidos en `enums/RolUsuario.java`. El único control activo por rol es:  
`/usuarios` → requiere `ADMIN`.
