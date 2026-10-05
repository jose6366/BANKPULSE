# BANKPULSE

Proyecto integrador de Diseño de Sistemas (USFQ). BANKPULSE implementa un flujo mínimo de pagos con arquitectura MVC, persistencia PostgreSQL, autenticación de demostración, auditoría y Docker.

## Alcance demostrable
- Login con Spring Security.
- Consulta de cuentas y saldo.
- Creación de pago con validación de saldo.
- Historial y consulta REST de pagos.
- Registro de auditoría al crear pagos.
- `GET /health` público con HTTP 200.

## Stack
Java 21, Spring Boot 3.5.6, Spring MVC, Thymeleaf, Spring Data JPA, Spring Security, PostgreSQL 16, Maven y Docker Compose.

## Estructura
`controller/` recibe solicitudes; `service/` contiene casos de uso; `model/` representa el dominio; `repository/` persiste datos; `templates/` implementa las vistas.

## Requisitos
Docker Desktop/Engine + Docker Compose. Para ejecución sin Docker: Java 21, Maven 3.9+ y PostgreSQL.

## Configuración
1. Copiar `.env.example` a `.env`.
2. No subir `.env` al repositorio.

## Ejecutar con Docker
```bash
cp .env.example .env
docker compose up --build
```
Abrir `http://localhost:8080`.

Usuarios demo:
- `demo` / `demo123`
- `operator` / `operator123`

## Salud y operación comprobable
```bash
curl http://localhost:8080/health
curl -u demo:demo123 http://localhost:8080/api/accounts
curl -u demo:demo123 -H 'Content-Type: application/json' -d '{"sourceAccountId":1,"beneficiary":"Proveedor Demo","amount":25.50}' http://localhost:8080/api/payments
curl -u demo:demo123 http://localhost:8080/api/payments
```
La primera llamada debe devolver HTTP 200. El POST crea la operación del Sprint 1 (HU-BP-03).

## Estado, logs y apagado
```bash
docker compose ps
docker compose logs -f app
docker compose down
```
Para reiniciar también los datos: `docker compose down -v`.

## Pruebas
```bash
mvn test
```
GitHub Actions ejecuta `mvn test` en pushes y Pull Requests hacia `main`.

## Estrategia GitHub Flow
Ver `docs/BRANCHING.md`. Cada historia se desarrolla en `feature/<hu>-descripcion`, con commits descriptivos, Pull Request, revisión de otro integrante y merge.

## Troubleshooting
- Puerto 8080 ocupado: cambiar `APP_PORT` en `.env`.
- DB no saludable: `docker compose logs db` y confirmar variables del `.env`.
- Datos de prueba dañados: `docker compose down -v` y levantar otra vez.
