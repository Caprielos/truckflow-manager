# Package `company` — Azienda e licenze

Modella l’impresa di trasporto e le licenze operative necessarie: albo, REN, licenza comunitaria, albo gestori ambientali.

## Responsabilità

- TransportCompany conserva licenze aziendali.
- CompanyComplianceRules decide se l’azienda può fare internazionale o rifiuti.

## Classi

- `CompanyComplianceRules` — classe di regole pure del package.
- `CompanyLicense` — modello/domain object del package.
- `CompanyLicenseType` — enum con valori: `ROAD_HAULAGE_REGISTER`, `REN`, `COMMUNITY_LICENSE`, `OWN_ACCOUNT_LICENSE`, `ENVIRONMENTAL_MANAGERS_REGISTER_CATEGORY_2_BIS`, `ENVIRONMENTAL_MANAGERS_REGISTER_CATEGORY_4`, `ENVIRONMENTAL_MANAGERS_REGISTER_CATEGORY_5`.
- `TransportCompany` — modello/domain object del package.

## Regole importanti

- Il trasporto internazionale richiede licenze aziendali valide.
- Il trasporto rifiuti richiede categorie ambientali coerenti.

## Collegamenti

- TransportCompany conserva licenze aziendali.
- CompanyComplianceRules decide se l’azienda può fare internazionale o rifiuti.
