# Package `availability` — Disponibilità risorse

## Scopo

Gestisce finestre di disponibilità per autisti, veicoli, convogli, trailer e facility.

## Classi ed enum

| Nome | Tipo | Ruolo sintetico |
|---|---|---|
| `AvailabilityResourceType` | Enum | Valori controllati usati dalle regole di dominio. |
| `AvailabilityRules` | Classe | Classe di regole di business del package. |
| `AvailabilityStatus` | Enum | Valori controllati usati dalle regole di dominio. |
| `ResourceAvailability` | Classe | Classe di dominio del package. |

## Enum principali

### `AvailabilityResourceType`

Valori: `DRIVER`, `VEHICLE`, `VEHICLE_COMBINATION`, `TRAILER`, `FACILITY`.

### `AvailabilityStatus`

Valori: `AVAILABLE`, `RESERVED`, `ASSIGNED`, `UNAVAILABLE`, `MAINTENANCE`, `ON_LEAVE`.



## Collegamenti con altri package

Questo package non vive isolato: le sue classi vengono usate dalle regole di business e dai casi d’uso futuri.

Per capire il flusso completo leggere anche:

- [`../domain-overview.md`](../domain-overview.md)
- [`../domain-rules.md`](../domain-rules.md)
- [`../domain-package-map.md`](../domain-package-map.md)

## Test collegati

I test si trovano sotto:

```text
src/test/java/it/gabriele/truckflow/domain/availability
```

Quando si modifica questo package, eseguire sempre:

```bash
mvn clean test
```
