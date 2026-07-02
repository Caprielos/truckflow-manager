# Glossario semplice

Questo glossario spiega le parole difficili con un linguaggio pratico.

## Adapter

Una classe concreta che collega il progetto a una tecnologia o a un dettaglio tecnico. Esempio: un repository in-memory o file-backed.

## Application Layer

La parte che coordina le azioni del sistema. Riceve command, chiama il dominio e usa repository.

## Bean

Un oggetto creato e gestito da Spring.

## Command

L'oggetto che contiene i dati necessari per eseguire un use case.

## Controller

La classe che, in futuro, riceverà richieste HTTP. Non è ancora stata introdotta.

## Database

Un sistema per salvare dati in modo reale e strutturato. Non è ancora presente nel progetto.

## Domain Layer

La parte che contiene le regole vere del business.

## DTO

Oggetto usato per trasportare dati, spesso nelle API. Verrà valutato nel Punto 8.

## File-backed repository

Repository che salva dati su file. È più reale dell'in-memory, ma non è ancora un database.

## In-memory repository

Repository che salva dati solo in memoria. Utile per test e sviluppo.

## Infrastructure Layer

La parte tecnica del progetto: repository concreti, configurazione, mapping e wiring.

## JPA

Tecnologia Java per lavorare con database relazionali. Non è ancora introdotta.

## Port

Un contratto. Dice cosa serve, senza dire quale tecnologia lo realizza.

## Port in

Contratto di ingresso verso il sistema, come un use case.

## Port out

Contratto verso l'esterno, come un repository.

## Repository

Oggetto che salva e recupera dati.

## REST API

Modo per esporre funzioni via HTTP. Sarà il tema del Punto 8.

## Result

Oggetto restituito da un use case.

## Security

Gestione di accessi, ruoli, token e protezione. Non è ancora introdotta a livello web.

## Spring wiring

Uso di Spring per collegare tra loro gli oggetti. Nel progetto è non-web e resta nell'infrastructure layer.

## Use case

Azione che il sistema sa eseguire, come registrare una location o trovare un documento.
