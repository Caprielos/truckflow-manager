# Roadmap prossimi step

## Stato attuale

Il progetto ora ha:

- domain molto ricco;
- application layer;
- repository port;
- infrastructure memory;
- test di scenario.

## Prossimo step consigliato

### 1. Web REST API

Aggiungere controller REST con Spring Boot.

Esempi:

```text
POST /parking/assignments
POST /shipments/from-order
POST /missions/plan
POST /missions/{id}/close
GET /economics/missions/{id}
```

### 2. DTO e mapper

Creare classi request/response separate dal domain.

### 3. Database

Aggiungere PostgreSQL e repository persistenti.

### 4. Sicurezza

Aggiungere autenticazione, ruoli e permessi.

### 5. Generazione documenti

Generare PDF per DDT, CMR, POD e report missione.

### 6. Frontend

Creare dashboard web per dispatch, flotta, economics e magazzino.
