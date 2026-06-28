# Package `order` — Ordini di trasporto

## Scopo

Richiesta commerciale del cliente prima di diventare spedizione/missione.

## Classi ed enum

| Nome | Tipo | Ruolo sintetico |
|---|---|---|
| `TransportOrder` | Classe | Classe di dominio del package. |
| `TransportOrderStatus` | Enum | Valori controllati usati dalle regole di dominio. |
| `TransportServiceType` | Enum | Valori controllati usati dalle regole di dominio. |

## Enum principali

### `TransportOrderStatus`

Valori: `DRAFT`, `SUBMITTED`, `ACCEPTED`, `REJECTED`, `CANCELLED`.

### `TransportServiceType`

Valori: `STANDARD`, `EXPRESS`, `REFRIGERATED`, `HAZARDOUS`, `OVERSIZED`.


## Ordine di trasporto

L’ordine è la richiesta commerciale del cliente.

Può essere:

- creato;
- inviato;
- accettato;
- rifiutato;
- cancellato.

Solo un ordine accettato può diventare spedizione.


## Collegamenti con altri package

Questo package non vive isolato: le sue classi vengono usate dalle regole di business e dai casi d’uso futuri.

Per capire il flusso completo leggere anche:

- [`../domain-overview.md`](../domain-overview.md)
- [`../domain-rules.md`](../domain-rules.md)
- [`../domain-package-map.md`](../domain-package-map.md)

## Test collegati

I test si trovano sotto:

```text
src/test/java/it/gabriele/truckflow/domain/order
```

Quando si modifica questo package, eseguire sempre:

```bash
mvn clean test
```
