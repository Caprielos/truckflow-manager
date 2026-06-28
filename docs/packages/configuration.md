# Package `configuration` — Configuration

## Scopo

Gestisce configurazioni di sistema, profili e valori configurabili senza hardcodare tutto nel codice.

## Concetti principali

- `SystemConfiguration`
- `ConfigurationValue`
- `ConfigurationCategory`
- `ConfigurationScope`
- `ConfigurationValueType`
- `ConfigurationRules`

## Classi del package

| Classe | Tipo | Ruolo sintetico |
|---|---|---|
| `ConfigurationCategory` | enum | Enum di classificazione/valori ammessi. |
| `ConfigurationRules` | final class | Classe statica di regole di business del package. |
| `ConfigurationScope` | enum | Enum di classificazione/valori ammessi. |
| `ConfigurationValue` | final class | Entity o value object del package. |
| `ConfigurationValueType` | enum | Enum di classificazione/valori ammessi. |
| `SystemConfiguration` | final class | Entity o value object del package. |

## Enum e valori ammessi

- `ConfigurationCategory`: `OPERATION`, `PRICING`, `NOTIFICATION`, `DOCUMENT`, `SECURITY`, `SUSTAINABILITY`, `REPORTING`, `INTEGRATION`
- `ConfigurationScope`: `GLOBAL`, `ORGANIZATION`, `CUSTOMER`, `FACILITY`, `USER`
- `ConfigurationValueType`: `TEXT`, `BOOLEAN`, `INTEGER`, `DECIMAL`, `PERCENTAGE`, `DURATION_MINUTES`

## Regole di business

- Chiavi sensibili come PASSWORD, SECRET, TOKEN o API_KEY devono essere considerate riservate.
- Scope e tipo valore devono essere coerenti.

## Collegamenti con altri package

- infrastructure futura per lettura da file/DB
- pricing/legal limits per configurazioni paese-specifiche

## Test collegati

- `ConfigurationRulesTest.java`

## Note di progettazione

Questo package appartiene al domain puro. Non deve contenere codice di database, API esterne, controller web, query SQL, repository concreti o logica di framework.

Le regole devono rimanere testabili con JUnit senza avviare servizi esterni.
