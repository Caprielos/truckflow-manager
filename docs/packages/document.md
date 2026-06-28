# Package `document` — Documenti di trasporto

Gestisce documenti richiesti, ricevuti, verificati, scaduti o rifiutati: CMR, POD, FIR, ADR, ATP, HACCP, veterinari.

## Responsabilità

- DocumentRules collega cargo/servizio a documenti richiesti.
- TransportDocument traccia stato e verifica.

## Classi

- `DocumentRules` — classe di regole pure del package.
- `DocumentStatus` — enum con valori: `DRAFT`, `REQUESTED`, `RECEIVED`, `VERIFIED`, `REJECTED`, `EXPIRED`.
- `TransportDocument` — modello/domain object del package.
- `TransportDocumentType` — enum con valori: `CMR_WAYBILL`, `PROOF_OF_DELIVERY`, `DELIVERY_NOTE`, `ADR_TRANSPORT_DOCUMENT`, `TEMPERATURE_LOG`, `INVOICE_COPY`, `INSURANCE_CERTIFICATE`, `VEHICLE_REGISTRATION`, `DRIVER_LICENSE_COPY`, `WASTE_IDENTIFICATION_FORM`, `SAFETY_DATA_SHEET`, `ADR_WRITTEN_INSTRUCTIONS`, `HACCP_SANITATION_DOCUMENT`, `VETERINARY_DOCUMENT`, `OVERSIZED_TRANSPORT_AUTHORIZATION`.

## Collegamenti

- DocumentRules collega cargo/servizio a documenti richiesti.
- TransportDocument traccia stato e verifica.
