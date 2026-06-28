# Package `document` — Documenti di trasporto

## Scopo

Documenti richiesti dalla missione, dal carico o dai certificati.

## Classi ed enum

| Nome | Tipo | Ruolo sintetico |
|---|---|---|
| `DocumentRules` | Classe | Classe di regole di business del package. |
| `DocumentStatus` | Enum | Valori controllati usati dalle regole di dominio. |
| `TransportDocument` | Classe | Documento associato a trasporto, veicolo, autista o missione. |
| `TransportDocumentType` | Enum | Tipi documento: CMR, POD, FIR, SDS, HACCP, veterinario, ADR e altri. |

## Enum principali

### `DocumentStatus`

Valori: `DRAFT`, `REQUESTED`, `RECEIVED`, `VERIFIED`, `REJECTED`, `EXPIRED`.

### `TransportDocumentType`

Valori: `CMR_WAYBILL`, `PROOF_OF_DELIVERY`, `DELIVERY_NOTE`, `ADR_TRANSPORT_DOCUMENT`, `TEMPERATURE_LOG`, `INVOICE_COPY`, `INSURANCE_CERTIFICATE`, `VEHICLE_REGISTRATION`, `DRIVER_LICENSE_COPY`, `WASTE_IDENTIFICATION_FORM`, `SAFETY_DATA_SHEET`, `ADR_WRITTEN_INSTRUCTIONS`, `HACCP_SANITATION_DOCUMENT`, `VETERINARY_DOCUMENT`, `OVERSIZED_TRANSPORT_AUTHORIZATION`.


## Documenti missione e compliance

I documenti non sono semplici allegati: fanno parte della validità operativa del trasporto.

Esempi:

```text
CMR_WAYBILL                   -> trasporti internazionali
WASTE_IDENTIFICATION_FORM     -> rifiuti
SAFETY_DATA_SHEET             -> ADR
ADR_WRITTEN_INSTRUCTIONS      -> ADR
HACCP_SANITATION_DOCUMENT     -> alimentari
VETERINARY_DOCUMENT           -> animali vivi
OVERSIZED_TRANSPORT_AUTHORIZATION -> trasporti eccezionali
```

`DocumentRules` gestisce stati e validazione.


## Collegamenti con altri package

Questo package non vive isolato: le sue classi vengono usate dalle regole di business e dai casi d’uso futuri.

Per capire il flusso completo leggere anche:

- [`../domain-overview.md`](../domain-overview.md)
- [`../domain-rules.md`](../domain-rules.md)
- [`../domain-package-map.md`](../domain-package-map.md)

## Test collegati

I test si trovano sotto:

```text
src/test/java/it/gabriele/truckflow/domain/document
```

Quando si modifica questo package, eseguire sempre:

```bash
mvn clean test
```
