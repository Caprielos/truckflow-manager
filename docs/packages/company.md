# Package `company` — Licenze aziendali

## Scopo

Rappresenta licenze e iscrizioni aziendali necessarie per svolgere trasporti specifici.

## Classi ed enum

| Nome | Tipo | Ruolo sintetico |
|---|---|---|
| `CompanyLicense` | Classe | Licenza o iscrizione aziendale. |
| `CompanyLicenseType` | Enum | Tipi di licenze aziendali per autotrasporto, UE, conto proprio e rifiuti. |

## Enum principali

### `CompanyLicenseType`

Valori: `ROAD_HAULAGE_REGISTER`, `REN`, `COMMUNITY_LICENSE`, `OWN_ACCOUNT_LICENSE`, `ENVIRONMENTAL_MANAGERS_REGISTER_CATEGORY_2_BIS`, `ENVIRONMENTAL_MANAGERS_REGISTER_CATEGORY_4`, `ENVIRONMENTAL_MANAGERS_REGISTER_CATEGORY_5`.


## Licenze aziendali

Alcuni trasporti non dipendono solo da autista e mezzo. Serve anche che l’azienda sia autorizzata.

Esempi:

- albo autotrasportatori;
- REN;
- licenza comunitaria;
- licenza conto proprio;
- albo gestori ambientali categorie 2-bis, 4, 5.

Queste informazioni serviranno a `ComplianceRules` e ai futuri use case di missione.


## Collegamenti con altri package

Questo package non vive isolato: le sue classi vengono usate dalle regole di business e dai casi d’uso futuri.

Per capire il flusso completo leggere anche:

- [`../domain-overview.md`](../domain-overview.md)
- [`../domain-rules.md`](../domain-rules.md)
- [`../domain-package-map.md`](../domain-package-map.md)

## Test collegati

I test si trovano sotto:

```text
src/test/java/it/gabriele/truckflow/domain/company
```

Quando si modifica questo package, eseguire sempre:

```bash
mvn clean test
```
