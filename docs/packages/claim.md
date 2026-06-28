# Package `claim` — Reclami, sinistri e danni

## Scopo

Gestisce danni merce, ritardi, contestazioni, sinistri, danni veicolo e pratiche assicurative.

## Classi ed enum

| Nome | Tipo | Ruolo sintetico |
|---|---|---|
| `ClaimRules` | Classe | Classe di regole di business del package. |
| `ClaimSeverity` | Enum | Valori controllati usati dalle regole di dominio. |
| `ClaimStatus` | Enum | Valori controllati usati dalle regole di dominio. |
| `ClaimType` | Enum | Valori controllati usati dalle regole di dominio. |
| `TransportClaim` | Classe | Classe di dominio del package. |

## Enum principali

### `ClaimSeverity`

Valori: `LOW`, `MEDIUM`, `HIGH`, `CRITICAL`.

### `ClaimStatus`

Valori: `OPEN`, `UNDER_REVIEW`, `ACCEPTED`, `SETTLED`, `REJECTED`, `CANCELLED`.

### `ClaimType`

Valori: `CARGO_DAMAGE`, `CARGO_LOSS`, `DELAY`, `TEMPERATURE_EXCURSION`, `DOCUMENT_DISPUTE`, `BILLING_DISPUTE`, `VEHICLE_DAMAGE`, `ACCIDENT`, `INSURANCE_CLAIM`, `OTHER`.


## Reclami, danni e sinistri

Il package gestisce sia reclami di trasporto sia casi fleet:

- danni merce;
- ritardi;
- temperatura fuori range;
- danni veicolo;
- sinistro;
- pratica assicurativa.


## Collegamenti con altri package

Questo package non vive isolato: le sue classi vengono usate dalle regole di business e dai casi d’uso futuri.

Per capire il flusso completo leggere anche:

- [`../domain-overview.md`](../domain-overview.md)
- [`../domain-rules.md`](../domain-rules.md)
- [`../domain-package-map.md`](../domain-package-map.md)

## Test collegati

I test si trovano sotto:

```text
src/test/java/it/gabriele/truckflow/domain/claim
```

Quando si modifica questo package, eseguire sempre:

```bash
mvn clean test
```
