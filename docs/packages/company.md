# Package `company` — Company

## Scopo

Rappresenta l’azienda di trasporto e le licenze necessarie per operare legalmente.

## Concetti principali

- `TransportCompany`
- `CompanyLicense`
- `CompanyLicenseType`
- `CompanyComplianceRules`

## Classi del package

| Classe | Tipo | Ruolo sintetico |
|---|---|---|
| `CompanyComplianceRules` | final class | Controlli sulle autorizzazioni aziendali. |
| `CompanyLicense` | final class | Abilitazione/certificazione con validità. |
| `CompanyLicenseType` | enum | Enum di classificazione/valori ammessi. |
| `TransportCompany` | final class | Azienda di trasporto con licenze operative. |

## Enum e valori ammessi

- `CompanyLicenseType`: `ROAD_HAULAGE_REGISTER`, `REN`, `COMMUNITY_LICENSE`, `OWN_ACCOUNT_LICENSE`, `ENVIRONMENTAL_MANAGERS_REGISTER_CATEGORY_2_BIS`, `ENVIRONMENTAL_MANAGERS_REGISTER_CATEGORY_4`, `ENVIRONMENTAL_MANAGERS_REGISTER_CATEGORY_5`

## Regole di business

- Licenze come Albo Autotrasportatori, REN, licenza comunitaria o Albo Gestori Ambientali possono essere obbligatorie in base al trasporto.
- Una licenza scaduta non deve rendere idonea l’azienda.

## Collegamenti con altri package

- operation per missioni
- cargo per rifiuti/internazionale/conto proprio
- compliance per controlli complessivi

## Test collegati

_Nessun test specifico rilevato nel package._

## Note di progettazione

Questo package appartiene al domain puro. Non deve contenere codice di database, API esterne, controller web, query SQL, repository concreti o logica di framework.

Le regole devono rimanere testabili con JUnit senza avviare servizi esterni.
