# Package `driver` — Autisti e qualifiche

Gestisce autisti, patenti, CQC, ADR, qualifiche operative e certificati con scadenze reali.

## Responsabilità

- Driver usa set compatibili con i vecchi test e nuovi DriverCertificate con scadenza.
- DriverRules valuta patente, CQC, ADR e qualifiche operative.

## Classi

- `Driver` — modello/domain object del package.
- `DriverAdrCertificateType` — enum con valori: `ADR_BASIC`, `ADR_TANK`, `ADR_CLASS_1_EXPLOSIVES`, `ADR_CLASS_7_RADIOACTIVE`.
- `DriverCertificate` — modello/domain object del package.
- `DriverCertificateType` — enum con valori: `LICENSE_B`, `LICENSE_C1`, `LICENSE_C`, `LICENSE_BE`, `LICENSE_C1E`, `LICENSE_CE`, `CQC_GOODS`, `ADR_BASIC`, `ADR_TANK`, `ADR_CLASS_1_EXPLOSIVES`, `ADR_CLASS_7_RADIOACTIVE`, `TRUCK_MOUNTED_CRANE`, `AERIAL_PLATFORM`, `FORKLIFT`, `EARTH_MOVING_MACHINES`….
- `DriverLicenseCategory` — enum con valori: `B`, `C1`, `C`, `BE`, `C1E`, `CE`, `E`.
- `DriverOperationalQualification` — enum con valori: `TEMPERATURE_CONTROLLED_TRANSPORT`, `INTERNATIONAL_TRANSPORT`, `HIGH_VALUE_CARGO`, `OVERSIZED_CARGO`, `TRUCK_MOUNTED_CRANE`, `AERIAL_PLATFORM`, `FORKLIFT`, `EARTH_MOVING_MACHINES`, `LIVE_ANIMAL_TRANSPORT`, `VEHICLE_RECOVERY_OPERATION`, `BULK_TRANSPORT`, `WASTE_TRANSPORT`.
- `DriverProfessionalQualification` — enum con valori: `CQC_GOODS`.
- `DriverRules` — classe di regole pure del package.
- `DriverStatus` — enum con valori: `AVAILABLE`, `ASSIGNED`, `ON_LEAVE`, `SUSPENDED`, `INACTIVE`.

## Regole importanti

- Il driver può avere vecchie categorie compatibili e nuovi certificati con validità temporale.
- La CQC e l’ADR non sono semplici booleani: sono requisiti professionali/documentali.

## Collegamenti

- Driver usa set compatibili con i vecchi test e nuovi DriverCertificate con scadenza.
- DriverRules valuta patente, CQC, ADR e qualifiche operative.
