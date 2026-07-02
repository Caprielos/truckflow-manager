# 5. Infrastructure Layer spiegato semplice

L'Infrastructure Layer è la parte tecnica del progetto.

Se il dominio rappresenta le regole e l'application layer coordina le azioni, l'infrastructure layer si occupa dei dettagli pratici: dove salvare i dati, come collegare i pezzi, come mappare oggetti tecnici e come testare questi collegamenti.

## Cosa abbiamo fatto nel Punto 7

Nel Punto 7 abbiamo costruito una infrastruttura prudente e controllata.

Abbiamo aggiunto:

- package infrastrutturali base;
- eccezioni tecniche;
- Spring come wiring non-web;
- blueprint del mapping domain ↔ persistence;
- repository file-backed prototipali;
- test tecnici infrastrutturali;
- freeze finale del Punto 7.

## Cosa significa file-backed

Un repository file-backed salva i dati su file.

Non è ancora un database vero, ma è più reale di un repository solo in-memory.

È utile perché ci permette di validare il pattern repository + mapper senza introdurre subito JPA, SQL, Spring Data o database.

## Perché non abbiamo introdotto subito un database

Perché un database introduce molte decisioni: tabelle, relazioni, JPA, schema, migrazioni, transazioni.

Prima di fare questo, volevamo controllare bene:

- come mappare il dominio;
- come mantenere puliti i layer;
- come testare repository tecnici;
- come evitare che il dominio dipenda dall'infrastruttura.

## Spring nel Punto 7

Spring è stato introdotto solo come collegamento tecnico.

È come un centralino: collega i repository e i use case, ma non contiene regole business e non entra nel dominio.

Il progetto resta non-web:

- niente controller;
- niente REST API;
- niente endpoint HTTP;
- niente security web.

Queste cose arriveranno nel Punto 8 o dopo.
