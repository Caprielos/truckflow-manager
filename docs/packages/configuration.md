# Package `configuration` — Configurazioni dominio

Permette di rappresentare valori configurabili per regole aziendali, soglie, parametri e impostazioni.

## Percorso

```text
src/main/java/it/gabriele/truckflow/domain/configuration
```

## Classi

- `ConfigurationCategory`
- `ConfigurationRules`
- `ConfigurationScope`
- `ConfigurationValue`
- `ConfigurationValueType`
- `SystemConfiguration`

## Test collegati

- `ConfigurationRulesTest`

## Ruolo nel sistema

Questo package contribuisce al domain model e deve restare indipendente da database, controller REST e framework esterni.
