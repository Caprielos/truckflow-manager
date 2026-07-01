# 1. Visione generale del progetto TruckFlow Manager

## 1.1 Obiettivo del progetto

TruckFlow Manager è un progetto Java pensato per diventare una piattaforma gestionale realistica per aziende di trasporto, logistica e gestione flotta.

L’obiettivo non è creare un semplice elenco di camion, autisti e spedizioni, ma costruire una base di dominio solida, ordinata e scalabile, capace di evolvere nel tempo verso un sistema enterprise.

In questa fase il progetto si concentra sul **domain layer**, cioè sulla parte che descrive i concetti fondamentali del business:

- utenti applicativi;
- abilitazioni e qualificazioni;
- figure operative aziendali;
- veicoli, rimorchi, semirimorchi e combinazioni;
- cargo, cioè merce e requisiti di trasporto;
- location, cioè luoghi logistici riutilizzabili;
- trip template, cioè percorsi tipo e missioni tecniche astratte;
- shipment, cioè richieste di spedizione composte da cargo, tratte logiche, requisiti, priorità e riferimenti;
- documents, cioè documenti aziendali astratti, classificati e riusabili senza file fisici, upload, workflow o storage;
- compliance, cioè requisiti astratti di conformità con regole, categorie, livelli di obbligatorietà, severità, target, fonti e giurisdizioni.

Il dominio non è ancora collegato a database, API REST, interfacce grafiche o servizi esterni. Questa è una scelta intenzionale: prima si costruisce il modello del business, poi si aggiungono infrastruttura e applicazione.

## 1.2 Perché partire dal dominio puro

Il dominio puro permette di ragionare sui concetti aziendali senza confonderli con dettagli tecnici.

Per esempio:

- un `User` non deve sapere nulla di JWT o sessioni HTTP;
- un `Driver` non deve dipendere da una tabella SQL;
- un `VehicleUnit` non deve dipendere da un controller REST;
- una `Qualification` non deve contenere file PDF, scadenze o documenti caricati;
- una `VehicleCombination` non deve ancora sapere se un rimorchio è disponibile oggi;
- un `CargoUnit` non deve ancora decidere quale veicolo può caricarlo.

Questa separazione rende il progetto più pulito e più facile da estendere.

## 1.3 I macro-domini attuali

Il dominio è stato diviso in dieci macro-aree:

### `domain.users`

Rappresenta gli account applicativi: chi accede al sistema, con quali ruoli, quali permessi, quale stato e quali dati di profilo legati all’account.

### `domain.qualifications`

Rappresenta il catalogo statico delle abilitazioni, certificazioni, patenti, CQC, ADR e formazioni operative.

### `domain.operational`

Rappresenta le persone operative reali dell’azienda: autisti, meccanici, magazzinieri, dispatcher e manager.

### `domain.vehicles`

Rappresenta il parco mezzi: unità veicolo singole, trailer, semirimorchi, trattori, combinazioni come bilico e autotreno, caratteristiche tecniche, allestimenti, capacità e agganci.

### `domain.cargo`

Rappresenta la merce: identificazione, tipologia, categorie logistiche, pesi, dimensioni, imballaggio, temperatura, pericolosità, requisiti normativi e requisiti di trasporto.

### `domain.locations`

Rappresenta i luoghi logistici e geografici usati dal sistema: depositi, magazzini, clienti, fornitori, yard, hub, porti, aeroporti, terminal ferroviari, terminal intermodali, aree di servizio e altri punti logistici.

### `domain.triptemplates`

Rappresenta percorsi tipo e missioni tecniche astratte. Un `TripTemplate` non è un viaggio reale: non contiene autisti, veicoli, cargo, orari o tracking. Descrive solo la struttura del percorso tramite segmenti ordinati e riferimenti a `LocationId`.

### `domain.shipments`

Rappresenta le richieste di spedizione. Una `Shipment` descrive cosa deve essere spedito, quali cargo compongono la spedizione, quali tratte logiche sono richieste, quali requisiti di trasporto devono essere rispettati, quale priorità ha e quale livello di servizio è richiesto. Non assegna ancora veicoli, autisti, orari o tracking. Il package è organizzato in sottopackage tematici, ma `Shipment` rimane l'unico aggregate root.

### `domain.documents`

Rappresenta il documento aziendale come concetto astratto e riusabile. Un `Document` contiene identità, codice, tipo, categoria, stato, metadati, contenuto logico e riferimenti generici verso altri domini. Non contiene file fisici, PDF, upload, storage, firma digitale, scadenze o workflow.

### `domain.compliance`

Rappresenta i requisiti astratti di conformità. Un `ComplianceRequirement` descrive quale regola esiste, a quale target astratto si applica, quale categoria riguarda, quanto è obbligatoria, quanto è severa, da quale fonte deriva e in quale giurisdizione vale. Non esegue controlli concreti, non registra violazioni, non gestisce scadenze, audit, workflow o approvazioni.

