# `domain/compliance`

Regole generali di conformità tra cargo, driver, veicolo e documenti.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `ComplianceRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | — | isVehicleCompliantForShipment, isDriverCompliantForShipment, isRouteCompliantForShipment, isAssignmentCompliant, requiresSpecialComplianceChecks |
