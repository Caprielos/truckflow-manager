# TruckFlow Manager — Documentazione del dominio

Questa cartella contiene la documentazione del dominio puro di TruckFlow Manager.

Il progetto, allo stato attuale, è concentrato sul **domain layer** e contiene i seguenti package principali:

- `domain.users`
- `domain.qualifications`
- `domain.operational`
- `domain.vehicles`
- `domain.cargo`
- `domain.locations`
- `domain.triptemplates`
- `domain.shipments`
- `domain.documents`
- `domain.compliance`

L’obiettivo di questa documentazione è spiegare in italiano, in modo chiaro e professionale, **perché il dominio è stato modellato così**, quali scelte sono state fatte e cosa ogni package rappresenta.

## Indice consigliato

1. [`01-project-overview.md`](01-project-overview.md) — visione generale del progetto e principi architetturali.
2. [`02-domain-users.md`](02-domain-users.md) — documentazione completa del dominio utenti.
3. [`03-domain-qualifications.md`](03-domain-qualifications.md) — documentazione completa del catalogo abilitazioni.
4. [`04-domain-operational.md`](04-domain-operational.md) — documentazione completa delle figure operative aziendali.
5. [`05-domain-vehicles.md`](05-domain-vehicles.md) — documentazione completa del dominio veicoli e combinazioni.
6. [`06-architecture-decisions.md`](06-architecture-decisions.md) — decisioni architetturali e regole generali del dominio puro.
7. [`07-domain-cargo.md`](07-domain-cargo.md) — documentazione completa del dominio cargo e dei requisiti della merce.
8. [`08-domain-locations.md`](08-domain-locations.md) — documentazione completa del dominio locations e dei luoghi logistici.
9. [`09-domain-triptemplates.md`](09-domain-triptemplates.md) — documentazione completa dei percorsi tipo e delle missioni tecniche astratte.
10. [`10-domain-shipments.md`](10-domain-shipments.md) — documentazione completa delle spedizioni richieste e dei loro requisiti.
11. [`11-domain-documents.md`](11-domain-documents.md) — documentazione completa del dominio documents e del concetto puro di documento aziendale.
12. [`12-domain-compliance.md`](12-domain-compliance.md) — documentazione completa del dominio compliance e dei requisiti astratti di conformità.
13. [`13-domain-rules.md`](13-domain-rules.md) — regole ufficiali della TruckFlow Domain Foundation v1.0 e roadmap della domain review.
14. [`14-domain-review-patches.md`](14-domain-review-patches.md) — riepilogo degli interventi correttivi eseguiti durante la prima review concreta del dominio puro.

## Stato del progetto documentato

Questa documentazione descrive la versione del progetto in cui il dominio contiene:

- account applicativi e autorizzazioni di base;
- catalogo statico delle qualificazioni e abilitazioni;
- figure operative reali dell’azienda;
- unità veicolo, allestimenti, combinazioni, schede tecniche, capacità, agganci e ruoli operativi dei mezzi, con `domain.vehicles` organizzato in sottopackage (`unit`, `combination`, `coupling`, `specification`, `body`, `operation`, `common`);
- dominio cargo per descrivere la merce, le sue caratteristiche e i suoi requisiti di trasporto senza introdurre pianificazione o compatibilità implementata;
- dominio locations per descrivere luoghi logistici riutilizzabili come depositi, hub, yard, porti, clienti e fornitori;
- dominio triptemplates per descrivere percorsi tipo e missioni tecniche astratte senza assegnare mezzi, autisti, cargo o orari reali;
- dominio shipments per descrivere richieste di spedizione, item cargo, tratte logiche, priorità, livelli di servizio, requisiti e riferimenti senza introdurre pianificazione o tracking, con `domain.shipments` organizzato in sottopackage (`core`, `items`, `legs`, `requirements`, `metrics`, `properties`, `notes`, `references`);
- dominio documents per descrivere il concetto puro di documento aziendale, con identità, codice, tipo, categoria, stato, metadati, contenuto logico e riferimenti astratti, senza introdurre file fisici, upload, storage, workflow, scadenze o compliance operativa;
- dominio compliance per descrivere requisiti astratti di conformità, categorie, livelli di obbligatorietà, severità, target, regole, fonti e giurisdizioni senza introdurre controlli automatici, workflow, audit, scadenze o risultati di verifica.
- regole ufficiali di dominio per guidare la TruckFlow Domain Foundation v1.0, la review finale del dominio puro e l'introduzione graduale delle eccezioni custom;
- prima review correttiva del dominio puro completata con sei interventi mirati: mutazioni atomiche, eccezioni custom, `OperationalCode` obbligatorio, test catalogo qualificazioni meno fragili e pulizia dei file locali/generati.


## Nota sul packaging di `domain.vehicles`

Il dominio veicoli è stato riorganizzato in sottopackage per renderlo più leggibile:

- `domain.vehicles.unit` per le unità fisiche;
- `domain.vehicles.combination` per bilici, autotreni e mezzi singoli operativi;
- `domain.vehicles.coupling` per agganci e traino;
- `domain.vehicles.specification` per le schede tecniche;
- `domain.vehicles.body` per allestimenti e profili di allestimento;
- `domain.vehicles.operation` per capacità e ruoli operativi;
- `domain.vehicles.common` per validazioni condivise.

