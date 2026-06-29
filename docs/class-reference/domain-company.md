# Package `domain.company`

Azienda di trasporto e licenze operative.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| CompanyComplianceRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.company. | hasBaseRoadHaulageAuthorizations, canOperateInternationalTransport, canTransportCargo |
| CompanyLicense | class | Classe del package domain.company; rappresenta un concetto del modello TruckFlow. | of, getType, getExpiresAt, getNotes, isValidOn, equals, hashCode |
| CompanyLicenseType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | - |
| TransportCompany | class | Classe del package domain.company; rappresenta un concetto del modello TruckFlow. | of, getCompanyCode, getBusinessName, getVatNumber, getLicenses, getNotes, hasLicense, hasValidLicense, hasAnyValidEnvironmentalLicense |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
