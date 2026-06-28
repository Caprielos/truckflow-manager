# TruckFlow Manager

**TruckFlow Manager** è un progetto Java 21 che modella il dominio di un gestionale realistico per trasporti, flotta e logistica.

Il progetto attuale è concentrato sul **domain layer**: regole di business pure, entità, value object, enum e modelli operativi. Non dipende ancora da Spring, database, JPA, REST API, frontend, filesystem o servizi esterni.

## Stato della versione documentata

Questa documentazione descrive la versione aggiornata dopo:

- integrazione del modello realistico flotta/autisti/azienda;
- aggiunta di moduli operativi reali per carburante, pneumatici, manutenzione, telematica, fissaggio carico e danni;
- introduzione di certificati e scadenze per mezzi, autisti e azienda;
- pulizia del vecchio package `it.gabriele.truckflow.shipment` fuori da `domain`;
- mantenimento di una sola implementazione corretta: `it.gabriele.truckflow.domain.shipment`.

## Struttura documentazione

```text
docs/
├── README.md
├── project-overview.md
├── architecture.md
├── domain-overview.md
├── domain-package-map.md
├── domain-rules.md
├── glossary.md
├── testing-guide.md
├── implementation-roadmap.md
├── domain-reference-complete.md
├── current-version-notes.md
├── guides/
│   ├── shipment-vs-mission.md
│   ├── realistic-fleet-model.md
│   ├── operational-modules.md
│   └── next-application-layer.md
├── packages/
└── architecture-decisions/
```

## Comandi principali

```bash
mvn clean test
```

```bash
git status
```

## Lettura consigliata

1. `docs/project-overview.md`
2. `docs/architecture.md`
3. `docs/current-version-notes.md`
4. `docs/domain-overview.md`
5. `docs/domain-package-map.md`
6. `docs/guides/shipment-vs-mission.md`
7. `docs/packages/fleet.md`
8. `docs/packages/driver.md`
9. `docs/packages/shipment.md`

## Principio base

Il dominio deve rappresentare la realtà del trasporto, ma deve restare pulito: niente database, niente API esterne, niente UI e niente framework dentro le classi di dominio.
