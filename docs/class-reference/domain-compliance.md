# Domain `compliance` spiegato

Regole generali di conformità tra cargo, driver, veicolo e documenti.

## Classi principali

### `ComplianceRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Metodi pubblici principali:

- `isVehicleCompliantForShipment()`
- `isDriverCompliantForShipment()`
- `isRouteCompliantForShipment()`
- `isAssignmentCompliant()`
- `requiresSpecialComplianceChecks()`
