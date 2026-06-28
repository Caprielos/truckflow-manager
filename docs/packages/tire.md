# Package `tire` — Pneumatici

Traccia singole gomme fisiche, RFID, posizione ruota, installazioni, rotazioni, stato e soglia battistrada.

## Responsabilità

- TireInstallation lega temporaneamente una gomma fisica a veicolo e posizione.
- TireRotationEvent permette storico rotazioni.

## Classi

- `Tire` — modello/domain object del package.
- `TireInstallation` — modello/domain object del package.
- `TireRotationEvent` — modello/domain object del package.
- `TireRules` — classe di regole pure del package.
- `TireStatus` — enum con valori: `NEW`, `RETREADED`, `REGROOVED`, `IN_USE`, `STORED`, `DISPOSED`.
- `WheelPosition` — modello/domain object del package.
- `WheelSide` — enum con valori: `LEFT`, `RIGHT`, `CENTER`.
- `WheelSlot` — enum con valori: `SINGLE`, `INNER`, `OUTER`.

## Regole importanti

- Una gomma fisica può cambiare veicolo e posizione.
- Il battistrada minimo genera alert/sostituzione.

## Collegamenti

- TireInstallation lega temporaneamente una gomma fisica a veicolo e posizione.
- TireRotationEvent permette storico rotazioni.
