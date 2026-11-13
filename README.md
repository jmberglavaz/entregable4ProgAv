# Entregable 4 — CI/CD - Juan Martín Berglavaz y Camila García

Aplicación web de adopción de mascotas con Spring Boot, Maven multi-módulo y pipeline declarativo de Jenkins dockerizado.

## Estructura

- `core`: dominio, DTOs, servicio y repositorio. Pruebas unitarias.
- `web`: Spring Boot (REST + Thymeleaf), H2, Actuator. Pruebas unitarias de controller y pruebas de integración.
- `Jenkinsfile`: pipeline declarativo (`Checkout` → `Build` → `Unit Tests` → `Integration Tests` → `Deploy simulado` → `Smoke Test`).
- `Dockerfile` y `docker-compose.yml`: definición de infraestructura para Jenkins con JDK 17, Maven, `curl` y `procps`.

## Requisitos

- JDK 17
- Maven 3.9+
- Docker y Docker Compose (para ejecutar el entorno de CI/CD con Jenkins)
- Puertos `8080`, `8081` y `50000` disponibles

## Ejecución Local (sin Jenkins)

### Compilar y ejecutar todas las pruebas
```bash
mvn clean verify
```

### Levantar la aplicación con Maven
```bash
mvn -pl web -am spring-boot:run
```

### Ejecutar el `.jar` empaquetado
```bash
mvn clean package
java -jar web/target/mascotas-web-1.0.0-SNAPSHOT.jar
```

En ejecución local, la aplicación queda disponible en el puerto `8080`:
- Web: http://localhost:8080/mascotas
- API: http://localhost:8080/api/mascotas
- Health: http://localhost:8080/actuator/health

## Pipeline CI/CD con Jenkins en Docker

1. Levantar el contenedor de Jenkins:
   ```bash
   docker compose up -d --build
   ```
2. Acceder a Jenkins en `http://localhost:8080`.
3. Crear un item de tipo **Multibranch Pipeline** apuntando al repositorio en GitHub (rama `main`, script path `Jenkinsfile`).
4. Configurar **Scan Multibranch Pipeline Triggers** con intervalo de **1 minuto** para detectar nuevos commits automáticamente.
5. Al finalizar el pipeline en verde, el deploy simulado copia el artefacto a `deploy/app.jar` y levanta la aplicación en el puerto `8081`:
   - Web (Producción simulada): http://localhost:8081/mascotas
   - Health check: http://localhost:8081/actuator/health

## Endpoints

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/api/mascotas` | Crear mascota |
| GET | `/api/mascotas` | Listar mascotas (filtros opcionales) |
| GET | `/api/mascotas/{id}` | Obtener por id |
| GET | `/actuator/health` | Health check |