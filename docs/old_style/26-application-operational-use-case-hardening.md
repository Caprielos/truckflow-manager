# Archivio storico — 26-application-operational-use-case-hardening

> Questo documento fa parte dell'archivio storico `docs/old_style/`.
> Mantiene il percorso step-by-step del progetto, ma la documentazione principale aggiornata si trova in:
> `docs/simple/`, `docs/professional/` e `docs/digital/`.

---

# Punto 6K — Application Operational Use Case Review & Hardening

Il Punto 6K rivede e rafforza l'espansione applicativa verso gli **Operational Roles** introdotta nel Punto 6J.

Questa fase non aggiunge nuovi domini business. Serve a controllare che i use case applicativi per `Driver`, `Mechanic`, `WarehouseOperator`, `Dispatcher` e `Manager` siano coerenti, simmetrici, testati e documentati prima di procedere verso altri domini applicativi.

Il Punto 6K rimane dentro i confini dell'application layer. Non introduce REST API, controller Spring, database, JPA, Spring Data, security, tracking, planning, dispatching reale, turni, availability, payroll, dashboard, audit trail enterprise o workflow operativi.

## Obiettivo dello step

L'obiettivo del Punto 6K è consolidare il blocco Operational Roles dopo la sua introduzione.

La review verifica in particolare:

- copertura uniforme dei service di stato per tutti i ruoli operativi;
- assenza di mutazioni parziali quando una validazione di dominio fallisce;
- protezione copy-on-write su attivazione, sospensione e marcatura come non idoneo;
- gestione coerente di command nulli;
- gestione coerente di dependency repository nulle;
- documentazione allineata tra Markdown, documentazione digitale HTML + CSS e file di comandi;
- pulizia di piccoli commenti tecnici generati nel Punto 6J.

## Nessuna nuova funzionalità business

Il Punto 6K non introduce nuovi casi d'uso funzionali.

Non vengono aggiunti:

- assegnazioni driver-veicolo;
- assegnazioni driver-shipment;
- disponibilità giornaliera;
- turni;
- calendario operativo;
- payroll;
- HR avanzato;
- controlli reali su patente, CQC, ADR o visite mediche;
- dispatching reale;
- planning;
- tracking;
- dashboard;
- REST API;
- persistenza definitiva.

Queste parti appartengono a moduli futuri e non devono entrare ora nel livello application di base.

## Test aggiunto

È stato aggiunto il test:

```text
ApplicationOperationalUseCaseHardeningTest
```

Questo test affianca i test introdotti nel Punto 6J e rafforza il contratto operativo applicativo.

## Copertura dei service di stato per tutti i ruoli

Il test verifica che i use case di stato siano coperti per tutti i ruoli operativi correnti:

- `Driver`;
- `Mechanic`;
- `WarehouseOperator`;
- `Dispatcher`;
- `Manager`.

Per ogni ruolo vengono testati i passaggi principali:

- registrazione valida;
- sospensione;
- marcatura come non idoneo;
- riattivazione.

In questo modo il progetto non dipende solo dai casi driver/dispatcher, ma protegge esplicitamente anche mechanic, warehouse operator e manager.

## Hardening copy-on-write sulle attivazioni fallite

Il Punto 6J aveva già introdotto i mutation support copy-on-write:

```text
DriverMutationSupport
MechanicMutationSupport
WarehouseOperatorMutationSupport
DispatcherMutationSupport
ManagerMutationSupport
```

Il Punto 6K verifica anche il caso negativo più importante: una figura operativa registrata come `SUSPENDED` senza qualificazioni o scope non può essere attivata.

Il comportamento atteso è:

1. il service carica l'aggregate dalla repository;
2. crea una copia;
3. prova ad attivare la copia;
4. il dominio rifiuta l'attivazione perché mancano qualificazioni o scope;
5. la repository mantiene l'aggregate originale in stato `SUSPENDED`;
6. nessuna modifica parziale viene salvata.

Questo protegge gli adapter in memory dal problema dei riferimenti mutabili.

## Error handling applicativo verificato

Il Punto 6K rafforza il controllo degli errori applicativi.

I test verificano che tutti i service Operational Roles rifiutino command nulli con:

```text
UseCaseValidationException
```

Viene verificato lo stesso contratto anche per le dependency repository nulle nei costruttori dei service.

Questa scelta mantiene coerente il comportamento già stabilito negli step precedenti dell'application layer.

## Pulizia documentale nel codice

Sono stati corretti piccoli commenti tecnici nei package applicativi Operational Roles.

In particolare:

- `warehouseoperator` è stato uniformato in `warehouse operator` nei commenti JavaDoc;
- i commenti dei command e delle port `MarkNotEligible...` sono stati resi più leggibili, usando “mark as not eligible” invece di esporre il nome tecnico del metodo.

Non sono stati cambiati nomi di classi, metodi, package o contratti pubblici.

## Documentazione aggiornata

Il Punto 6K aggiorna la documentazione collegata:

- questo documento `docs/old_style/26-application-operational-use-case-hardening.md`;
- `docs/README.md`;
- `TRUCKFLOW_PROJECT_DOCUMENTATION.md`;
- i documenti applicativi precedenti dove è utile chiarire lo stato attuale;
- `docs/digital/index.html`;
- `docs/digital/styles.css`;
- `docs/digital/README.md`;
- `docs/digital/truckflow-manager-enterprise-documentation.html`;
- `command_basic.md`.

La documentazione digitale rimane un mirror navigabile della documentazione Markdown ufficiale.

## Confini architetturali rispettati

Il Punto 6K non introduce:

- dipendenze dal dominio verso application o infrastructure;
- dipendenze dall'application layer verso infrastructure concreta;
- dipendenze dall'application layer verso controller, web, JPA o Spring Data;
- persistenza definitiva;
- API REST;
- motori di workflow;
- regole enterprise avanzate.

Il dominio Operational continua a contenere le invarianti business. L'application layer orchestra i casi d'uso e gestisce errori applicativi, repository port e result.

## Stato finale dopo il Punto 6K

Dopo il Punto 6K, il blocco Operational Roles risulta più solido:

- use case applicativi presenti per tutti i ruoli principali;
- repository port e repository in memory presenti;
- mutazioni di stato protette con copy-on-write;
- test positivi e negativi più completi;
- command nulli e dependency nulle controllati;
- documentazione aggiornata.

Il Punto 6L è stato poi applicato come espansione controllata verso i use case base di Compliance. Successivamente è stato completato anche il **Punto 6M — Application Layer Final Review & Freeze**.

## Aggiornamento successivo — Punto 6L Compliance base

Dopo l'hardening Operational Roles, il Punto 6L applica una nuova espansione controllata verso Compliance base.

Il blocco Operational Roles rimane invariato. Compliance base aggiunge invece register/find/status use cases per `ComplianceRequirement`, mantenendo fuori controlli reali su persone, documenti, veicoli, cargo o shipment.

## Allineamento Punto 6M

Il Punto 6M chiude il primo ciclo dell'application layer con una review/freeze finale. Da questo momento i contenuti documentati nei punti 6A-6L sono considerati fondazione applicativa stabile: eventuali evoluzioni future dovranno essere introdotte in nuovi punti roadmap, mantenendo ancora fuori REST API, controller, database, JPA, Spring Data, security, tracking, planning, dashboard, workflow e integrazioni esterne.
