# Package `configuration` — Configurazioni dominio

Modella configurazioni tipizzate, categorie e scope applicativi senza dipendere da un database.

## Responsabilità

- SystemConfiguration conserva parametri modificabili.
- Scope permette configurazioni globali, per organizzazione, cliente, facility o utente.

## Classi

- `ConfigurationCategory` — enum con valori: `OPERATION`, `PRICING`, `NOTIFICATION`, `DOCUMENT`, `SECURITY`, `SUSTAINABILITY`, `REPORTING`, `INTEGRATION`.
- `ConfigurationRules` — classe di regole pure del package.
- `ConfigurationScope` — enum con valori: `GLOBAL`, `ORGANIZATION`, `CUSTOMER`, `FACILITY`, `USER`.
- `ConfigurationValue` — modello/domain object del package.
- `ConfigurationValueType` — enum con valori: `TEXT`, `BOOLEAN`, `INTEGER`, `DECIMAL`, `PERCENTAGE`, `DURATION_MINUTES`.
- `SystemConfiguration` — modello/domain object del package.

## Collegamenti

- SystemConfiguration conserva parametri modificabili.
- Scope permette configurazioni globali, per organizzazione, cliente, facility o utente.
