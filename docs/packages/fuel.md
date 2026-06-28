# Package `fuel` — Carburante e consumi

Gestisce transazioni carburante, fuel card provider e regole di consumo/anomalia tra rifornimenti.

## Responsabilità

- FuelTransaction viene confrontata con transazioni precedenti.
- FuelConsumptionRules segnala consumi anomali.

## Classi

- `FuelCardProvider` — enum con valori: `DKV`, `UTA`, `ENI`, `SHELL`, `OTHER`.
- `FuelConsumptionRules` — classe di regole pure del package.
- `FuelTransaction` — modello/domain object del package.

## Regole importanti

- Consumi fuori soglia sono anomalie, non errori matematici.
- La differenza odometrica deve essere coerente tra rifornimenti.

## Collegamenti

- FuelTransaction viene confrontata con transazioni precedenti.
- FuelConsumptionRules segnala consumi anomali.
