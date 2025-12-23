# StailenceBack

API backend para Stailence (gestión de citas B2B)

Requisitos
- Java 17
- Maven (se recomienda usar el wrapper `mvnw.cmd` incluido)
- MySQL o MariaDB

Variables de entorno (properties)
- `SPRING_DATASOURCE_URL` o configurar `spring.datasource.url` en `application.properties`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `APP_JWT_SECRET` -> secreto para JWT (mínimo 256 bits para HS256)

Comandos útiles (Windows, cmd.exe)

Compilar y empaquetar:
```cmd
.\mvnw.cmd -DskipTests package
```

Ejecutar la app local:
```cmd
java -jar target\StailenceBack-0.0.1-SNAPSHOT.jar
```

Endpoints principales
- POST /api/auth/register
- POST /api/auth/login
- POST /api/auth/refresh
- POST /api/auth/logout

Swagger UI
- http://localhost:8080/swagger-ui.html

Notas
- Para desarrollo usa `application.properties` con `spring.jpa.hibernate.ddl-auto=update` y una BD local.
- Refresh tokens se almacenan en la tabla `refresh_tokens`.

Fecha: 2025-12-21

