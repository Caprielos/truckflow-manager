# Package `availability` — Disponibilità risorse

Rappresenta disponibilità, assegnazione, manutenzione, ferie e indisponibilità di driver, veicoli, convogli e facility.

## Responsabilità

- Serve per bloccare risorse già assegnate, in manutenzione o non disponibili.
- Application layer futuro userà queste regole prima di creare missioni.

## Classi

- `AvailabilityResourceType` — enum con valori: `DRIVER`, `VEHICLE`, `VEHICLE_COMBINATION`, `TRAILER`, `FACILITY`.
- `AvailabilityRules` — classe di regole pure del package.
- `AvailabilityStatus` — enum con valori: `AVAILABLE`, `RESERVED`, `ASSIGNED`, `UNAVAILABLE`, `MAINTENANCE`, `ON_LEAVE`.
- `ResourceAvailability` — modello/domain object del package.

## Collegamenti

- Serve per bloccare risorse già assegnate, in manutenzione o non disponibili.
- Application layer futuro userà queste regole prima di creare missioni.
