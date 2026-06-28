# Package `driver` — Driver

## Scopo

Gestisce anagrafica autista, patenti, CQC, ADR, certificati operativi e idoneità alla missione.

## Concetti principali

- `Driver`
- `DriverLicenseCategory`
- `DriverCertificate`
- `DriverCertificateType`
- `DriverProfessionalQualification`
- `DriverAdrCertificateType`
- `DriverOperationalQualification`
- `DriverStatus`
- `DriverRules`

## Classi del package

| Classe | Tipo | Ruolo sintetico |
|---|---|---|
| `Driver` | final class | Entity dell’autista. |
| `DriverAdrCertificateType` | enum | Enum di classificazione/valori ammessi. |
| `DriverCertificate` | final class | Certificato/abilitazione con scadenza del conducente. |
| `DriverCertificateType` | enum | Enum di classificazione/valori ammessi. |
| `DriverLicenseCategory` | enum | Enum di classificazione/valori ammessi. |
| `DriverOperationalQualification` | enum | Enum di classificazione/valori ammessi. |
| `DriverProfessionalQualification` | enum | Enum di classificazione/valori ammessi. |
| `DriverRules` | final class | Regole di idoneità del conducente rispetto a mezzo, carico e missione. |
| `DriverStatus` | enum | Enum di classificazione/valori ammessi. |

## Enum e valori ammessi

- `DriverAdrCertificateType`: `ADR_BASIC`, `ADR_TANK`, `ADR_CLASS_1_EXPLOSIVES`, `ADR_CLASS_7_RADIOACTIVE`
- `DriverCertificateType`: `LICENSE_B`, `LICENSE_C1`, `LICENSE_C`, `LICENSE_BE`, `LICENSE_C1E`, `LICENSE_CE`, `CQC_GOODS`, `ADR_BASIC`, `ADR_TANK`, `ADR_CLASS_1_EXPLOSIVES`, `ADR_CLASS_7_RADIOACTIVE`, `TRUCK_MOUNTED_CRANE`, `AERIAL_PLATFORM`, `FORKLIFT`, `EARTH_MOVING_MACHINES`, `LIVE_ANIMAL_TRANSPORT`, `TEMPERATURE_CONTROLLED_TRANSPORT`, `OVERSIZED_TRANSPORT`, `HIGH_VALUE_CARGO`, `INTERNATIONAL_TRANSPORT`
- `DriverLicenseCategory`: `C1`, `BE`, `C1E`, `CE`
- `DriverOperationalQualification`: `TEMPERATURE_CONTROLLED_TRANSPORT`, `INTERNATIONAL_TRANSPORT`, `HIGH_VALUE_CARGO`, `OVERSIZED_CARGO`, `TRUCK_MOUNTED_CRANE`, `AERIAL_PLATFORM`, `FORKLIFT`, `EARTH_MOVING_MACHINES`, `LIVE_ANIMAL_TRANSPORT`, `VEHICLE_RECOVERY_OPERATION`, `BULK_TRANSPORT`, `WASTE_TRANSPORT`
- `DriverProfessionalQualification`: `CQC_GOODS`
- `DriverStatus`: `AVAILABLE`, `ASSIGNED`, `ON_LEAVE`, `SUSPENDED`, `INACTIVE`

## Regole di business

- Le patenti dipendono da peso e combinazione: B, C1, C, BE, C1E, CE.
- CQC, ADR e patentini operativi devono essere validi se richiesti.
- Autisti sospesi, assenti o inattivi non sono assegnabili.

## Collegamenti con altri package

- fleet per mezzo/convoglio
- cargo per ADR, bestiame, frigo, rifiuti
- drivetime per ore guida
- operation per missione

## Test collegati

- `DriverRulesTest.java`
- `DriverTest.java`

## Note di progettazione

Questo package appartiene al domain puro. Non deve contenere codice di database, API esterne, controller web, query SQL, repository concreti o logica di framework.

Le regole devono rimanere testabili con JUnit senza avviare servizi esterni.
