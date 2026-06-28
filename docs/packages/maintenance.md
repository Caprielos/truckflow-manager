# Package `maintenance` — Manutenzione

## Scopo

Ordini di manutenzione, guasti, tagliandi, controlli tecnici e stati.

## Classi ed enum

| Nome | Tipo | Ruolo sintetico |
|---|---|---|
| `MaintenanceRules` | Classe | Classe di regole di business del package. |
| `MaintenanceStatus` | Enum | Valori controllati usati dalle regole di dominio. |
| `MaintenanceType` | Enum | Valori controllati usati dalle regole di dominio. |
| `MaintenanceWorkOrder` | Classe | Classe di dominio del package. |

## Enum principali

### `MaintenanceStatus`

Valori: `OPEN`, `SCHEDULED`, `IN_PROGRESS`, `COMPLETED`, `CANCELLED`.

### `MaintenanceType`

Valori: `ROUTINE_SERVICE`, `SAFETY_INSPECTION`, `TIRE_REPLACEMENT`, `REPAIR`, `REFRIGERATION_UNIT_SERVICE`, `ADR_TANK_INSPECTION`, `BREAKDOWN`, `ENGINE_SERVICE`, `AIR_DRYER_FILTER_REPLACEMENT`, `BRAKE_WEAR_CHECK`, `TIRE_ROTATION`, `DRIVER_DEFECT_TICKET`, `DOWNTIME`.


## Manutenzione

Il package gestisce manutenzioni programmate e guasti.

Dopo il refactor sono presenti anche tipi più realistici:

- tagliando motore;
- filtro essiccatore;
- controllo freni;
- giro gomme;
- ticket autista;
- fermo macchina;
- manutenzione gruppo frigo;
- ispezione cisterna ADR.


## Collegamenti con altri package

Questo package non vive isolato: le sue classi vengono usate dalle regole di business e dai casi d’uso futuri.

Per capire il flusso completo leggere anche:

- [`../domain-overview.md`](../domain-overview.md)
- [`../domain-rules.md`](../domain-rules.md)
- [`../domain-package-map.md`](../domain-package-map.md)

## Test collegati

I test si trovano sotto:

```text
src/test/java/it/gabriele/truckflow/domain/maintenance
```

Quando si modifica questo package, eseguire sempre:

```bash
mvn clean test
```
