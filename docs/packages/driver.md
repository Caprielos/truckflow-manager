# Package `driver` — Autisti, patenti e abilitazioni

## Scopo

Patenti, CQC, ADR, patentini operativi e regole di assegnazione autista.

## Classi ed enum

| Nome | Tipo | Ruolo sintetico |
|---|---|---|
| `Driver` | Classe | Entity dell’autista con patenti, CQC, ADR, qualifiche operative e stato. |
| `DriverAdrCertificateType` | Enum | Certificati ADR del conducente. |
| `DriverLicenseCategory` | Enum | Patenti B, C1, C, BE, C1E, CE. |
| `DriverOperationalQualification` | Enum | Patentini e competenze operative speciali. |
| `DriverProfessionalQualification` | Enum | Valori controllati usati dalle regole di dominio. |
| `DriverRules` | Classe | Regole per assegnare un autista a convogli, carichi e spedizioni. |
| `DriverStatus` | Enum | Valori controllati usati dalle regole di dominio. |

## Enum principali

### `DriverAdrCertificateType`

Valori: `ADR_BASIC`, `ADR_TANK`, `ADR_CLASS_1_EXPLOSIVES`, `ADR_CLASS_7_RADIOACTIVE`.

### `DriverLicenseCategory`

Valori: `B`, `C1`, `C`, `BE`, `C1E`, `CE`.

### `DriverOperationalQualification`

Valori: `TEMPERATURE_CONTROLLED_TRANSPORT`, `INTERNATIONAL_TRANSPORT`, `HIGH_VALUE_CARGO`, `OVERSIZED_CARGO`, `TRUCK_MOUNTED_CRANE`, `AERIAL_PLATFORM`, `FORKLIFT`, `EARTH_MOVING_MACHINES`, `LIVE_ANIMAL_TRANSPORT`, `VEHICLE_RECOVERY_OPERATION`, `BULK_TRANSPORT`, `WASTE_TRANSPORT`.

### `DriverProfessionalQualification`

Valori: `CQC_GOODS`.

### `DriverStatus`

Valori: `AVAILABLE`, `ASSIGNED`, `ON_LEAVE`, `SUSPENDED`, `INACTIVE`.


## Patenti

Le patenti presenti sono:

```text
B, C1, C, BE, C1E, CE
```

La patente dipende da:

- massa del mezzo;
- tipo di convoglio;
- presenza di rimorchio o semirimorchio.

Non dipende dal fatto che il mezzo sia frigo, cisterna o centinato.

## CQC, ADR e patentini

Il dominio separa:

```text
DriverLicenseCategory          -> patente
DriverProfessionalQualification -> CQC merci
DriverAdrCertificateType        -> ADR base/cisterna/classe 1/classe 7
DriverOperationalQualification  -> patentini e competenze operative
```

## Regole di assegnazione

`DriverRules` verifica:

- autista assegnabile;
- patente per il convoglio;
- CQC merci;
- ADR se il carico lo richiede;
- qualifiche operative per spedizioni speciali.


## Collegamenti con altri package

Questo package non vive isolato: le sue classi vengono usate dalle regole di business e dai casi d’uso futuri.

Per capire il flusso completo leggere anche:

- [`../domain-overview.md`](../domain-overview.md)
- [`../domain-rules.md`](../domain-rules.md)
- [`../domain-package-map.md`](../domain-package-map.md)

## Test collegati

I test si trovano sotto:

```text
src/test/java/it/gabriele/truckflow/domain/driver
```

Quando si modifica questo package, eseguire sempre:

```bash
mvn clean test
```
