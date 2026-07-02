# Documentazione TruckFlow Manager

Questa cartella raccoglie tutta la documentazione ufficiale di TruckFlow Manager dopo la chiusura del **Punto 7 — Infrastructure Layer** e l’avvio controllato del **Punto 8 — API Layer**.

La documentazione è divisa in documenti storici, guide semplici, documentazione professionale e lettore digitale.

## Da dove partire

- **Vuoi capire il progetto in modo professionale?** Parti da [`professional/00-software-engineering-overview.md`](professional/00-software-engineering-overview.md).
- **Vuoi capire il nuovo Punto 8A?** Leggi [`professional/38-api-layer-blueprint.md`](professional/38-api-layer-blueprint.md).
- **Vuoi leggere una versione visuale nel browser?** Apri [`digital/software-engineering-overview.html`](digital/software-engineering-overview.html) o usa [`digital/index.html`](digital/index.html).
- **Vuoi capire il progetto da zero con parole semplici?** Parti da [`simple/README.md`](simple/README.md).
- **Vuoi una documentazione tecnica presentabile?** Vai in [`professional/README.md`](professional/README.md).
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

- **Punto 1 → Punto 5**: Domain Layer;
- **Punto 6A → Punto 6M**: Application Layer;
- **Punto 7A → Punto 7H**: Infrastructure Layer;
- **Punto 8A**: API Layer Blueprint, con documentazione formale e test architetturale future-proof.

Il prossimo ciclo naturale è:

- **Punto 8B — API Layer Foundation**.

## Digital Documentation Reader

Il reader digitale in [`digital/index.html`](digital/index.html) è un lettore statico.

Contiene:

- Home pulita con documento enterprise in evidenza;
- ricerca nella documentazione;
- sezioni Simple, Professional, Old Style, Roadmap, Architecture e Digital;
- Package Explorer ad albero apribile/chiudibile, basato sui package Java reali rilevati al momento dell’aggiornamento del catalogo;
- documento `38-api-layer-blueprint.md` nella documentazione professionale;
- versione HTML visuale del documento enterprise;
- apertura dei documenti in nuova scheda, senza iframe interno;
- versioni HTML statiche dei Markdown in `docs/digital/rendered/`, per evitare la lettura come testo grezzo.

Il reader non introduce backend, database, controller runtime o generazione automatica della documentazione. Il Punto 8A introduce solo blueprint API e test architetturale, non endpoint reali.
