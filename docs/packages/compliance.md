# Package `compliance` — Compliance

## Scopo

Coordina controlli di conformità tra merce, mezzo, autista, documenti e regole generali.

## Concetti principali

- `ComplianceRules`

## Classi del package

| Classe | Tipo | Ruolo sintetico |
|---|---|---|
| `ComplianceRules` | final class | Classe statica di regole di business del package. |

## Enum e valori ammessi

_Nessuna enum nel package._

## Regole di business

- Verifica se un carico richiede ADR, ATP, documenti o qualifiche speciali.
- Centralizza controlli trasversali senza spostare logica tecnica fuori dai package proprietari.

## Collegamenti con altri package

- cargo, driver, fleet, document, company, operation

## Test collegati

- `ComplianceRulesTest.java`

## Note di progettazione

Questo package appartiene al domain puro. Non deve contenere codice di database, API esterne, controller web, query SQL, repository concreti o logica di framework.

Le regole devono rimanere testabili con JUnit senza avviare servizi esterni.
