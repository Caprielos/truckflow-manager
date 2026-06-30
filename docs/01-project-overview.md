# 1. Visione generale del progetto TruckFlow Manager

## 1.1 Obiettivo del progetto

TruckFlow Manager è un progetto Java pensato per diventare una piattaforma gestionale realistica per aziende di trasporto, logistica e gestione flotta.

L’obiettivo non è creare un semplice elenco di camion, autisti e spedizioni, ma costruire una base di dominio solida, ordinata e scalabile, capace di evolvere nel tempo verso un sistema enterprise.

In questa fase il progetto si concentra sul **domain layer**, cioè sulla parte che descrive i concetti fondamentali del business:

- utenti applicativi;
- abilitazioni e qualificazioni;
- figure operative aziendali;
- veicoli, rimorchi, semirimorchi e combinazioni.

Il dominio non è ancora collegato a database, API REST, interfacce grafiche o servizi esterni. Questa è una scelta intenzionale: prima si costruisce il modello del business, poi si aggiungono infrastruttura e applicazione.

## 1.2 Perché partire dal dominio puro

Il dominio puro permette di ragionare sui concetti aziendali senza confonderli con dettagli tecnici.

Per esempio:

- un `User` non deve sapere nulla di JWT o sessioni HTTP;
- un `Driver` non deve dipendere da una tabella SQL;
- un `VehicleUnit` non deve dipendere da un controller REST;
- una `Qualification` non deve contenere file PDF, scadenze o documenti caricati;
- una `VehicleCombination` non deve ancora sapere se un rimorchio è disponibile oggi.

Questa separazione rende il progetto più pulito e più facile da estendere.

## 1.3 I macro-domini attuali

Il dominio è stato diviso in quattro macro-aree:

### `domain.users`

Rappresenta gli account applicativi: chi accede al sistema, con quali ruoli, quali permessi, quale stato e quali dati di profilo legati all’account.

### `domain.qualifications`

Rappresenta il catalogo statico delle abilitazioni, certificazioni, patenti, CQC, ADR e formazioni operative.

### `domain.operational`

Rappresenta le persone operative reali dell’azienda: autisti, meccanici, magazzinieri, dispatcher e manager.

### `domain.vehicles`

Rappresenta il parco mezzi: unità veicolo singole, trailer, semirimorchi, trattori, combinazioni come bilico e autotreno, caratteristiche tecniche, allestimenti, capacità e agganci.

## 1.4 Perché separare i contesti

La separazione dei contesti evita errori di modellazione.

Un esempio importante è la differenza tra `User` e `Driver`.

Un `User` è un account applicativo. Serve per accedere al sistema.

Un `Driver` è una figura operativa reale. Serve per rappresentare un autista nel business dell’azienda.

Quindi non vanno fusi nella stessa classe.

Lo stesso ragionamento vale per le qualificazioni: una patente C è una `Qualification` del catalogo, mentre il fatto che Mario Rossi possieda quella patente appartiene al dominio operativo tramite una `OperationalQualification`.

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

- viaggi;
- spedizioni;
- assegnazione autista-mezzo;
- disponibilità giornaliera;
- pianificazione;
- manutenzione dettagliata;
- scadenze legali;
- documenti;
- audit trail completo;
- telematica;
- GPS;
- integrazioni esterne.

Queste parti verranno aggiunte in fasi successive, mantenendo il dominio attuale come base pulita e stabile.
