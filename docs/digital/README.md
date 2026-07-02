# Digital Documentation Reader

Questa cartella contiene il lettore digitale statico della documentazione di TruckFlow Manager.

Apri [`index.html`](index.html) dal browser per navigare la documentazione.

## Cosa contiene

- **Home** pulita con il documento enterprise in evidenza;
- **Software Engineering Overview** in versione HTML visuale;
- **Simple Documentation**;
- **Professional Documentation**, incluso il blueprint `38-api-layer-blueprint.md`;
- **Package Explorer** ad albero apribile/chiudibile come in un IDE;
- **Old Style Archive** come sezione centrale, non come lista fissa in sidebar;
- **Roadmap & Freeze**;
- **Architecture Rules**;
- **Digital Reader**.

## Regola fondamentale

`docs/digital` è solo un **lettore statico** della documentazione.

Non introduce e non deve introdurre:

- Spring;
- API REST;
- controller;
- backend;
- database;
- generazione automatica runtime della documentazione;
- scansione runtime del filesystem;
- modifica dei file dal browser;
- dipendenze runtime dal futuro API Layer. Il Punto 8A è documentale e architetturale, non introduce endpoint reali.

## File principali

```text
docs/digital/
├── index.html
├── styles.css
├── docs-catalog.js
├── software-engineering-overview.html
├── rendered/
├── truckflow-manager-enterprise-documentation.html
└── README.md
```

## Ruolo dei file

- `index.html`: navigatore principale con sidebar pulita, ricerca, sezioni documentali e package tree. I documenti si aprono in una nuova scheda, senza iframe interno.
- `styles.css`: stile grafico del reader.
- `docs-catalog.js`: catalogo statico dei documenti, dei package Java reali e dello stato documentale. I documenti Markdown puntano alle versioni HTML renderizzate in `rendered/`.
- `software-engineering-overview.html`: versione visuale e funzionale del documento enterprise di Ingegneria del Software.
- `truckflow-manager-enterprise-documentation.html`: pagina enterprise sintetica già presente.
- `README.md`: spiegazione di questa cartella.
- `rendered/`: versioni HTML statiche dei documenti Markdown, pensate per essere aperte in nuova scheda e lette senza testo grezzo.

## Package Explorer

Il Package Explorer non mostra una griglia enorme di card.

Mostra invece un albero apribile e richiudibile, simile all’IDE:

```text
src
└── main
    ├── java
    │   └── it.gabriele.truckflow
    │       ├── application
    │       ├── domain
    │       └── infrastructure
    └── resources
        └── application.yml
```

Quando apri/chiudi i nodi puoi navigare la struttura; quando selezioni un package, il pannello dettaglio mostra:

- nome package;
- layer;
- numero file Java;
- stato documentazione;
- descrizione;
- documenti collegati.

Il catalogo è statico: rispecchia il progetto al momento dell’aggiornamento, ma il browser non scansiona automaticamente il filesystem.

## Nota Markdown / HTML

I documenti Markdown possono essere mostrati dal browser come testo. I documenti principali, come `software-engineering-overview.html`, hanno una versione HTML visuale pensata per la lettura nel reader.

## Apertura documenti

Il reader non usa più un iframe interno per i documenti.

Quando clicchi un documento, questo viene aperto in una nuova scheda del browser. I documenti Markdown vengono indirizzati a una versione HTML statica dentro `rendered/`, così non appaiono come testo grezzo nel reader e non rimangono visualizzati in modo persistente nella pagina principale.

Il pulsante **Home** e il comando **Torna alla Home** riportano alla schermata iniziale del reader.