Questa divisione non è per tipo di mezzo, ma per concetto di dominio. È stata scelta perché molte classi sono condivise tra camion, trattori, rimorchi, semirimorchi e mezzi di magazzino.

## Cosa significa “domain puro”

Nel progetto TruckFlow, “domain puro” significa che le classi di dominio descrivono il business e le sue regole essenziali, senza dipendere da framework o infrastruttura.

Quindi il dominio non contiene:

- controller REST;
- database;
- JPA;
- Spring;
- JWT;
- microservizi;
- chiamate HTTP;
- disponibilità giornaliera;
- pianificazione dei viaggi;
- documenti PDF;
- scadenze amministrative;
- GPS o telematica.

Tutte queste parti potranno essere aggiunte in futuro in layer o moduli separati, senza sporcare il dominio.

## Nota su `domain.locations` e `domain.triptemplates`

Le location sono state modellate come dominio separato perché i luoghi non appartengono solo ai percorsi: in futuro saranno utili anche per clienti, fornitori, magazzini, spedizioni, tracking, documenti e pianificazione.

I percorsi astratti sono stati modellati come `TripTemplate`, non come `Trip`, per evitare confusione con il viaggio reale operativo. Un `TripTemplate` descrive la struttura del percorso; il viaggio eseguito con veicolo, autista, cargo e orari reali verrà modellato più avanti in planning/dispatching.

## Nota su `domain.shipments`

Il dominio shipments rappresenta la richiesta di spedizione: cosa deve essere spedito, quali cargo compongono la spedizione, da quali location parte, verso quali location arriva e quali requisiti devono essere rispettati.

Una shipment non è ancora un viaggio operativo reale. Per questo non contiene veicoli, autisti, orari reali, tracking, documenti operativi o costi. Questi concetti verranno introdotti più avanti nei moduli di planning, dispatching, transport execution, tracking e documents.

Il package `domain.shipments` è stato riorganizzato in sottopackage tematici per migliorare la leggibilità:

- `domain.shipments.core` per l'aggregate root `Shipment`, ID, codice, stato, priorità, livello di servizio e validazioni condivise;
- `domain.shipments.items` per gli item cargo della spedizione;
- `domain.shipments.legs` per le tratte logiche della spedizione;
- `domain.shipments.requirements` per i requisiti di trasporto dichiarati;
- `domain.shipments.metrics` per peso e volume dichiarati;
- `domain.shipments.properties` per proprietà generali e temperatura;
- `domain.shipments.notes` per note interne ed esterne;
- `domain.shipments.references` per riferimenti cliente, fornitore e interni.

Questa divisione non crea micro-aggregate. `Shipment` rimane l'unico aggregate root; tutti gli altri elementi rimangono entity interne o value object appartenenti alla shipment.

## Nota su `domain.documents`

Il dominio documents rappresenta il documento aziendale come concetto astratto e riusabile. Non gestisce file PDF, upload, path filesystem, URL, storage, firma digitale, scadenze o workflow.

Un documento contiene solo identità, codice aziendale, tipo, categoria, stato astratto, metadati, contenuto logico opzionale e riferimenti generici verso altri domini tramite `DocumentReference`.

`DocumentReference` non importa gli ID concreti degli altri domini: usa `DocumentReferenceType` e un `referencedId` testuale. Questa scelta mantiene `domain.documents` disaccoppiato da vehicles, cargo, shipments, locations, triptemplates e operational.


## Nota su `domain.compliance`

Il dominio compliance rappresenta i requisiti astratti di conformità di TruckFlow. Un `ComplianceRequirement` descrive una regola, la sua categoria, il suo tipo, il livello di obbligatorietà, la severità, il target astratto, la fonte e la giurisdizione.

Il dominio non esegue controlli concreti e non contiene violazioni, audit, workflow, scadenze, approvazioni o notifiche. Questi concetti saranno introdotti più avanti nei moduli applicativi di compliance check, planning, dispatching e audit.

Questa scelta completa la prima grande fondazione del dominio puro enterprise di TruckFlow, mantenendo separati requisiti astratti e verifiche operative.



## Nota su `docs/14-domain-review-patches.md`

Il documento `14-domain-review-patches.md` riepiloga la prima review correttiva concreta del dominio puro.

Non descrive procedure operative di applicazione tecnica, ma spiega perché sono stati eseguiti i sei interventi principali:

- validare prima di mutare lo stato degli aggregate;
- usare eccezioni custom nei domini semplici;
- usare eccezioni custom nei domini complessi;
- rendere `OperationalCode` obbligatorio;
- rendere meno fragili i test del catalogo qualificazioni;
- tenere fuori dal repository file locali, artefatti generati e file temporanei.

Questo documento serve come storico architetturale della review e come riferimento per le prossime fasi.

## Nota su `docs/13-domain-rules.md`

Il documento `13-domain-rules.md` definisce la roadmap ufficiale della TruckFlow Domain Foundation v1.0.

La Domain Review Finale è considerata avviata e la roadmap è approvata, ma non ancora completata dominio per dominio.

Le regole principali sono:

- non unificare value object simili se hanno significato diverso;
- non importare aggregate root completi da altri domini;
- usare riferimenti tramite ID, value object stabili o concetti astratti;
- introdurre eccezioni custom di dominio in modo graduale;
- non aggiungere ancora concetti come `ComplianceViolationException`, perché le violazioni concrete appartengono a moduli futuri di compliance check, audit o planning.
