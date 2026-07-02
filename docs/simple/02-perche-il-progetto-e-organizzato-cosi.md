# 2. Perché il progetto è organizzato così

TruckFlow Manager è diviso in parti perché ogni parte ha un compito diverso.

Questa separazione serve a evitare confusione. Se metti regole di business, database, API, sicurezza e interfaccia tutte insieme, il progetto diventa difficile da capire, testare e modificare.

## La divisione principale

Il progetto segue questa idea:

```text
Domain Layer
Application Layer
Infrastructure Layer
API Layer futuro
```

Ogni layer ha una responsabilità.

## Domain Layer

Il Domain Layer contiene le regole importanti del mondo TruckFlow.

Esempio: cosa significa una spedizione valida, un veicolo valido, un documento valido o un requisito di conformità valido.

Questa parte non deve sapere nulla di database, API, JSON, Spring o pagine web.

## Application Layer

L'Application Layer coordina le azioni.

Esempio: registrare una location, creare una shipment, registrare un documento, attivare un veicolo, trovare un requisito compliance.

Non decide le regole profonde del business, ma organizza il flusso: riceve un comando, controlla i dati, chiama il dominio e usa un repository per salvare o leggere.

## Infrastructure Layer

L'Infrastructure Layer contiene i dettagli tecnici.

Esempio: repository in-memory, repository file-backed, configurazioni Spring, mapper di persistenza, eccezioni tecniche.

Questa parte può conoscere application e domain, perché deve implementare i contratti richiesti dall'application layer.

## Perché questa organizzazione aiuta

Questa struttura permette di:

- cambiare database senza riscrivere le regole di business;
- testare i use case senza API o controller;
- evitare che Spring entri nel dominio;
- aggiungere REST API più avanti senza rompere quello che esiste;
- capire meglio dove mettere ogni nuova classe.

## Regola semplice

Quando non sai dove mettere qualcosa, chiediti:

- è una regola del mondo dei trasporti? Va nel domain.
- è un'azione del sistema? Va nell'application.
- è un dettaglio tecnico? Va nell'infrastructure.
- è una chiamata HTTP o una API? Andrà nel Punto 8.
