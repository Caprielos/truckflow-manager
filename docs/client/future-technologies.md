# Tecnologie future previste

Il progetto oggi è Java puro con domain, application e infrastructure/memory. Le tecnologie future consigliate sono:

## Backend

- Java 21 come target stabile.
- Spring Boot per REST API, dependency injection e configurazione.
- Spring Web per controller REST.
- Spring Validation per validazione input.
- Spring Security per login, ruoli e permessi.

## Database

- PostgreSQL come database principale.
- Flyway o Liquibase per migrazioni schema.
- JPA/Hibernate solo nello strato infrastructure, non nel domain.

## Test

- JUnit 5 per unit test e scenario test.
- Mockito per mock quando servirà.
- Testcontainers per test con database reale PostgreSQL.

## Documenti

- Generazione PDF per DDT, CMR, POD, report missione e fatture.
- Template HTML/PDF separati dalla logica domain.

## API esterne future

- Carte carburante.
- Pedaggi/Telepass.
- Telematica GPS.
- Tachigrafo.
- Sistemi contabili.
- Banche.

## Frontend futuro

- React o Angular per dashboard web.
- Tabelle operative per ufficio traffico.
- Dashboard economica.
- Vista deposito/parcheggi.
- Vista flotta.
