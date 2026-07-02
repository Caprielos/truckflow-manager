# Documentazione TruckFlow Manager

Questa cartella raccoglie tutta la documentazione ufficiale di TruckFlow Manager dopo la chiusura del **Punto 7 — Infrastructure Layer**.

La documentazione è stata riorganizzata per evitare confusione tra documenti storici, guide semplici, documentazione professionale e versione digitale.

## Da dove partire

- **Vuoi capire il progetto da zero?** Parti da [`simple/README.md`](simple/README.md).
- **Vuoi una documentazione tecnica presentabile?** Vai in [`professional/README.md`](professional/README.md).
- **Vuoi navigare la documentazione dal browser?** Apri [`digital/index.html`](digital/index.html).
- **Vuoi leggere la storia step-by-step del progetto?** Consulta [`old_style/README.md`](old_style/README.md).

## Struttura

```text
docs/
├── README.md
├── old_style/
├── simple/
├── professional/
└── digital/
```

## Stato attuale del progetto

TruckFlow Manager ha completato:

- **Punto 1 → Punto 5**: dominio puro, review, eccezioni e regole di dominio;
- **Punto 6A → Punto 6M**: application layer, use case, repository port, in-memory repository, hardening e freeze;
- **Punto 7A → Punto 7H**: infrastructure layer, Spring wiring non-web, mapping blueprint, repository file-backed prototipali, test tecnici e freeze.

Il prossimo ciclo naturale sarà il **Punto 8 — API Layer**.

## Cosa contiene ogni sezione

### `simple/`

Guida semplice e ragionata, pensata per capire il progetto anche conoscendo poco la programmazione. Spiega le scelte, i perché, gli esempi e i concetti principali con parole chiare.

### `professional/`

Documentazione tecnica ufficiale, più formale e presentabile. Descrive architettura, layer, qualità, roadmap, stato attuale e prossimi step.

### `digital/`

Documentazione navigabile da browser. Sostituisce la vecchia cartella `digitalDocs/`.

### `old_style/`

Archivio storico con i documenti numerati da `01` a `37`. Sono stati conservati e resi più ordinati, ma non sono più il punto di partenza principale.

## Regola di manutenzione

Quando il progetto evolve, aggiorna prima la documentazione principale (`simple/`, `professional/`, `digital/`) e poi, se serve, aggiungi nuovi documenti storici in `old_style/`.
