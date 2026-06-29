# Package `document` — Documenti trasporto

Gestisce documenti richiesti, bolla/DDT strutturata, bundle documentale spedizione e stati documentali.

## Percorso

```text
src/main/java/it/gabriele/truckflow/domain/document
```

## Classi

- `DeliveryNote`
- `DeliveryNoteLine`
- `DocumentRules`
- `DocumentStatus`
- `ShipmentDocumentBundle`
- `TransportDocument`
- `TransportDocumentType`

## Test collegati

- `DeliveryNoteAndDocumentBundleTest`
- `DocumentRulesTest`

## Ruolo nel sistema

Questo package contribuisce al domain model e deve restare indipendente da database, controller REST e framework esterni.