## 1.4 Perché separare i contesti

La separazione dei contesti evita errori di modellazione.

Un esempio importante è la differenza tra `User` e `Driver`.

Un `User` è un account applicativo. Serve per accedere al sistema.

Un `Driver` è una figura operativa reale. Serve per rappresentare un autista nel business dell’azienda.

Quindi non vanno fusi nella stessa classe.

Lo stesso ragionamento vale per le qualificazioni: una patente C è una `Qualification` del catalogo, mentre il fatto che Mario Rossi possieda quella patente appartiene al dominio operativo tramite una `OperationalQualification`.

Lo stesso principio vale per cargo e veicoli: il cargo dichiara i propri requisiti, il veicolo dichiara le proprie capacità, mentre la verifica di compatibilità appartiene a un futuro modulo di pianificazione o assegnazione.

Lo stesso principio vale anche per location, trip template, shipment e documents: una location è un luogo riutilizzabile, un `TripTemplate` usa solo `LocationId` per indicare origine e destinazione dei segmenti, una `Shipment` usa `CargoId` e `LocationId` per riferirsi alla merce e ai luoghi senza inglobare gli aggregati completi, un `Document` usa `DocumentReference` generico per riferirsi ad altri contesti senza importarne le classi, e un `ComplianceRequirement` usa `ComplianceTarget` per indicare il tipo di dominio a cui si applica senza collegarsi a istanze concrete. In questo modo i contesti rimangono separati e più semplici da evolvere.

## 1.5 Principi architetturali seguiti

Il progetto segue alcuni principi ispirati a Domain-Driven Design e Clean Architecture:

- separazione tra dominio, applicazione e infrastruttura;
- classi di dominio senza dipendenze da framework;
- value object per rappresentare concetti specifici;
- entità con identità propria;
- regole di dominio vicine ai dati che proteggono;
- niente duplicazioni concettuali;
- niente logiche temporali o tecniche inserite troppo presto;
- uso di riferimenti tramite ID tra aggregati diversi.

## 1.6 Cosa non fa ancora il dominio

Il dominio attuale non gestisce ancora:

- viaggi operativi reali;
- esecuzione operativa delle spedizioni;
- assegnazione autista-mezzo;
- disponibilità giornaliera;
- pianificazione;
- esecuzione reale dei trip;
- manutenzione dettagliata;
- scadenze legali;
- verifiche concrete di compliance;
- violazioni, audit e workflow approvativi;
- gestione file fisici e workflow documentali;
- audit trail completo;
- telematica;
- GPS;
- integrazioni esterne.

Queste parti verranno aggiunte in fasi successive, mantenendo il dominio attuale come base pulita e stabile.

## 1.7 TruckFlow Domain Foundation v1.0

La versione attuale del dominio rappresenta la **TruckFlow Domain Foundation v1.0**.

Questo significa che la fondazione del dominio puro è stata definita e che la prima review correttiva del dominio è stata eseguita con interventi mirati su invarianti, eccezioni, codici aziendali, test, pulizia del repository e identificativi tecnici del dominio veicoli.

Non significa che tutta la piattaforma sia già enterprise completa in senso assoluto. API, database, workflow, audit, planning, dispatching e integrazioni esterne rimangono attività successive.

Le regole fondamentali della fondazione sono:

- ogni dominio deve mantenere confini chiari;
- gli aggregate root non devono essere importati direttamente da altri domini;
- i riferimenti tra domini devono avvenire tramite ID, value object stabili o concetti astratti;
- i value object simili non vanno unificati se hanno significato diverso;
- le eccezioni custom devono essere usate per rappresentare errori specifici di dominio;
- le mutazioni degli aggregate devono avvenire solo dopo il completamento delle validazioni;
- i codici aziendali devono essere obbligatori quando rappresentano identificativi interni leggibili;
- la targa e il VIN devono essere modellati come value object del dominio veicoli, non come semplici stringhe;
- i test dei cataloghi devono proteggere comportamento e coerenza, non conteggi fragili;
- le violazioni concrete di compliance non fanno ancora parte del dominio puro.

I documenti operativi di riferimento sono [`13-domain-rules.md`](13-domain-rules.md) e [`14-domain-review-patches.md`](14-domain-review-patches.md).

## Avvio del Punto 6 — Application Layer

Dopo la stabilizzazione della TruckFlow Domain Foundation, il progetto entra nel Punto 6A: blueprint dell'application layer.

Questo passaggio non introduce ancora REST API, database o framework. Serve a definire come il sistema dovrà eseguire azioni applicative usando il dominio: registrare location, registrare cargo, creare shipment, aggiungere item e tratte, confermare shipment e recuperare informazioni tramite repository astratti.

Il primo blocco consigliato è Locations + Cargo + Shipments, perché rappresenta un flusso logistico reale senza anticipare moduli futuri come planning, dispatching, tracking o availability.
