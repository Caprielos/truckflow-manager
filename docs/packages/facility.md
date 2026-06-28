# Package `facility` — Facility logistiche

## Scopo

Depositi, magazzini, punti di carico/scarico e loro caratteristiche.

## Classi ed enum

| Nome | Tipo | Ruolo sintetico |
|---|---|---|
| `Facility` | Classe | Classe di dominio del package. |
| `FacilityType` | Enum | Valori controllati usati dalle regole di dominio. |

## Enum principali

### `FacilityType`

Valori: `WAREHOUSE`, `DEPOT`, `CUSTOMER_SITE`, `SUPPLIER_SITE`, `CROSS_DOCK`, `TERMINAL`, `PORT`, `AIRPORT`, `MAINTENANCE_CENTER`.



## Collegamenti con altri package

Questo package non vive isolato: le sue classi vengono usate dalle regole di business e dai casi d’uso futuri.

Per capire il flusso completo leggere anche:

- [`../domain-overview.md`](../domain-overview.md)
- [`../domain-rules.md`](../domain-rules.md)
- [`../domain-package-map.md`](../domain-package-map.md)

## Test collegati

I test si trovano sotto:

```text
src/test/java/it/gabriele/truckflow/domain/facility
```

Quando si modifica questo package, eseguire sempre:

```bash
mvn clean test
```
