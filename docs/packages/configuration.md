# Package `configuration` — Configurazione sistema

## Scopo

Parametri configurabili, scope e valori sensibili, senza dipendere da file o database.

## Classi ed enum

| Nome | Tipo | Ruolo sintetico |
|---|---|---|
| `ConfigurationCategory` | Enum | Valori controllati usati dalle regole di dominio. |
| `ConfigurationRules` | Classe | Classe di regole di business del package. |
| `ConfigurationScope` | Enum | Valori controllati usati dalle regole di dominio. |
| `ConfigurationValue` | Classe | Classe di dominio del package. |
| `ConfigurationValueType` | Enum | Valori controllati usati dalle regole di dominio. |
| `SystemConfiguration` | Classe | Classe di dominio del package. |

## Enum principali

### `ConfigurationCategory`

Valori: `OPERATION`, `PRICING`, `NOTIFICATION`, `DOCUMENT`, `SECURITY`, `SUSTAINABILITY`, `REPORTING`, `INTEGRATION`.

### `ConfigurationScope`

Valori: `GLOBAL`, `ORGANIZATION`, `CUSTOMER`, `FACILITY`, `USER`.

### `ConfigurationValueType`

Valori: `TEXT`, `BOOLEAN`, `INTEGER`, `DECIMAL`, `PERCENTAGE`, `DURATION_MINUTES`.



## Collegamenti con altri package

Questo package non vive isolato: le sue classi vengono usate dalle regole di business e dai casi d’uso futuri.

Per capire il flusso completo leggere anche:

- [`../domain-overview.md`](../domain-overview.md)
- [`../domain-rules.md`](../domain-rules.md)
- [`../domain-package-map.md`](../domain-package-map.md)

## Test collegati

I test si trovano sotto:

```text
src/test/java/it/gabriele/truckflow/domain/configuration
```

Quando si modifica questo package, eseguire sempre:

```bash
mvn clean test
```
