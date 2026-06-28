# Package `loadsecurity` — Fissaggio e sicurezza carico

## Scopo

Checklist e dispositivi per bloccare fisicamente la merce durante il viaggio.

## Classi ed enum

| Nome | Tipo | Ruolo sintetico |
|---|---|---|
| `LoadSecuringChecklist` | Classe | Checklist dei dispositivi di fissaggio disponibili/richiesti. |
| `LoadSecuringEquipment` | Classe | Singolo dispositivo di fissaggio con quantità e capacità. |
| `LoadSecuringEquipmentType` | Enum | Valori controllati usati dalle regole di dominio. |

## Enum principali

### `LoadSecuringEquipmentType`

Valori: `RATCHET_STRAP`, `LOAD_BAR`, `ANTI_SLIP_MAT`, `CONTAINMENT_NET`, `EDGE_PROTECTOR`.


## Sicurezza del carico

Il carico deve essere bloccato fisicamente durante il viaggio.

Il package modella:

- cinghie a cricchetto;
- barre fermacarico;
- tappeti antiscivolo;
- reti di contenimento;
- angolari.

La checklist potrà essere collegata alla missione e al controllo pre-partenza dell’autista.


## Collegamenti con altri package

Questo package non vive isolato: le sue classi vengono usate dalle regole di business e dai casi d’uso futuri.

Per capire il flusso completo leggere anche:

- [`../domain-overview.md`](../domain-overview.md)
- [`../domain-rules.md`](../domain-rules.md)
- [`../domain-package-map.md`](../domain-package-map.md)

## Test collegati

I test si trovano sotto:

```text
src/test/java/it/gabriele/truckflow/domain/loadsecurity
```

Quando si modifica questo package, eseguire sempre:

```bash
mvn clean test
```
