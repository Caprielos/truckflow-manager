# Package `document` — Document

## Scopo

Gestisce documenti di trasporto, legali, sanitari, ADR, CMR, FIR e allegati di missione.

## Concetti principali

- `TransportDocument`
- `TransportDocumentType`
- `DocumentStatus`
- `DocumentRules`

## Classi del package

| Classe | Tipo | Ruolo sintetico |
|---|---|---|
| `DocumentRules` | final class | Classe statica di regole di business del package. |
| `DocumentStatus` | enum | Enum di classificazione/valori ammessi. |
| `TransportDocument` | final class | Entity o value object del package. |
| `TransportDocumentType` | enum | Enum di classificazione/valori ammessi. |

## Enum e valori ammessi

- `DocumentStatus`: `DRAFT`, `REQUESTED`, `RECEIVED`, `VERIFIED`, `REJECTED`, `EXPIRED`
- `TransportDocumentType`: `CMR_WAYBILL`, `PROOF_OF_DELIVERY`, `DELIVERY_NOTE`, `ADR_TRANSPORT_DOCUMENT`, `TEMPERATURE_LOG`, `INVOICE_COPY`, `INSURANCE_CERTIFICATE`, `VEHICLE_REGISTRATION`, `DRIVER_LICENSE_COPY`, `WASTE_IDENTIFICATION_FORM`, `SAFETY_DATA_SHEET`, `ADR_WRITTEN_INSTRUCTIONS`, `HACCP_SANITATION_DOCUMENT`, `VETERINARY_DOCUMENT`, `OVERSIZED_TRANSPORT_AUTHORIZATION`

## Regole di business

- I documenti obbligatori dipendono da carico e missione.
- Un documento può essere richiesto, caricato, validato o scaduto.

## Collegamenti con altri package

- cargo, compliance, operation, company, claim

## Test collegati

- `DocumentRulesTest.java`

## Note di progettazione

Questo package appartiene al domain puro. Non deve contenere codice di database, API esterne, controller web, query SQL, repository concreti o logica di framework.

Le regole devono rimanere testabili con JUnit senza avviare servizi esterni.
