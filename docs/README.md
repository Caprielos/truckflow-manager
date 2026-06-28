# TruckFlow Manager — Documentazione Domain

Questa cartella documenta il modello di dominio di **TruckFlow Manager** dopo il refactor realistico della flotta e della logistica.

La documentazione è stata riscritta con l’obiettivo di spiegare:

- cosa rappresenta ogni package del domain;
- perché il modello è stato diviso in concetti piccoli;
- come ragioniamo su camion, rimorchi, semirimorchi, convogli, merce, autisti e compliance;
- quali regole stanno nel domain e cosa invece andrà più avanti in application, infrastructure e web.

## Ordine consigliato di lettura

1. [`project-overview.md`](project-overview.md)
2. [`architecture.md`](architecture.md)
3. [`domain-overview.md`](domain-overview.md)
4. [`domain-package-map.md`](domain-package-map.md)
5. [`domain-rules.md`](domain-rules.md)
6. [`packages/fleet.md`](packages/fleet.md)
7. [`packages/cargo.md`](packages/cargo.md)
8. [`packages/driver.md`](packages/driver.md)
9. Le altre pagine in [`packages/`](packages/)
10. [`implementation-roadmap.md`](implementation-roadmap.md)

## Struttura

```text
docs/
├── README.md
├── project-overview.md
├── architecture.md
├── domain-overview.md
├── domain-package-map.md
├── domain-rules.md
├── glossary.md
├── implementation-roadmap.md
├── testing-guide.md
├── packages/
└── architecture-decisions/
```

## Principio principale

TruckFlow Manager non modella un camion come una sola enum gigante. Il dominio separa:

```text
unità fisica del veicolo
allestimento
accessori
scheda tecnica
certificati
merce
autista
azienda
missione
documenti
costi
```

Questa separazione rende il progetto più estendibile e adatto a un vero software di gestione flotta.
