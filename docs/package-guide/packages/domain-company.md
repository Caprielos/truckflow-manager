# `domain/company`

Azienda di trasporto, licenze aziendali e autorizzazioni operative.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `CompanyComplianceRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | — | hasBaseRoadHaulageAuthorizations, canOperateInternationalTransport, canTransportCargo |
| `CompanyLicense` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | type, expiresAt, notes | of, getType, getExpiresAt, getNotes, isValidOn, equals, hashCode |
| `CompanyLicenseType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | — |
| `TransportCompany` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_CODE_LENGTH, MAX_NAME_LENGTH, companyCode, businessName, vatNumber, licenses, notes | of, getCompanyCode, getBusinessName, getVatNumber, getLicenses, getNotes, hasLicense, hasValidLicense |
