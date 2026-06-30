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
- documents, cioè documenti aziendali astratti, classificati e riusabili senza file fisici, upload, workflow o storage.

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

Il dominio è stato diviso in nove macro-aree:

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

## 1.4 Perché separare i contesti

La separazione dei contesti evita errori di modellazione.

Un esempio importante è la differenza tra `User` e `Driver`.

Un `User` è un account applicativo. Serve per accedere al sistema.

Un `Driver` è una figura operativa reale. Serve per rappresentare un autista nel business dell’azienda.

Quindi non vanno fusi nella stessa classe.

Lo stesso ragionamento vale per le qualificazioni: una patente C è una `Qualification` del catalogo, mentre il fatto che Mario Rossi possieda quella patente appartiene al dominio operativo tramite una `OperationalQualification`.

Lo stesso principio vale per cargo e veicoli: il cargo dichiara i propri requisiti, il veicolo dichiara le proprie capacità, mentre la verifica di compatibilità appartiene a un futuro modulo di pianificazione o assegnazione.

Lo stesso principio vale anche per location, trip template, shipment e documents: una location è un luogo riutilizzabile, un `TripTemplate` usa solo `LocationId` per indicare origine e destinazione dei segmenti, una `Shipment` usa `CargoId` e `LocationId` per riferirsi alla merce e ai luoghi senza inglobare gli aggregati completi, e un `Document` usa `DocumentReference` generico per riferirsi ad altri contesti senza importarne le classi. In questo modo i contesti rimangono separati e più semplici da evolvere.

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
- gestione file fisici e workflow documentali;
- audit trail completo;
- telematica;
- GPS;
- integrazioni esterne.

Queste parti verranno aggiunte in fasi successive, mantenendo il dominio attuale come base pulita e stabile.
