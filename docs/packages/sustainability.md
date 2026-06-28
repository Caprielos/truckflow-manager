# Package `sustainability` — Emissioni e sostenibilità

Calcola emissioni stimate, rating, fuel type e standard emissivi Euro/zero emission.

## Responsabilità

- EmissionEstimate collega distanza, fuel type e standard emissivo.
- Serve per report ESG e KPI CO₂.

## Classi

- `EmissionEstimate` — modello/domain object del package.
- `EmissionRating` — enum con valori: `LOW`, `MEDIUM`, `HIGH`, `VERY_HIGH`.
- `EmissionStandard` — enum con valori: `EURO_0`, `EURO_1`, `EURO_2`, `EURO_3`, `EURO_4`, `EURO_5`, `EURO_6`, `ZERO_EMISSION`, `UNKNOWN`.
- `FuelType` — enum con valori: `DIESEL`, `HVO`, `LNG`, `CNG`, `ELECTRIC`, `HYDROGEN`, `UNKNOWN`.
- `SustainabilityRules` — classe di regole pure del package.

## Collegamenti

- EmissionEstimate collega distanza, fuel type e standard emissivo.
- Serve per report ESG e KPI CO₂.
