# Documentazione TruckFlow Manager

Questa documentazione è stata riscritta sulla versione del progetto dopo:

- integrazione del modello realistico flotta/autisti/certificati;
- rimozione del vecchio package `it.gabriele.truckflow.shipment` fuori da `domain`;
- aggiunta di economia reale: IVA, costi, ricavi, asset, fatture fornitore e marginalità;
- aggiunta di depositi, strutture, parcheggi, posti numerati e convogli parcheggiati;
- aggiunta di payroll autista basato su ore, qualifiche, ADR, tipo trasporto, rimorchio e missione;
- aggiunta di contract, inventory, dispatch, data import e documenti operativi più strutturati.

## Indice rapido

| File | Contenuto |
| --- | --- |
| project-overview.md | Visione generale del progetto |
| architecture.md | Architettura e confini del dominio |
| domain-overview.md | Mappa dei moduli domain |
| business-flow.md | Flussi reali del trasporto |
| domain-package-map.md | Tabella package/classi/test |
| domain-reference-complete.md | Catalogo completo dei package |
| domain-rules.md | Regole business principali |
| testing-guide.md | Come testare e leggere i test |
| implementation-roadmap.md | Roadmap verso application/database/API |
| glossary.md | Glossario italiano/tecnico |

## Guide principali

| Guida | Tema |
| --- | --- |
| guides/economics-profitability.md | Costi, ricavi, IVA, debito, utile/perdita |
| guides/facilities-parking-inventory.md | Depositi, parcheggi, magazzino, posti numerati |
| guides/driver-payroll.md | Costo autista e stipendio missione |
| guides/contracts-pricing-billing.md | Contratti, listini, pricing e fatturazione |
| guides/dispatch-readiness.md | Ufficio traffico e scelta mezzi/autisti |
| guides/shipment-vs-mission.md | Differenza tra ordine, spedizione e missione |
| guides/realistic-fleet-model.md | Flotta, combinazioni e allestimenti reali |
| guides/application-layer-next.md | Come iniziare application layer |
