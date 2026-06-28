# Package `claim` — Claim

## Scopo

Gestisce reclami, danni, sinistri e controlli visivi legati a mezzi e missioni.

## Concetti principali

- `TransportClaim`
- `DamageInspection`
- `DamageInspectionItem`
- `ClaimType`
- `ClaimSeverity`
- `ClaimStatus`
- `ClaimRules`

## Classi del package

| Classe | Tipo | Ruolo sintetico |
|---|---|---|
| `ClaimRules` | final class | Classe statica di regole di business del package. |
| `ClaimSeverity` | enum | Enum di classificazione/valori ammessi. |
| `ClaimStatus` | enum | Enum di classificazione/valori ammessi. |
| `ClaimType` | enum | Enum di classificazione/valori ammessi. |
| `DamageInspection` | final class | Controllo danni visivo pre/post viaggio. |
| `DamageInspectionItem` | final class | Entity o value object del package. |
| `TransportClaim` | final class | Pratica sinistro/reclamo. |

## Enum e valori ammessi

- `ClaimSeverity`: `LOW`, `MEDIUM`, `HIGH`, `CRITICAL`
- `ClaimStatus`: `OPEN`, `UNDER_REVIEW`, `ACCEPTED`, `SETTLED`, `REJECTED`, `CANCELLED`
- `ClaimType`: `CARGO_DAMAGE`, `CARGO_LOSS`, `DELAY`, `TEMPERATURE_EXCURSION`, `DOCUMENT_DISPUTE`, `BILLING_DISPUTE`, `VEHICLE_DAMAGE`, `ACCIDENT`, `INSURANCE_CLAIM`, `OTHER`

## Regole di business

- Un sinistro deve avere gravità, stato e informazioni minime.
- Le ispezioni danni permettono controlli pre-partenza o post-missione.

## Collegamenti con altri package

- fleet per mezzo danneggiato
- operation/tracking per viaggio
- document per CAI/foto/documenti
- billing per costi o rimborsi

## Test collegati

- `ClaimRulesTest.java`
- `TransportClaimTest.java`

## Note di progettazione

Questo package appartiene al domain puro. Non deve contenere codice di database, API esterne, controller web, query SQL, repository concreti o logica di framework.

Le regole devono rimanere testabili con JUnit senza avviare servizi esterni.
