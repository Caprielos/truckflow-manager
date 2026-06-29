# Package `domain.compliance`

Regole trasversali di conformità.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| ComplianceRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.compliance. | isVehicleCompliantForShipment, isDriverCompliantForShipment, isRouteCompliantForShipment, isAssignmentCompliant, requiresSpecialComplianceChecks |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
