# Entregable 4 — CI/CD - Juan Martín Berglavaz y Camila García

Aplicación web de adopción de mascotas con Spring Boot, Maven multi-módulo y pipeline declarativo de Jenkins.

## Estructura

- `core`: dominio, DTOs, servicio y repositorio. Pruebas unitarias.
- `web`: Spring Boot (REST + Thymeleaf), H2, actuator. Pruebas unitarias de controller y pruebas de integración.
- `Jenkinsfile`: pipeline declarativo (build → unit → integration → deploy simulado → smoke test).

## Requisitos

- JDK 17
- Maven 3.9+
- Jenkins (para el pipeline)

## Compilar y testear

```bash
mvn clean verify
```

## Ejecutar la aplicación

```bash
mvn -pl web spring-boot:run
```

- Web: http://localhost:8080/mascotas
- API: http://localhost:8080/api/mascotas
- Health: http://localhost:8080/actuator/health

## Ejecutar el jar empaquetado

```bash
mvn clean package
java -jar web/target/mascotas-web-1.0.0-SNAPSHOT.jar
```

## Pipeline Jenkins

1. Crear item **Pipeline** apuntando al repo GitHub.
2. Seleccionar **Pipeline script from SCM** → Git → branch `main` → script path `Jenkinsfile`.
3. Configurar webhook en GitHub: `http://<jenkins>/github-webhook/`.
4. El deploy simulado deja el jar en `deploy/app.jar` y levanta la app en `:8081`.

## Endpoints

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/api/mascotas` | Crear mascota |
| GET | `/api/mascotas` | Listar mascotas (filtros opcionales) |
| GET | `/api/mascotas/{id}` | Obtener por id |
| GET | `/actuator/health` | Health check |