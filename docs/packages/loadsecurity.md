# Package `loadsecurity` — Load Security

## Scopo

Gestisce dispositivi di fissaggio del carico e checklist di sicurezza.

## Concetti principali

- `LoadSecuringChecklist`
- `LoadSecuringEquipment`
- `LoadSecuringEquipmentType`
- `LoadSecuringRules`

## Classi del package

| Classe | Tipo | Ruolo sintetico |
|---|---|---|
| `LoadSecuringChecklist` | final class | Checklist dispositivi di fissaggio del carico. |
| `LoadSecuringEquipment` | final class | Entity o value object del package. |
| `LoadSecuringEquipmentType` | enum | Enum di classificazione/valori ammessi. |
| `LoadSecuringRules` | final class | Regole per verificare dotazione minima e sicurezza carico. |

## Enum e valori ammessi

- `LoadSecuringEquipmentType`: `RATCHET_STRAP`, `LOAD_BAR`, `ANTI_SLIP_MAT`, `CONTAINMENT_NET`, `EDGE_PROTECTOR`

## Regole di business

- Cinghie, barre, tappeti, reti e angolari possono essere richiesti in base al carico e all’allestimento.
- La checklist verifica quantità e portata minima dei dispositivi.

## Collegamenti con altri package

- cargo, fleet, operation, compliance

## Test collegati

- `LoadSecuringChecklistTest.java`

## Note di progettazione

Questo package appartiene al domain puro. Non deve contenere codice di database, API esterne, controller web, query SQL, repository concreti o logica di framework.

Le regole devono rimanere testabili con JUnit senza avviare servizi esterni.
