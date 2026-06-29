# TruckFlow Manager

**TruckFlow Manager** è un progetto Java 21 che modella il dominio di un gestionale realistico per trasporti, flotta, spedizioni, costi, documenti, magazzino, parcheggi, stipendi autisti e controllo della marginalità.

La versione documentata in questo pacchetto descrive lo stato dopo il completamento del domain layer prima dell'application layer.

## Stato attuale

- Linguaggio: **Java 21**.
- Build tool: **Maven**.
- Architettura attuale: **domain layer puro**.
- Framework esterni nel dominio: **nessuno**.
- Stato test rilevato dallo zip: **816 test, 0 failure, 0 errori, 0 skipped**.
- Package Java principali: **38 package domain**.
- Classi sorgenti principali: **265**.

## Cosa rappresenta

Il progetto non modella solo camion e spedizioni. Modella una base realistica da Fleet/Transport Management System:

```text
cliente / ordine
→ spedizione
→ pianificazione ufficio traffico
→ missione operativa
→ autista + mezzo + rimorchio/convoglio
→ documenti e compliance
→ tracking e chiusura missione
→ fatturazione cliente
→ costi, IVA, stipendi, asset, struttura, magazzino
→ utile/perdita e controllo economico
```

## Dove leggere

1. `docs/project-overview.md`
2. `docs/architecture.md`
3. `docs/domain-overview.md`
4. `docs/business-flow.md`
5. `docs/guides/economics-profitability.md`
6. `docs/guides/facilities-parking-inventory.md`
7. `docs/guides/driver-payroll.md`
8. `docs/guides/application-layer-next.md`
9. `docs/domain-reference-complete.md`

## Comando principale

```bash
mvn clean test
```

## Principio architetturale

Il dominio contiene regole e modelli di business. Non contiene database, REST API, Spring, UI, filesystem o chiamate a servizi esterni. Queste parti saranno introdotte dopo nel layer `application`, `infrastructure` e `web`.
