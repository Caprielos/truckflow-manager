# Package `drivetime` — Driver Time

## Scopo

Contiene regole base sui tempi di guida e riposo per la pianificazione del viaggio.

## Concetti principali

- `DriverTimeRules`

## Classi del package

| Classe | Tipo | Ruolo sintetico |
|---|---|---|
| `DriverTimeRules` | final class | Classe statica di regole di business del package. |

## Enum e valori ammessi

_Nessuna enum nel package._

## Regole di business

- Guida giornaliera ordinaria 9 ore, estensione a 10 in casi controllati.
- Pausa dopo 4h30 di guida e riposo giornaliero minimo.
- Limiti settimanali e bisettimanali usati per planning futuro.

## Collegamenti con altri package

- driver, route, operation, planning futuro

## Test collegati

- `DriverTimeRulesTest.java`

## Note di progettazione

Questo package appartiene al domain puro. Non deve contenere codice di database, API esterne, controller web, query SQL, repository concreti o logica di framework.

Le regole devono rimanere testabili con JUnit senza avviare servizi esterni.
