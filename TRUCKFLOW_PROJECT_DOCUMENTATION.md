# TruckFlow Manager — Documentazione principale

Questa è la mappa principale della documentazione del progetto.

La documentazione completa è stata riorganizzata sotto [`docs/`](docs/README.md) dopo la chiusura del **Punto 7 — Infrastructure Layer**.

## Stato attuale

TruckFlow Manager ha completato:

- Punto 1 → Punto 5: Domain Layer;
- Punto 6A → Punto 6M: Application Layer;
- Punto 7A → Punto 7H: Infrastructure Layer.

Il prossimo ciclo sarà il **Punto 8 — API Layer**.

## Nuova struttura documentale

```text
docs/
├── README.md
├── old_style/
├── simple/
├── professional/
└── digital/
```

## Lettura consigliata

- Per capire il progetto con parole semplici: [`docs/simple/README.md`](docs/simple/README.md).
- Per una documentazione tecnica presentabile: [`docs/professional/README.md`](docs/professional/README.md).
- Per navigare dal browser: [`docs/digital/index.html`](docs/digital/index.html).
- Per consultare la storia step-by-step: [`docs/old_style/README.md`](docs/old_style/README.md).

## Archivio storico

I vecchi documenti numerati sono stati spostati in [`docs/old_style/`](docs/old_style/README.md).

L'archivio conserva i documenti da `01` a `37`, dal project overview fino al freeze finale dell'Infrastructure Layer.

## Guida semplice

La nuova guida semplice si trova in [`docs/simple/`](docs/simple/README.md).

È pensata per spiegare il progetto in modo chiaro, con esempi, analogie e ragionamenti sulle scelte fatte.

## Documentazione professionale

La documentazione tecnica ufficiale si trova in [`docs/professional/`](docs/professional/README.md).

Contiene overview, architettura, descrizione dei layer, qualità, roadmap, stato attuale, prossimi step e glossario tecnico professionale.

## Documentazione digitale

La documentazione digitale si trova in [`docs/digital/`](docs/digital/README.md).

La vecchia cartella `digitalDocs/` è stata superata dalla nuova struttura `docs/digital/`.

## Confini attuali del progetto

Alla fine del Punto 7 il progetto non contiene ancora:

- REST API;
- controller;
- DTO web;
- security HTTP;
- JPA;
- Spring Data;
- database relazionale;
- frontend.

Questi temi appartengono al futuro **Punto 8 — API Layer** o a cicli successivi.
