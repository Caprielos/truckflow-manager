# Package `loadsecurity` — Fissaggio carico

Modella checklist e dotazioni di fissaggio: cinghie, barre, antiscivolo, protezioni e reti.

## Responsabilità

- LoadSecuringChecklist deve essere coerente con peso e categoria merce.
- Serve prima della partenza missione.

## Classi

- `LoadSecuringChecklist` — modello/domain object del package.
- `LoadSecuringEquipment` — modello/domain object del package.
- `LoadSecuringEquipmentType` — enum con valori: `RATCHET_STRAP`, `LOAD_BAR`, `ANTI_SLIP_MAT`, `CONTAINMENT_NET`, `EDGE_PROTECTOR`.
- `LoadSecuringRules` — classe di regole pure del package.

## Regole importanti

- La dotazione minima dipende da tipo merce e peso.
- La checklist non sostituisce la missione, la abilita.

## Collegamenti

- LoadSecuringChecklist deve essere coerente con peso e categoria merce.
- Serve prima della partenza missione.
