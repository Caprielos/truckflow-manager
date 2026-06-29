# Package `driver` — Autisti e abilitazioni

Modella autisti, patenti, CQC, ADR, qualifiche operative, certificati con validità e stato autista.

## Percorso

```text
src/main/java/it/gabriele/truckflow/domain/driver
```

## Classi

- `Driver`
- `DriverAdrCertificateType`
- `DriverCertificate`
- `DriverCertificateType`
- `DriverLicenseCategory`
- `DriverOperationalQualification`
- `DriverProfessionalQualification`
- `DriverRules`
- `DriverStatus`

## Test collegati

- `DriverRulesTest`
- `DriverTest`

## Ruolo nel sistema

Questo package contribuisce al domain model e deve restare indipendente da database, controller REST e framework esterni.
