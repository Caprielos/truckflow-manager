# Package `claim` — Danni e reclami

Gestisce reclami, danni merce, ritardi, dispute, sinistri, damage inspection e severità.

## Responsabilità

- TransportClaim gestisce reclamo; DamageInspection dettaglia danni rilevati.
- Può nascere da tracking, delivery, documenti o billing.

## Classi

- `ClaimRules` — classe di regole pure del package.
- `ClaimSeverity` — enum con valori: `LOW`, `MEDIUM`, `HIGH`, `CRITICAL`.
- `ClaimStatus` — enum con valori: `OPEN`, `UNDER_REVIEW`, `ACCEPTED`, `SETTLED`, `REJECTED`, `CANCELLED`.
- `ClaimType` — enum con valori: `CARGO_DAMAGE`, `CARGO_LOSS`, `DELAY`, `TEMPERATURE_EXCURSION`, `DOCUMENT_DISPUTE`, `BILLING_DISPUTE`, `VEHICLE_DAMAGE`, `ACCIDENT`, `INSURANCE_CLAIM`, `OTHER`.
- `DamageInspection` — modello/domain object del package.
- `DamageInspectionItem` — modello/domain object del package.
- `TransportClaim` — modello/domain object del package.

## Collegamenti

- TransportClaim gestisce reclamo; DamageInspection dettaglia danni rilevati.
- Può nascere da tracking, delivery, documenti o billing.
