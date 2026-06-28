# Package `fuel` — Fuel

## Scopo

Traccia rifornimenti, carte carburante, litri, costo e consumo reale.

## Concetti principali

- `FuelTransaction`
- `FuelCardProvider`
- `FuelConsumptionRules`

## Classi del package

| Classe | Tipo | Ruolo sintetico |
|---|---|---|
| `FuelCardProvider` | enum | Enum di classificazione/valori ammessi. |
| `FuelConsumptionRules` | final class | Calcoli e controlli su consumo reale/anomalie. |
| `FuelTransaction` | final class | Rifornimento carburante. |

## Enum e valori ammessi

- `FuelCardProvider`: `DKV`, `UTA`, `ENI`, `SHELL`, `OTHER`

## Regole di business

- Un rifornimento deve avere veicolo, data, litri, prezzo e odometro validi.
- Il consumo reale può essere calcolato tra due rifornimenti e usato per alert/anomalie.

## Collegamenti con altri package

- fleet, pricing, sustainability, telematics

## Test collegati

- `FuelTransactionTest.java`

## Note di progettazione

Questo package appartiene al domain puro. Non deve contenere codice di database, API esterne, controller web, query SQL, repository concreti o logica di framework.

Le regole devono rimanere testabili con JUnit senza avviare servizi esterni.
