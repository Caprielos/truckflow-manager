# Roadmap prossimi step

## Stato attuale

Il progetto ha:

- domain ricco;
- application layer;
- repository port;
- infrastructure/memory;
- test unitari e scenario test;
- documentazione tecnica/didattica/cliente.

## Prossimo step consigliato

### 1. Web REST API

Aggiungere Spring Boot e controller REST.

Endpoint esempi:

```text
POST /parking/assignments
POST /missions/plan
POST /inventory/movements
GET /missions/{id}/economics
GET /drivers/{id}/payroll
```

### 2. Infrastructure database

Aggiungere PostgreSQL e repository persistenti.

### 3. Security

Aggiungere login, ruoli e permessi.

### 4. PDF/documenti

Generare DDT, CMR, POD, report missione e fatture.

### 5. Import reali

Integrare file CSV o API per carburante, pedaggi, banca, telematica.

## Cosa non fare ora

Non aggiungere infinite classi domain senza use case. Il domain è già ampio. Ora serve rendere l'app usabile tramite API e database.
