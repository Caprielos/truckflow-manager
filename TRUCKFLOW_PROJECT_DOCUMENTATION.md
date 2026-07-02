# TruckFlow Manager — Documentazione principale

Questa è la mappa principale della documentazione del progetto.

La documentazione completa è organizzata sotto [`docs/`](docs/README.md) dopo la chiusura del **Punto 7 — Infrastructure Layer** e l’avvio controllato del **Punto 8 — API Layer**.

## Stato attuale

TruckFlow Manager ha completato:

- Punto 1 → Punto 5: Domain Layer;
- Punto 6A → Punto 6M: Application Layer;
- Punto 7A → Punto 7H: Infrastructure Layer;
- Punto 8A: API Layer Blueprint.

Il prossimo step sarà il **Punto 8B — API Layer Foundation**.

## Struttura documentale

```text
docs/
├── README.md
├── old_style/
├── simple/
├── professional/
└── digital/
```

## Lettura consigliata

- Documento enterprise testuale: [`docs/professional/00-software-engineering-overview.md`](docs/professional/00-software-engineering-overview.md).
- Blueprint API Layer: [`docs/professional/38-api-layer-blueprint.md`](docs/professional/38-api-layer-blueprint.md).
- Documento enterprise visuale: [`docs/digital/software-engineering-overview.html`](docs/digital/software-engineering-overview.html).
- Lettore digitale: [`docs/digital/index.html`](docs/digital/index.html).
- Guida semplice: [`docs/simple/README.md`](docs/simple/README.md).
- Documentazione tecnica: [`docs/professional/README.md`](docs/professional/README.md).
- Archivio storico: [`docs/old_style/README.md`](docs/old_style/README.md).

## Digital Documentation Reader

Il reader digitale è stato semplificato per essere più leggibile:

- sidebar pulita;
- ricerca nella documentazione;
- nessun filtro superfluo;
- Old Style Archive nella zona centrale;
- Package Explorer ad albero apribile/chiudibile;
- dettaglio package con stato documentale e documenti collegati;
- documento enterprise in HTML visuale;
- apertura dei documenti in nuova scheda, senza iframe interno persistente;
- rendering HTML statico dei documenti Markdown in `docs/digital/rendered/`.

Il reader resta statico e non introduce backend, database, security o modifiche ai layer applicativi.

## Punto 8A

Il Punto 8A ha introdotto il blueprint ufficiale del futuro API Layer:

- package futuro `it.gabriele.truckflow.api`;
- versionamento pubblico tramite `/api/v1`;
- primo contesto REST: Locations;
- endpoint futuri iniziali `POST /api/v1/locations` e `GET /api/v1/locations/{id}`;
- regola `API → Application → Domain`;
- divieto di dipendenza API verso Infrastructure e repository concreti;
- test `ApiLayerArchitectureTest` future-proof.

Non sono stati creati controller, DTO, mapper API, endpoint reali, security, JPA o database.
